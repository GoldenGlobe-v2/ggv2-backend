package com.ggv2.goldenglobe_renewal.domain.listGroup;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListGroupRepository extends JpaRepository<ListGroup, Long> {
  @Query("SELECT lg FROM ListGroup lg LEFT JOIN FETCH lg.listItems WHERE lg.checkList = :checkList")
  List<ListGroup> findAllByCheckListWithItems(@Param("checkList") CheckList checkList);
}
