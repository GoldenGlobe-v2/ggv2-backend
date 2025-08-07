package com.ggv2.goldenglobe_renewal.domain.checkList;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChecklistRepository extends JpaRepository<CheckList, Long> {
  Optional<CheckList> findByTravelListId(Long travelListId);
}
