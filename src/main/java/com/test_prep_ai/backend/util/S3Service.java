package com.test_prep_ai.backend.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test_prep_ai.backend.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${S3.BUCKET.NAME}")
    private String bucket;

    @Autowired
    private final AmazonS3 s3Client;

    // 허용된 파일 확장자 목록
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf");

    /**
     * 단일 파일 업로드 메서드
     * @param file 업로드할 MultipartFile 객체
     * @return 업로드된 파일명
     */
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServerException("File is empty");
        }

        // 파일명 생성 및 확장자 검증
        String fileName = createFileName(file.getOriginalFilename());

        // 메타데이터 설정
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        // S3 업로드
        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
        } catch (IOException e) {
            throw new ServerException("Failed to upload file to S3");
        }

        return fileName;
    }

    /**
     * S3 파일 삭제 메서드
     * @param fileName 삭제할 파일명
     */
    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } catch (Exception e) {
            throw new ServerException("Failed to delete file from S3");
        }
    }

    /**
     * 중복 방지를 위한 파일명 생성 (UUID 사용)
     * @param originalFileName 원본 파일명
     * @return 중복 방지가 적용된 새로운 파일명
     */
    private String createFileName(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new ServerException("Invalid file name");
        }

        String extension = getFileExtension(originalFileName);
        return UUID.randomUUID() + "." + extension;
    }

    /**
     * 파일 확장자 추출 및 유효성 검사
     * @param fileName 파일명
     * @return 파일 확장자
     */
    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1 || lastIndex == fileName.length() - 1) {
            throw new ServerException("File has no extension");
        }

        // 확장자 추출 및 소문자로 변환
        String extension = fileName.substring(lastIndex + 1).toLowerCase();

        // 허용된 확장자인지 검사
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ServerException("Unsupported file type: " + extension);
        }

        return extension;
    }

    /**
     * S3 파일 URL에서 파일 키 추출
     * @param fileUrl 전체 파일 URL
     * @return S3 내부 키값
     */
    public String extractS3Key(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            throw new ServerException("Invalid file URL");
        }

        String prefix = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/";
        if (!fileUrl.startsWith(prefix)) {
            throw new ServerException("File URL does not match bucket prefix");
        }

        return fileUrl.substring(prefix.length());
    }
}
