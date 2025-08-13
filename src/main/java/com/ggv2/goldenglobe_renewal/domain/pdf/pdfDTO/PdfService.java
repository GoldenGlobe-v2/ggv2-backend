package com.ggv2.goldenglobe_renewal.domain.pdf.pdfDTO;

import com.ggv2.goldenglobe_renewal.domain.pdf.PdfList;
import com.ggv2.goldenglobe_renewal.domain.pdf.PdfListRepository;
import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import com.ggv2.goldenglobe_renewal.domain.travelList.TravelListRepository;
import com.ggv2.goldenglobe_renewal.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
@Transactional
@RequiredArgsConstructor
public class PdfService {
  private final PdfListRepository pdfListRepository;
  private final TravelListRepository travelListRepository;
  private final S3Service s3Service;
  private final RestTemplate restTemplate;

  public PdfUploadResponse uploadPdf(Long travelListId, Long userId, MultipartFile pdfFile) throws IOException {
    // 1. 여행 목록을 찾고, 요청한 사용자가 소유주인지 확인합니다.
    TravelList travelList = travelListRepository.findById(travelListId)
        .orElseThrow(() -> new IllegalArgumentException("Travel list not found"));

    if (!travelList.getUser().getId().equals(userId)) {
      throw new SecurityException("You do not have permission to upload PDFs to this list.");
    }

    // 2. S3에 PDF 파일을 업로드합니다.
    String pdfUrl = s3Service.upload(pdfFile, "pdfs");

    // 3. 업로드된 PDF 정보를 DB에 저장합니다.
    PdfList pdfList = PdfList.builder()
        .pdfName(pdfFile.getOriginalFilename())
        .pdfPath(pdfUrl)
        .travelList(travelList)
        .build();

    PdfList savedPdfList = pdfListRepository.save(pdfList);

    try {
      PdfIndexRequest indexRequest = new PdfIndexRequest(travelListId, new URL(pdfUrl));

      String aiServerUrl = "http://localhost:8000/index";
      restTemplate.postForEntity(aiServerUrl, indexRequest, String.class);
    } catch (Exception e) {
      System.err.println("Failed to call AI server for indexing: " + e.getMessage());
    }

    // 4. DTO로 변환하여 반환합니다.
    return PdfUploadResponse.from(savedPdfList);
  }
}
