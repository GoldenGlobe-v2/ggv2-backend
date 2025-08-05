package com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TravelListCreateRequest(String country, String city, LocalDate startDate, LocalDate endDate, BigDecimal budget) {

}
