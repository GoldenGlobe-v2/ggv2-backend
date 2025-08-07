package com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO;

import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TravelListResponse(
    Long travelListId, String country, String city, LocalDate startDate, LocalDate endDate, BigDecimal budget
) {
  public static TravelListResponse from(TravelList travelList) {
    return new TravelListResponse(
        travelList.getId(), travelList.getCountry(), travelList.getCity(),  travelList.getStartDate(), travelList.getEndDate(), travelList.getBudget()
    );
  }
}
