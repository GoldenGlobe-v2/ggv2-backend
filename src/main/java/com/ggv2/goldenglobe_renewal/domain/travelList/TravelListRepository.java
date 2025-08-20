package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelListRepository extends JpaRepository<TravelList, Long> {
  @Query("SELECT tl FROM TravelList tl JOIN FETCH tl.user WHERE tl.user = :user")
  List<TravelList> findByUserWithUser(@Param("user") User user);
}
