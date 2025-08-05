package com.ggv2.goldenglobe_renewal.domain.user;

import com.ggv2.goldenglobe_renewal.domain.user.userDTO.*;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "02. 사용자 API", description = "로그인한 사용자 정보 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "내 정보 조회", description = "현재 로그인된 사용자의 정보를 조회합니다.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @GetMapping("/me")
  public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUser customUser) {
    UserProfileResponse profile = userService.getMyProfile(customUser.getUser().getId());
    return ResponseEntity.ok(profile);
  }

  @Operation(summary = "내 정보 수정", description = "현재 로그인된 사용자의 프로필 정보를 수정합니다.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @PutMapping("/me")
  public ResponseEntity<UserProfileResponse> updateMyProfile(
      @AuthenticationPrincipal CustomUser customUser,
      @RequestBody UserProfileUpdateRequest request) {
    UserProfileResponse updatedProfile = userService.updateMyProfile(customUser.getUser().getId(), request);
    return ResponseEntity.ok(updatedProfile);
  }

  @Operation(summary = "회원 탈퇴", description = "현재 로그인된 사용자가 탈퇴 처리됩니다.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @DeleteMapping("/me")
  public ResponseEntity<String> deleteMyAccount(@AuthenticationPrincipal CustomUser customUser) {
    userService.deleteAccount(customUser.getUser().getId());
    return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
  }
}
