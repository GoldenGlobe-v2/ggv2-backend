package com.ggv2.goldenglobe_renewal.domain.auth;

import com.ggv2.goldenglobe_renewal.domain.user.userDTO.LoginRequest;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.TokenResponse;
import com.ggv2.goldenglobe_renewal.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final BlacklistService blacklistService;

  public TokenResponse login(LoginRequest request) {
    // 1. 사용자 인증 시도
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.cellphone(),
            request.password()
        )
    );

    // 2. 인증 성공 시 JWT 생성
    return jwtUtil.generateTokens(authentication);
  }

  public void logout(String accessToken) {
    // 1. "Bearer " 접두사 제거
    if (accessToken != null && accessToken.startsWith("Bearer ")) {
      accessToken = accessToken.substring(7);
    }

    // 2. (TODO) Redis 같은 저장소에 Access Token을 블랙리스트로 등록하는 로직
     blacklistService.addToBlacklist(accessToken);

    System.out.println("로그아웃 처리된 토큰: " + accessToken);
  }
}
