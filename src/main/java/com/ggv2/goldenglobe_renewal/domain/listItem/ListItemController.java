package com.ggv2.goldenglobe_renewal.domain.listItem;

import com.ggv2.goldenglobe_renewal.domain.checkList.ChecklistService;
import com.ggv2.goldenglobe_renewal.domain.listItem.listItemDTO.ListItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ListItemController {
  private final ChecklistService checklistService;

  // 항목 상태 수정 (체크/해제)
  @PatchMapping("/{itemId}")
  public ResponseEntity<String> updateItemStatus(
      @PathVariable Long itemId,
      @RequestBody ListItemUpdateRequest request) {
    checklistService.updateItemStatus(itemId, request);
    return ResponseEntity.ok("항목 상태가 변경되었습니다.");
  }

  // 항목 삭제
  @DeleteMapping("/{itemId}")
  public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
    checklistService.deleteItem(itemId);
    return ResponseEntity.ok("항목이 삭제되었습니다.");
  }
}
