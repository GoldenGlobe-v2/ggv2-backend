package com.ggv2.goldenglobe_renewal.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;

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

  @Column(nullable = false, length = 255)
  private String name; // name VARCHAR(255)

  @Column(nullable = false, length = 60)  // BCrypt 해시 길이 고려
  private String password;

  @Column(nullable = false, unique=true, length = 11) // 로그인 ID로 사용되므로 unique = true 추가
  @Pattern(regexp = "^010\\d{8}$")
  private String cellphone;

  @Column
  private LocalDate birth;

  @Column
  private String profile; //파일은 AWS S3 같은 스토리지에 업로드하고, 데이터베이스에는 그 파일의 주소(URL)만 String 형태로 저장
  // 'User'가 삭제될 때 관련된 'TravelList'도 함께 삭제되도록 설정
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TravelList> travelLists = new ArrayList<>();

  // 'User'가 삭제될 때 관련된 'SharedList' 정보도 함께 삭제되도록 설정
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SharedList> sharedLists = new ArrayList<>();

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
