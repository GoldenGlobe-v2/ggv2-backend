package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListResponse;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListUpdateRequest;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping
  public ResponseEntity<List<TravelListResponse>> getMyTravelLists(@AuthenticationPrincipal CustomUser customUser) {
    List<TravelListResponse> response = travelListService.getTravelListsByUser(customUser.getUser().getId());

    return ResponseEntity.ok(response);
  }

  @PutMapping("/{travelListId}")
  public ResponseEntity<TravelListResponse> updateTravelList(
      @PathVariable Long travelListId,
      @RequestBody TravelListUpdateRequest request,
      @AuthenticationPrincipal CustomUser customUser) {

    TravelListResponse response = travelListService.updateTravelList(travelListId, request, customUser.getUser().getId());
    return ResponseEntity.ok(response);
  }
}
