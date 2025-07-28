package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListResponse;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListUpdateRequest;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import com.ggv2.goldenglobe_renewal.domain.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

  @Transactional(readOnly = true)
  public List<TravelListResponse> getTravelListsByUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return travelListRepository.findByUser(user)
        .stream()
        .map(TravelListResponse::from)
        .toList();
  }

  public TravelListResponse updateTravelList(Long travelListId, TravelListUpdateRequest request, Long userId) {
    // 1. 여행 목록을 찾는다.
    TravelList travelList = travelListRepository.findById(travelListId)
        .orElseThrow(() -> new IllegalArgumentException("Travel list not found"));

    // 2. 현재 로그인한 사용자가 해당 여행 목록의 소유주인지 확인한다.
    if (!travelList.getUser().getId().equals(userId)) {
      throw new SecurityException("You do not have permission to edit this travel list.");
    }

    // 3. 엔티티의 수정 메서드를 호출하여 값을 변경한다.
    travelList.update(request);

    // 4. @Transactional에 의해 변경된 내용이 자동 저장되고, DTO로 변환하여 반환한다.
    return TravelListResponse.from(travelList);
  }
}
