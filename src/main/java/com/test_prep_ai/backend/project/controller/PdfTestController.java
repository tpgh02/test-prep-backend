package com.test_prep_ai.backend.project.controller;

import com.test_prep_ai.backend.project.service.PdfTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
public class PdfTestController {

    private final PdfTestService pdfTestService;

    @GetMapping("/{projectId}/download")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable long projectId) {
        byte[] pdfBytes = pdfTestService.generatePdf(projectId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test-prep"+projectId+".pdf")
                .body(pdfBytes);
    }
}