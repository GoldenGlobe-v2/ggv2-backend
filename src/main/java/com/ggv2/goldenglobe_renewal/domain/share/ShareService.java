package com.ggv2.goldenglobe_renewal.domain.share;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import com.ggv2.goldenglobe_renewal.domain.checkList.ChecklistRepository;
import com.ggv2.goldenglobe_renewal.domain.share.shareDTO.ShareRequest;
import com.ggv2.goldenglobe_renewal.domain.share.shareDTO.SharedUserResponse;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import com.ggv2.goldenglobe_renewal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShareService {

  private final SharedListRepository sharedListRepository;
  private final ChecklistRepository checklistRepository;
  private final UserRepository userRepository;

  public SharedUserResponse createShare(Long checklistId, Long ownerId, ShareRequest request) {
    CheckList checkList = checklistRepository.findById(checklistId)
        .orElseThrow(() -> new IllegalArgumentException("체크리스트를 찾을 수 없습니다."));

    if (!checkList.getTravelList().getUser().getId().equals(ownerId)) {
      throw new SecurityException("체크리스트 공유 권한이 없습니다.");
    }

    User guestUser = userRepository.findByCellphone(request.cellphone())
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

    if (sharedListRepository.existsByCheckListAndUser(checkList, guestUser)) {
      throw new IllegalStateException("이미 공유된 사용자입니다.");
    }

    SharedList sharedList = new SharedList(checkList, guestUser);
    SharedList savedSharedList = sharedListRepository.save(sharedList);

    return SharedUserResponse.from(savedSharedList);
  }

  @Transactional(readOnly = true)
  public List<SharedUserResponse> getSharedUsers(Long checklistId, Long requesterId) {

    CheckList checkList = checklistRepository.findById(checklistId)
        .orElseThrow(() -> new IllegalArgumentException("해당 체크리스트가 존재하지 않습니다."));

    boolean isOwner = checkList.getTravelList().getUser().getId().equals(requesterId);
    boolean isSharedUser = sharedListRepository.existsByCheckListAndUser(checkList, new User(requesterId)); // User 객체를 임시로 생성하여 확인

    if (!isOwner && !isSharedUser) {
      throw new SecurityException("해당 체크리스트를 조회할 권한이 없습니다.");
    }

    List<SharedList> sharedLists = sharedListRepository.findByCheckList(checkList);

    return sharedLists.stream()
        .map(SharedUserResponse::from)
        .toList();
  }
  }
}
