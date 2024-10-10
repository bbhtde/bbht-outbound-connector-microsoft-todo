package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.RecurrenceRange;
import de.bbht.development.connector.service.dto.task.RecurrenceRangeDto;

public final class RecurrenceRangeMapper {

  private RecurrenceRangeMapper() {
    // private constructor to prevent instantiation
  }

  public static RecurrenceRangeDto mapRecurrenceRange(RecurrenceRange recurrenceRange) {
    RecurrenceRangeDto recurrenceRangeDto = null;
    if (recurrenceRange != null) {
      recurrenceRangeDto = new RecurrenceRangeDto();
      recurrenceRangeDto.setEndDate(recurrenceRange.getEndDate());
      recurrenceRangeDto.setNumberOfOccurrences(recurrenceRange.getNumberOfOccurrences());
      recurrenceRangeDto.setRecurrenceTimeZone(recurrenceRange.getRecurrenceTimeZone());
      recurrenceRangeDto.setStartDate(recurrenceRange.getStartDate());
      recurrenceRangeDto.setType(EnumMapper.mapRecurrenceRangeType(recurrenceRange.getType()));
    }
    return recurrenceRangeDto;
  }

  public static RecurrenceRange mapRecurrenceRangeDto(RecurrenceRangeDto recurrenceRangeDto) {
    RecurrenceRange recurrenceRange = null;
    if (recurrenceRangeDto != null) {
      recurrenceRange = new RecurrenceRange();
      recurrenceRange.setEndDate(recurrenceRangeDto.getEndDate());
      recurrenceRange.setNumberOfOccurrences(recurrenceRangeDto.getNumberOfOccurrences());
      recurrenceRange.setRecurrenceTimeZone(recurrenceRangeDto.getRecurrenceTimeZone());
      recurrenceRange.setStartDate(recurrenceRangeDto.getStartDate());
      recurrenceRange.setType(EnumMapper.mapRecurrenceRangeTypeDto(recurrenceRangeDto.getType()));
    }
    return recurrenceRange;
  }

}
