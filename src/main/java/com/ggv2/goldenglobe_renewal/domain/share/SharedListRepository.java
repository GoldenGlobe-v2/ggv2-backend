package com.ggv2.goldenglobe_renewal.domain.share;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SharedListRepository extends JpaRepository<SharedList, Long> {
  boolean existsByCheckListAndUser(CheckList checkList, User user);

  Optional<SharedList> findByCheckListAndUser(CheckList checkList, User user);

  List<SharedList> findByCheckList(CheckList checkList);

  boolean existsByCheckListIdAndUserId(Long checklistId, Long userId);
}
