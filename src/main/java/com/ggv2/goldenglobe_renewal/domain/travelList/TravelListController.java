package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListResponse;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelListController {
  private final TravelListService travelListService;

  @PostMapping
  public ResponseEntity<TravelListResponse> createTravelList(
      @RequestBody TravelListCreateRequest request,
      @AuthenticationPrincipal CustomUser customUser
      ) {
    TravelListResponse response = travelListService.createTravelList(request, customUser.getUser().getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
