package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TravelListRepository extends JpaRepository<TravelList, Long> {
  List<TravelList> findByUser(User user);
}
