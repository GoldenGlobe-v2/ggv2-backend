package com.ggv2.goldenglobe_renewal.global.auth;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

public class CustomUser extends org.springframework.security.core.userdetails.User {

  private final User user; // 이 필드는 반드시 초기화되어야 합니다.

  public CustomUser(User user) {
    // 부모 클래스(Spring Security의 User) 생성자 호출
    super(user.getCellphone(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

    // 아래 코드를 추가하여 final 필드를 초기화하세요.
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }
}
