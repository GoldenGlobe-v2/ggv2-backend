package com.ggv2.goldenglobe_renewal.domain.checkList;

import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "체크리스트")
@Getter
@NoArgsConstructor
public class CheckList {

  public CheckList(TravelList travelList) {
    this.travelList = travelList;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_list_id", nullable = false)
  private TravelList travelList;
}