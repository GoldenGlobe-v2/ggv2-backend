package com.ggv2.goldenglobe_renewal.domain.pdf.pdfDTO;

import com.ggv2.goldenglobe_renewal.domain.pdf.PdfList;

public record PdfUploadResponse(
    Long pdfId,
    String pdfName,
    String pdfUrl
) {
  public static PdfUploadResponse from(PdfList pdfList) {
    return new PdfUploadResponse(
        pdfList.getId(),
        pdfList.getPdfName(),
        pdfList.getPdfPath()
    );
  }
}
