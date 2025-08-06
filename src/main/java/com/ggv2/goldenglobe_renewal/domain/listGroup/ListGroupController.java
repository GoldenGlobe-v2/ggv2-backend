package com.ggv2.goldenglobe_renewal.domain.listGroup;

import com.ggv2.goldenglobe_renewal.domain.checkList.ChecklistService;
import com.ggv2.goldenglobe_renewal.domain.listItem.ListItem;
import com.ggv2.goldenglobe_renewal.domain.listItem.listItemDTO.ListItemCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ListGroupController {
  private final ChecklistService checkListService;

  // 특정 그룹에 아이템 추가
  @PostMapping("/{groupId}/items")
  public ResponseEntity<String> addItem(
      @PathVariable Long groupId,
      @RequestBody ListItemCreateRequest request
      ) {
    checkListService.addItem(groupId, request);

    return ResponseEntity.status(HttpStatus.CREATED).body("항목이 추가되었습니다.");
  }

  @DeleteMapping("/{groupId}")
  public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
    checkListService.deleteGroup(groupId);
    return ResponseEntity.ok("그룹이 삭제되었습니다.");
  }
}
