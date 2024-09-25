package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.DateTimeTimeZone;
import de.bbht.development.connector.service.dto.task.DateTimeTimeZoneDto;

public final class DateTimeTimeZoneMapper {

  private DateTimeTimeZoneMapper() {
    // private constructor to prevent instantiation.
  }

  public static DateTimeTimeZone mapDateTimeTimeZoneDto(DateTimeTimeZoneDto dto) {
    DateTimeTimeZone dateTimeTimeZone = null;
    if (dto != null) {
      dateTimeTimeZone = new DateTimeTimeZone();
      dateTimeTimeZone.setDateTime(dto.getDateTime());
      dateTimeTimeZone.setTimeZone(dto.getTimeZone());
    }
    return dateTimeTimeZone;
  }

  public static DateTimeTimeZoneDto mapDateTimeTimeZone(DateTimeTimeZone dateTimeTimeZone) {
    DateTimeTimeZoneDto dateTimeTimeZoneDto = null;
    if (dateTimeTimeZone != null) {
      dateTimeTimeZoneDto = new DateTimeTimeZoneDto();
      dateTimeTimeZoneDto.setDateTime(dateTimeTimeZone.getDateTime());
      dateTimeTimeZoneDto.setTimeZone(dateTimeTimeZone.getTimeZone());
    }
    return dateTimeTimeZoneDto;
  }
}
