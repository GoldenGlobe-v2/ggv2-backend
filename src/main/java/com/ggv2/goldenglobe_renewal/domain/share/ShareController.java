package com.ggv2.goldenglobe_renewal.domain.share;

import com.ggv2.goldenglobe_renewal.domain.share.shareDTO.ShareColorUpdateRequest;
import com.ggv2.goldenglobe_renewal.domain.share.shareDTO.ShareRequest;
import com.ggv2.goldenglobe_renewal.domain.share.shareDTO.SharedUserResponse;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklists/{checklistId}/shares")
@RequiredArgsConstructor
public class ShareController {

  private final ShareService shareService;

  @PostMapping
  public ResponseEntity<SharedUserResponse> createShare(
      @PathVariable Long checklistId,
      @AuthenticationPrincipal CustomUser customUser,
      @RequestBody ShareRequest request) {

    SharedUserResponse response = shareService.createShare(checklistId, customUser.getUser().getId(), request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<SharedUserResponse>> getSharedUsers(
      @PathVariable Long checklistId,
      @AuthenticationPrincipal CustomUser customUser
  ) {
    List<SharedUserResponse> response = shareService.getSharedUsers(checklistId, customUser.getUser().getId());

    return ResponseEntity.ok(response);
  }

  @PatchMapping
  public ResponseEntity<String> updateShareColor(
      @PathVariable Long checklistId,
      @AuthenticationPrincipal CustomUser customUser,
      @RequestBody ShareColorUpdateRequest request) {

    shareService.updateShareColor(checklistId, customUser.getUser().getId(), request);
    return ResponseEntity.ok("공유 색상이 변경되었습니다.");
  }
}
