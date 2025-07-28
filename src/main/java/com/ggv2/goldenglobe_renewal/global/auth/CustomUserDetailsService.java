package com.ggv2.goldenglobe_renewal.global.auth;

import com.ggv2.goldenglobe_renewal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String cellphone) throws UsernameNotFoundException {
    return userRepository.findByCellphone(cellphone)
        .map(CustomUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자를 찾을 수 없습니다."));
  }
}
