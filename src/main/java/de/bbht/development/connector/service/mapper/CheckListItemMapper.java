package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.ChecklistItem;
import de.bbht.development.connector.service.dto.checklistitem.CheckListItemDto;
import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;

import java.util.List;

public final class CheckListItemMapper {

  private CheckListItemMapper() {
    // private constructor to prevent instantiation
  }

  public static List<CheckListItemDto> mapCheckListItems(List<ChecklistItem> listOfCheckListItems) {
    return listOfCheckListItems.stream()
        .map(CheckListItemMapper::mapCheckListItem)
        .toList();
  }

  public static CheckListItemDto mapCheckListItem(ChecklistItem checkListItem) {
    CheckListItemDto checkListItemDto = null;
    if (checkListItem != null) {
      checkListItemDto = new CheckListItemDto();
      checkListItemDto.setId(checkListItem.getId());
      checkListItemDto.setDisplayName(checkListItem.getDisplayName());
      checkListItemDto.setChecked(checkListItem.getIsChecked());
      checkListItemDto.setCheckedDateTime(checkListItem.getCheckedDateTime());
      checkListItemDto.setCreatedDateTime(checkListItem.getCreatedDateTime());
    }
    return checkListItemDto;
  }

  public static ChecklistItem mapCreateUpdateCheckListItemDto(
      CreateUpdateCheckListItemDto createUpdateCheckListItemDto) {
    ChecklistItem checkListItem = null;
    if (createUpdateCheckListItemDto != null) {
      checkListItem = new ChecklistItem();
      checkListItem.setDisplayName(createUpdateCheckListItemDto.getDisplayName());
      checkListItem.setIsChecked(createUpdateCheckListItemDto.getChecked());
    }
    return checkListItem;
  }
}
