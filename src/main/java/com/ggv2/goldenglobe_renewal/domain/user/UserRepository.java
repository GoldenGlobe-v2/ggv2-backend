package com.ggv2.goldenglobe_renewal.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // List import 추가
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  // 핸드폰 번호로 사용자를 찾는 메서드 (로그인 시 필요)
  Optional<User> findByCellphone(String cellphone);

  // 이름으로 사용자를 찾는 메서드 (새로 추가)
  List<User> findByName(String name);
}
