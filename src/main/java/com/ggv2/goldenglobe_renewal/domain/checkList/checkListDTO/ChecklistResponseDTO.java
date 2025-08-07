package com.ggv2.goldenglobe_renewal.domain.checkList.checkListDTO;

import com.ggv2.goldenglobe_renewal.domain.checkList.CheckList;
import com.ggv2.goldenglobe_renewal.domain.listGroup.ListGroup;
import com.ggv2.goldenglobe_renewal.domain.listItem.ListItem;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChecklistResponseDTO { // DTO 클래스명 변경
  private Long checklistId;
  private List<ListGroupDTO> groups;

  public ChecklistResponseDTO(CheckList checkList, List<ListGroup> listGroups) {
    this.checklistId = checkList.getId();
    this.groups = listGroups.stream()
        .map(ListGroupDTO::new)
        .collect(Collectors.toList());
  }

  // static 내부 클래스로 변경
  @Getter
  public static class ListGroupDTO {
    private Long groupId;
    private String groupName;
    private List<ListItemDTO> items;

    // 생성자 파라미터를 ListGroup으로 수정
    public ListGroupDTO(ListGroup listGroup) {
      this.groupId = listGroup.getId();
      this.groupName = listGroup.getGroupName();
      // ListItem을 ListItemDto로 변환하여 리스트 초기화
      this.items = listGroup.getListItems().stream()
          .map(ListItemDTO::new)
          .collect(Collectors.toList());
    }
  }

  // static 내부 클래스로 추가
  @Getter
  public static class ListItemDTO {
    private Long itemId;
    private String itemName;
    private Boolean isChecked;

    public ListItemDTO(ListItem listItem) {
      this.itemId = listItem.getId();
      this.itemName = listItem.getItemName();
      this.isChecked = listItem.getIsChecked();
    }
  }
}