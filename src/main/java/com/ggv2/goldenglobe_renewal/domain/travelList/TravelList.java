package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import com.ggv2.goldenglobe_renewal.domain.pdf.PdfList;
import com.ggv2.goldenglobe_renewal.domain.share.SharedList;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListUpdateRequest;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "여행지 목록")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TravelList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 255)
  private String country;

  @Column(length = 255)
  private String city;
  private LocalDate startDate;
  private LocalDate endDate;

  @Column(precision = 15, scale = 2)
  private BigDecimal budget;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToOne(mappedBy = "travelList", cascade = CascadeType.ALL, orphanRemoval = true)
  private CheckList checkList;

//  @OneToOne(mappedBy = "travelList", cascade = CascadeType.ALL, orphanRemoval = true)
//  private Chatbot chatbot;
//
//  @OneToMany(mappedBy = "travelList", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<Expense> expenses = new ArrayList<>();

  @OneToMany(mappedBy = "travelList", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PdfList> pdfLists = new ArrayList<>();

  @Builder
  public TravelList(String country, String city, LocalDate startDate, LocalDate endDate, BigDecimal budget, User user) {
    this.country = country;
    this.city = city;
    this.startDate = startDate;
    this.endDate = endDate;
    this.budget = budget;
    this.user = user;
  }

  public void update(TravelListUpdateRequest request) {
    this.country = request.country();
    this.city = request.city();
    this.startDate = request.startDate();
    this.endDate = request.endDate();
    this.budget = request.budget();
  }
}
