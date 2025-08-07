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

  // 특정 체크리스트에 그룹 추가
  @PostMapping("/{checklistId}/groups")
  public ResponseEntity<String> addGroup(
      @PathVariable Long checklistId,
      @RequestBody ListGroupCreateRequest request
  ) {
    checkListService.addGroup(checklistId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body("그룹이 추가되었습니다.");
  }

  // 특정 여행의 체크리스트 전체 조회
  @GetMapping
  public ResponseEntity<ChecklistResponseDTO> getChecklistByTravelList(@RequestParam Long travelListId) {
    ChecklistResponseDTO response = checkListService.getChecklist(travelListId);
    return ResponseEntity.ok(response);
  }
}
