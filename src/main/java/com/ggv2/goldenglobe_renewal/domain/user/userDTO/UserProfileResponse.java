package com.ggv2.goldenglobe_renewal.domain.user.userDTO;

import com.ggv2.goldenglobe_renewal.domain.user.User;

public record UserProfileResponse(Long id, String name, String cellphone, String profile) {
  public static UserProfileResponse from(User user) {
    return new UserProfileResponse(
        user.getId(),
        user.getName(),
        user.getCellphone(),
        user.getProfile()
    );
  }
}
