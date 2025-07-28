package com.ggv2.goldenglobe_renewal.domain.user;

import com.ggv2.goldenglobe_renewal.domain.user.userDTO.SignUpRequestDto;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.UserProfileResponse;
import com.ggv2.goldenglobe_renewal.domain.user.userDTO.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // C(Create) - 회원가입
  public Long signUp(SignUpRequestDto requestDto) {
    // 전화번호 중복 확인
    if (userRepository.findByCellphone(requestDto.getCellphone()).isPresent()) {
      throw new IllegalArgumentException("이미 가입된 핸드폰 번호입니다.");
    }

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

    User user = User.builder()
        .name(requestDto.getName())
        .password(encodedPassword)
        .cellphone(requestDto.getCellphone())
        .build();

    // 사용자 저장
    User savedUser = userRepository.save(user);
    return savedUser.getId();
  }

  @Transactional(readOnly = true)
  public UserProfileResponse getMyProfile(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

    return UserProfileResponse.from(user);
  }

  public UserProfileResponse updateMyProfile(Long userId, UserProfileUpdateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

    user.updateProfile(request.profile());

    return UserProfileResponse.from(user);
  }

  // R(Read) - 단일 사용자 조회 (ID 기준)
  @Transactional(readOnly = true)
  public User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=" + userId));
  }

  // R(Read) - 단일 사용자 조회 (핸드폰 번호 기준)
  @Transactional(readOnly = true)
  public User findUserByCellphone(String cellphone) {
    return userRepository.findByCellphone(cellphone)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. cellphone=" + cellphone));
  }


  // U(Update) - 비밀번호 재설정
  public void resetPassword(String cellphone) {
    User user = findUserByCellphone(cellphone);
    String tempPassword = generateTemporaryPassword();

    // 비밀번호를 암호화하여 업데이트
    user.updatePassword(passwordEncoder.encode(tempPassword));
    // userRepository.save(user)를 호출하지 않아도,
    // @Transactional에 의해 메서드 종료 시 변경된 내용이 DB에 자동 반영됩니다. (더티 체킹)
  }

  // D(Delete) - 사용자 삭제
  public void deleteAccount(Long userId) {
    userRepository.deleteById(userId);
  }

  private String generateTemporaryPassword() {
    return "tempPw123"; // 임시 비밀번호 생성 로직
  }
}
