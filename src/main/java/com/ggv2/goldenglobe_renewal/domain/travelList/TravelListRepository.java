package com.ggv2.goldenglobe_renewal.domain.travelList;

import com.ggv2.goldenglobe_renewal.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelListRepository extends JpaRepository<TravelList, Long> {

  // Spring Data JPA가 메서드 이름을 보고 자동으로
  // 페이징 쿼리를 생성해주는 가장 기본적인 방식입니다.
  Page<TravelList> findByUser(User user, Pageable pageable);
}