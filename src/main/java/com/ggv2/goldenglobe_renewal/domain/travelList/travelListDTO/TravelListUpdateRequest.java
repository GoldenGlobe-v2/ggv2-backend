package com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO;

import java.time.LocalDate;

public record TravelListUpdateRequest(String country, String city, LocalDate startDate, LocalDate endDate, Double budget) {
}
