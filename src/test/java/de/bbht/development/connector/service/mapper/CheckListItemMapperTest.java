package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import org.junit.jupiter.api.Test;

public class CheckListItemMapperTest {

  @Test
  void shouldMapNullCreateUpdateCheckListItemDto() {
    // given
    var dto = (CreateUpdateCheckListItemDto)null;

    // when
    var result = CheckListItemMapper.mapCreateUpdateCheckListItemDto(dto);

    // then
    assertThat(result).isNull();
  }


}
