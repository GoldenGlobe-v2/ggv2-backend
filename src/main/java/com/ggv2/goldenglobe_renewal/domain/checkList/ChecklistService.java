package com.ggv2.goldenglobe_renewal.domain.checkList;

import com.ggv2.goldenglobe_renewal.domain.checkList.checkListDTO.ChecklistResponseDTO;
import com.ggv2.goldenglobe_renewal.domain.listGroup.ListGroup;
import com.ggv2.goldenglobe_renewal.domain.listGroup.ListGroupRepository;
import com.ggv2.goldenglobe_renewal.domain.listGroup.listGroupDTO.ListGroupCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.listItem.ListItem;
import com.ggv2.goldenglobe_renewal.domain.listItem.ListItemRepository;
import com.ggv2.goldenglobe_renewal.domain.listItem.listItemDTO.ListItemCreateRequest;
import com.ggv2.goldenglobe_renewal.domain.listItem.listItemDTO.ListItemUpdateRequest;
import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistService {

  private final ChecklistRepository checkListRepository;
  private final ListGroupRepository listGroupRepository;
  private final ListItemRepository listItemRepository;

  public void createChecklist(TravelList travelList) {
    CheckList checkList = new CheckList(travelList);
    checkListRepository.save(checkList);
  }

  public void addGroup(Long checklistId, ListGroupCreateRequest request) {
    CheckList checkList = checkListRepository.findById(checklistId)
        .orElseThrow(() -> new IllegalArgumentException("해당 체크리스트를 찾을 수 없습니다."));

    ListGroup listGroup = new ListGroup(request.groupName(), checkList);
    listGroupRepository.save(listGroup);
  }

  public void addItem(Long groupId, ListItemCreateRequest request) {
    ListGroup listGroup = listGroupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("해당 그룹을 찾을 수 없습니다."));

    ListItem listItem = new ListItem(request.itemName(), listGroup);
    listItemRepository.save(listItem);
  }

  public ChecklistResponseDTO getChecklist(Long travelListId) {
    CheckList checkList = checkListRepository.findByTravelListId(travelListId)
        .orElseThrow(() -> new IllegalArgumentException("체크리스트를 찾을 수 없습니다."));
    List<ListGroup> listGroups = listGroupRepository.findAllByCheckListWithItems(checkList);
    return new ChecklistResponseDTO(checkList, listGroups);
  }

  public void updateItemStatus(Long itemId, ListItemUpdateRequest request) {
    ListItem listItem = listItemRepository.findById(itemId)
        .orElseThrow(() -> new IllegalArgumentException("ListItem not found"));

    listItem.updateStatus(request.isChecked()); // 엔티티에 수정 메서드 추가 필요
  }

  public void deleteGroup(Long groupId) {
    listGroupRepository.deleteById(groupId);
  }

  public void deleteItem(Long itemId) {
    listItemRepository.deleteById(itemId);
  }
}
