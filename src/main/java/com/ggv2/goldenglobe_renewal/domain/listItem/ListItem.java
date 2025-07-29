package com.ggv2.goldenglobe_renewal.domain.listItem;

import com.ggv2.goldenglobe_renewal.domain.listGroup.ListGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "체크리스트_항목")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListItem {

  public ListItem(String itemName, ListGroup listGroup) {
    this.itemName = itemName;
    this.isChecked = false; // 기본값 설정
    this.listGroup = listGroup;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String itemName;

  private Boolean isChecked;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "list_group_id", nullable = false)
  private ListGroup listGroup;

  public void updateStatus(Boolean isChecked) {
    this.isChecked = isChecked;
  }
}
