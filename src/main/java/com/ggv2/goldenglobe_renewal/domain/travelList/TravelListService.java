package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListResponse;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import com.ggv2.goldenglobe_renewal.domain.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelListService {
  private final TravelListRepository travelListRepository;
  private final UserRepository userRepository;

  public TravelListResponse createTravelList(TravelListCreateRequest request, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    TravelList travelList = TravelList.builder()
        .country(request.country())
        .city(request.city())
        .startDate(request.startDate())
        .endDate(request.endDate())
        .budget(request.budget())
        .user(user) // 관계 설정
        .build();

    TravelList savedTravelList = travelListRepository.save(travelList);

    return TravelListResponse.from(savedTravelList);
  }
}
