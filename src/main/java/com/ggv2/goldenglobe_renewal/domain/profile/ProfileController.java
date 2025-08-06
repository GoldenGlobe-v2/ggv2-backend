package com.ggv2.goldenglobe_renewal.domain.profile;

import com.ggv2.goldenglobe_renewal.domain.user.UserService;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

  private final UserService userService;

  @PostMapping("/users/me/profile-image")
  public ResponseEntity<Map<String, String>> uploadProfileImage(
      @AuthenticationPrincipal CustomUser customUser,
      @RequestParam("image") MultipartFile profileImage
  ) throws IOException {
    String imageUrl = userService.updateProfileImage(customUser.getUser().getId(), profileImage);

    return ResponseEntity.ok(Map.of("profileImageUrl", imageUrl));
  }
}
