package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
  private Double budget;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Builder
  public TravelList(String country, String city, LocalDate startDate, LocalDate endDate, Double budget, User user) {
    this.country = country;
    this.city = city;
    this.startDate = startDate;
    this.endDate = endDate;
    this.budget = budget;
    this.user = user;
  }
}
