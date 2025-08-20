package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.checkList.ChecklistService;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListResponse;
import com.ggv2.goldenglobe_renewal.domain.travelList.travelListDTO.TravelListUpdateRequest;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import com.ggv2.goldenglobe_renewal.domain.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelListService {
  private final TravelListRepository travelListRepository;
  private final UserRepository userRepository;
  private final ChecklistService checklistService;

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
    checklistService.createChecklist(savedTravelList);

    return TravelListResponse.from(savedTravelList);
  }

  @Transactional(readOnly = true)
  public List<TravelListResponse> getTravelListsByUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return travelListRepository.findByUserWithUser(user)
        .stream()
        .map(TravelListResponse::from)
        .toList();
  }

  public TravelListResponse updateTravelList(Long travelListId, TravelListUpdateRequest request, Long userId) {
    TravelList travelList = travelListRepository.findById(travelListId)
        .orElseThrow(() -> new IllegalArgumentException("해당 여행 목록을 찾을 수 없습니다."));

    if (!travelList.getUser().getId().equals(userId)) {
      throw new SecurityException("여행 목록 수정 권한이 없습니다.");
    }
    travelList.update(request);
    return TravelListResponse.from(travelList);
  }

  public void deleteTravelList(Long travelListId, Long userId) {
    TravelList travelList = travelListRepository.findById(travelListId)
        .orElseThrow(() -> new IllegalArgumentException("해당 여행 목록을 찾을 수 없습니다."));
    if (!travelList.getUser().getId().equals(userId)) {
      throw new SecurityException("여행 목록을 삭제할 권한이 없습니다.");
    }
    travelListRepository.delete(travelList);
  }
}
