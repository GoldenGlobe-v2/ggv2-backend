package com.ggv2.goldenglobe_renewal.domain.pdf;

import com.ggv2.goldenglobe_renewal.domain.pdf.pdfDTO.PdfService;
import com.ggv2.goldenglobe_renewal.domain.pdf.pdfDTO.PdfUploadResponse;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/travels/{travelListId}/pdfs")
public class PdfController {
  private final PdfService pdfService;

  @PostMapping
  public ResponseEntity<PdfUploadResponse> uploadPdf(
      @PathVariable Long travelListId,
      @AuthenticationPrincipal CustomUser customUser,
      @RequestParam("pdf") MultipartFile pdfFile) throws IOException {

    PdfUploadResponse response = pdfService.uploadPdf(travelListId, customUser.getUser().getId(), pdfFile);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
