package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.graph.models.DateTimeTimeZone;
import de.bbht.development.connector.service.dto.task.DateTimeTimeZoneDto;
import org.junit.jupiter.api.Test;

class DateTimeTimeZoneMapperTest {

  private static final String DATE_TIME = "2024-08-13T09:48:42.011434";

  @Test
  void shouldMapDateTimeTimeZoneToDto() {
    // given
    var dateTimeTimeZone = new DateTimeTimeZone();
    dateTimeTimeZone.setDateTime(DATE_TIME);
    dateTimeTimeZone.setTimeZone("UTC");

    // when
    var result = DateTimeTimeZoneMapper.mapDateTimeTimeZone(dateTimeTimeZone);

    // then
    assertThat(result).isNotNull()
                      .returns(DATE_TIME, DateTimeTimeZoneDto::getDateTime)
                      .returns("UTC", DateTimeTimeZoneDto::getTimeZone);
  }

  @Test
  void shouldMapNullDateTimeTimeZoneToNullDto() {
    // given
    var dateTimeTimeZone = (DateTimeTimeZone) null;

    // when
    var result = DateTimeTimeZoneMapper.mapDateTimeTimeZone(dateTimeTimeZone);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapDateTimeTimeZoneDtoToDateTimeTimeZone() {
    // given
    var dateTimeTimeZoneDto = new DateTimeTimeZoneDto();
    dateTimeTimeZoneDto.setDateTime(DATE_TIME);
    dateTimeTimeZoneDto.setTimeZone("UTC");

    // when
    var result = DateTimeTimeZoneMapper.mapDateTimeTimeZoneDto(dateTimeTimeZoneDto);

    // then
    assertThat(result).isNotNull()
                      .returns(DATE_TIME, DateTimeTimeZone::getDateTime)
                      .returns("UTC", DateTimeTimeZone::getTimeZone);
  }

  @Test
  void shouldMapNullDateTimeTimeZoneDtoToNull() {
    // given
    var dateTimeTimeZoneDto = (DateTimeTimeZoneDto) null;

    // when
    var result = DateTimeTimeZoneMapper.mapDateTimeTimeZoneDto(dateTimeTimeZoneDto);

    // then
    assertThat(result).isNull();
  }
}
