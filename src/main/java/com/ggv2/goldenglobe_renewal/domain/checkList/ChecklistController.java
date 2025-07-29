package com.ggv2.goldenglobe_renewal.domain.checkList;

import com.ggv2.goldenglobe_renewal.domain.checkList.checkListDTO.ChecklistResponseDTO;
import com.ggv2.goldenglobe_renewal.domain.listGroup.listGroupDTO.ListGroupCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.listItem.listItemDTO.ListItemCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.listItem.listItemDTO.ListItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {
  private final ChecklistService checkListService;

  @PostMapping("/{checklistId}/groups")
  public ResponseEntity<String> addGroup(
      @PathVariable Long checklistId,
      @RequestBody ListGroupCreateRequest request
  ) {
    checkListService.addGroup(checklistId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body("그룹이 추가되었습니다.");
  }

  @PostMapping("/groups/{groupId}/items")
  public ResponseEntity<String> addItem(
      @PathVariable Long groupId,
      @RequestBody ListItemCreateRequest request) {
    checkListService.addItem(groupId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body("항목이 추가되었습니다.");
  }

  @GetMapping
  public ResponseEntity<ChecklistResponseDTO> getChecklistByTravelList(@RequestParam Long travelListId) {
    ChecklistResponseDTO response = checkListService.getChecklist(travelListId);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/items/{itemId}") // PATCH는 리소스의 일부만 수정할 때 주로 사용
  public ResponseEntity<String> updateItemStatus(
      @PathVariable Long itemId,
      @RequestBody ListItemUpdateRequest request) {
    checkListService.updateItemStatus(itemId, request);
    return ResponseEntity.ok("항목 상태가 변경되었습니다.");
  }

  @DeleteMapping("/groups/{groupId}")
  public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
    checkListService.deleteGroup(groupId);
    return ResponseEntity.ok("그룹이 삭제되었습니다.");
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
    checkListService.deleteItem(itemId);
    return ResponseEntity.ok("항목이 삭제되었습니다.");
  }
}
