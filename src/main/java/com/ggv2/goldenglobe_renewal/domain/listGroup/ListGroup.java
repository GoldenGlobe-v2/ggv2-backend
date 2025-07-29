package com.ggv2.goldenglobe_renewal.domain.listGroup;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import com.ggv2.goldenglobe_renewal.domain.listItem.ListItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "체크리스트그룹")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListGroup {

  public ListGroup(String groupName, CheckList checkList) {
    this.groupName = groupName;
    this.checkList = checkList;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String groupName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "checklist_id", nullable = false)
  private CheckList checkList;

  @OneToMany(mappedBy = "listGroup", cascade = CascadeType.ALL)
  private List<ListItem> listItems = new ArrayList<>();
}
