package com.ggv2.goldenglobe_renewal.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
import com.ggv2.goldenglobe_renewal.domain.share.SharedList;
import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "회원")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;  // id BIGINT (PK)

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 60)  // BCrypt 해시 길이 고려
  private String password;

  @Column(nullable = false, unique=true, length = 11) // 로그인 ID로 사용되므로 unique = true 추가
  @Pattern(regexp = "^010\\d{8}$")
  private String cellphone;

  @Column
  private LocalDate birth;

  @Column
  private String profile;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<TravelList> travelLists = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<SharedList> sharedLists = new ArrayList<>();

  @Builder
  public User(String name, String password, String cellphone, LocalDate birth, String profile) {
    this.name = name;
    this.password = password;
    this.cellphone = cellphone;
    this.birth = birth;
    this.profile = profile;
  }

  // 프로필 정보 수정을 위한 별도 메서드 (Setter 대안)
  public void updateProfile(String profile) {
    this.profile = profile;
  }

  //비밀번호 변경을 위한 전용 메서드
  public void updatePassword(String newPassword) {
    this.password = newPassword;
  }
}
