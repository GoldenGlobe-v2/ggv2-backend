package com.ggv2.goldenglobe_renewal.domain.pdf;

import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PDF_목록")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PdfList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String pdfName;

  @Column
  private String pdfPath;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_list_id")
  private TravelList travelList;

  @Builder
  public PdfList(String pdfName, String pdfPath, TravelList travelList) {
    this.pdfName = pdfName;
    this.pdfPath = pdfPath;
    this.travelList = travelList;
  }
}
