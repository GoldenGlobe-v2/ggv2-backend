package com.ggv2.goldenglobe_renewal.domain.auth;

import com.ggv2.goldenglobe_renewal.domain.user.UserService;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.LoginRequest;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.SignUpRequestDto;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "01. 인증 API", description = "사용자 회원가입 및 로그인/로그아웃")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
class AuthController {
  private final UserService userService;
  private final AuthService authService;

  @Operation(summary = "회원가입")
  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto requestDto) {
    userService.signUp(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
  }

  @Operation(summary = "로그인")
  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
    TokenResponse tokens = authService.login(request);
    return ResponseEntity.ok(tokens);
  }

  @Operation(summary = "로그아웃")
  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {
    authService.logout(accessToken);
    return ResponseEntity.ok("성공적으로 로그아웃되었습니다.");
  }
}
