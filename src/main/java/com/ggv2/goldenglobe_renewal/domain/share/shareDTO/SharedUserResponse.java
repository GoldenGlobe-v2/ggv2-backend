package com.ggv2.goldenglobe_renewal.domain.share.shareDTO;

import com.ggv2.goldenglobe_renewal.domain.share.SharedList;

public record SharedUserResponse(
    Long sharedId,
    Long checklistId,
    Long userId,
    String userColor,
    String profileUrl
) {
  public static SharedUserResponse from(SharedList sharedList) {
    return new SharedUserResponse(
        sharedList.getId(),
        sharedList.getCheckList().getId(),
        sharedList.getUser().getId(),
        sharedList.getUserColor(),
        sharedList.getUser().getProfile()
    );
  }
}
