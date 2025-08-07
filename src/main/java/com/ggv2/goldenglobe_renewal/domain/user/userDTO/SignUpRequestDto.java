package com.ggv2.goldenglobe_renewal.domain.user.userDTO;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
  private String name;
  private String password;
  private String cellphone;
}
