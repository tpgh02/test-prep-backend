package com.test_prep_ai.backend.project.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.test_prep_ai.backend.problem.domain.ProblemEntity;
import com.test_prep_ai.backend.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfTestService {

    private final ProblemRepository problemRepository;

    public byte[] generatePdf(long projectID) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 프로젝트의 문제 목록 불러오기
            List<ProblemEntity> problemEntityList = problemRepository.findAllByProjectId(projectID);

            BaseFont baseFont = BaseFont.createFont(
                    "src/main/resources/fonts/NanumGothic.ttf",  // 폰트 파일 경로
                    BaseFont.IDENTITY_H,                        // 유니코드(한글) 인코딩
                    BaseFont.EMBEDDED                           // 폰트 내장(권장)
            );
            Font koreanFont = new Font(baseFont, 12, Font.NORMAL);

            // 기본 폰트 설정
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font answerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.ITALIC);

            for (ProblemEntity problem : problemEntityList) {
                // 문제 제목 추가 (굵은 글씨)
                Paragraph title = new Paragraph(problem.getTitle(), koreanFont);
                title.setSpacingBefore(10);
                document.add(title);

                // 객관식 문제인지 확인하고 선택지 추가
                Map<String, String> options = problem.getOptions();
                if (options != null && !options.isEmpty()) {
                    for (Map.Entry<String, String> entry : options.entrySet()) {
                        Paragraph option = new Paragraph(entry.getKey() + ". " + entry.getValue(), koreanFont);
                        document.add(option);
                    }
                }

                // 정답 추가
                Paragraph answer = new Paragraph("정답: " + problem.getAnswer(), koreanFont);
                answer.setSpacingBefore(5);
                document.add(answer);

                // 줄바꿈 추가
                document.add(new Paragraph("\n"));
            }

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF 생성 중 오류 발생", e);
        }
    }
}

