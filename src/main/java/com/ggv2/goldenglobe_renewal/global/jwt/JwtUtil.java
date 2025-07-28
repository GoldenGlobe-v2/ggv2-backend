package com.ggv2.goldenglobe_renewal.global.jwt;

import com.ggv2.goldenglobe_renewal.domain.user.userDTO.TokenResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  public TokenResponse generateTokens(Authentication authentication) {
    String accessToken = "";
    String refreshToken = "";
    return new TokenResponse(accessToken, refreshToken);
  }
}
