package com.ggv2.goldenglobe_renewal.domain.user;

import com.ggv2.goldenglobe_renewal.domain.auth.AuthService;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.*;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
  private final UserService userService;
  private final AuthService authService;

  @PostMapping("/auth/signup")
  public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto requestDto) {
    userService.signUp(requestDto); // 서비스의 메서드를 호출하기만 한다
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("회원가입이 성공적으로 완료되었습니다.");
  }

  @PostMapping("/auth/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
    TokenResponse tokens = authService.login(request);
    return ResponseEntity.ok(tokens);
  }

  @PostMapping("/auth/logout")
  public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {
    authService.logout(accessToken);
    return ResponseEntity.ok("성공적으로 로그아웃되었습니다.");
  }

  @GetMapping("/users/me")
  public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUser customUser) {
    UserProfileResponse profile = userService.getMyProfile(customUser.getUser().getId());
    return ResponseEntity.ok(profile);
  }

  @PutMapping("/users/me")
  public ResponseEntity<UserProfileResponse> updateMyProfile(
      @AuthenticationPrincipal CustomUser customUser,
      @RequestBody UserProfileUpdateRequest request
  ) {
    UserProfileResponse updatedProfile = userService.updateMyProfile(customUser.getUser().getId(), request);
    return ResponseEntity.ok(updatedProfile);
  }

  @DeleteMapping("/users/me")
  public ResponseEntity<String> deleteMyAccount(@AuthenticationPrincipal CustomUser customUser) {
    userService.deleteAccount(customUser.getUser().getId());
    return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
  }
}
