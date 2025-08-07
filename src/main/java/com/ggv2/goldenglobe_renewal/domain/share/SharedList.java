package com.ggv2.goldenglobe_renewal.domain.share;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import com.ggv2.goldenglobe_renewal.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "체크리스트_공유")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "checklist_id")
  private CheckList checkList;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private String userColor;

  public SharedList(CheckList checkList, User user) {
    this.checkList = checkList;
    this.user = user;
  }

  public void updateColor(String color) {
    this.userColor = color;
  }
}
