package com.ggv2.goldenglobe_renewal.domain.groupMemo;

import com.ggv2.goldenglobe_renewal.domain.listItem.ListItem;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "공유메모")
@Getter
public class GroupMemo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String memoContent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "list_item_id", nullable = false)
  private ListItem listItem;
}
