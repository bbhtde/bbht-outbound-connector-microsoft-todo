package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.RecurrencePattern;
import de.bbht.development.connector.service.dto.task.RecurrencePatternDto;

public final class RecurrencePatternMapper {

  private RecurrencePatternMapper() {
    // private constructor to prevent instantiation
  }

  public static RecurrencePatternDto mapRecurrencePattern(RecurrencePattern recurrencePattern) {
    RecurrencePatternDto recurrencePatternDto = null;
    if (recurrencePattern != null) {
      recurrencePatternDto = new RecurrencePatternDto();
      recurrencePatternDto.setDayOfMonth(recurrencePattern.getDayOfMonth());
      recurrencePatternDto.setDaysOfWeek(
          EnumMapper.mapDaysOfWeek(recurrencePattern.getDaysOfWeek()));
      recurrencePatternDto.setFirstDayOfWeek(
          EnumMapper.mapDayOfWeek(recurrencePattern.getFirstDayOfWeek()));
      recurrencePatternDto.setIndex(EnumMapper.mapWeekIndex(recurrencePattern.getIndex()));
      recurrencePatternDto.setInterval(recurrencePattern.getInterval());
      recurrencePatternDto.setMonth(recurrencePattern.getMonth());
      recurrencePatternDto.setType(
          EnumMapper.mapRecurrencePatternType(recurrencePattern.getType()));
    }
    return recurrencePatternDto;
  }

  public static RecurrencePattern mapRecurrencePatternDto(
      RecurrencePatternDto recurrencePatternDto) {
    RecurrencePattern recurrencePattern = null;
    if (recurrencePatternDto != null) {
      recurrencePattern = new RecurrencePattern();
      recurrencePattern.setDayOfMonth(recurrencePatternDto.getDayOfMonth());
      recurrencePattern.setDaysOfWeek(
          EnumMapper.mapDaysOfWeekDto(recurrencePatternDto.getDaysOfWeek()));
      recurrencePattern.setFirstDayOfWeek(
          EnumMapper.mapDayOfWeekDto(recurrencePatternDto.getFirstDayOfWeek()));
      recurrencePattern.setIndex(EnumMapper.mapWeekIndexDto(recurrencePatternDto.getIndex()));
      recurrencePattern.setInterval(recurrencePatternDto.getInterval());
      recurrencePattern.setMonth(recurrencePatternDto.getMonth());
      recurrencePattern.setType(
          EnumMapper.mapRecurrencePatternTypeDto(recurrencePatternDto.getType()));
    }
    return recurrencePattern;
  }
}
