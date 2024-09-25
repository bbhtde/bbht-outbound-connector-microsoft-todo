package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.PatternedRecurrence;
import de.bbht.development.connector.service.dto.task.PatternedRecurrenceDto;

public final class PatternedRecurrenceMapper {

  private PatternedRecurrenceMapper() {
    // private constructor to prevent instantiation.
  }

  public static PatternedRecurrenceDto mapPatternedRecurrence(PatternedRecurrence patternedRecurrence) {
    PatternedRecurrenceDto patternedRecurrenceDto = null;
    if (patternedRecurrence != null) {
      patternedRecurrenceDto = new PatternedRecurrenceDto();
      patternedRecurrenceDto.setPattern(RecurrencePatternMapper.mapRecurrencePattern(patternedRecurrence.getPattern()));
      patternedRecurrenceDto.setRange(RecurrenceRangeMapper.mapRecurrenceRange(patternedRecurrence.getRange()));
    }
    return patternedRecurrenceDto;
  }

  public static PatternedRecurrence mapPatternedRecurrenceDto(PatternedRecurrenceDto patternedRecurrenceDto) {
    PatternedRecurrence patternedRecurrence = null;
    if (patternedRecurrenceDto != null) {
      patternedRecurrence = new PatternedRecurrence();
      patternedRecurrence.setPattern(RecurrencePatternMapper.mapRecurrencePatternDto(patternedRecurrenceDto.getPattern()));
      patternedRecurrence.setRange(RecurrenceRangeMapper.mapRecurrenceRangeDto(patternedRecurrenceDto.getRange()));
    }
    return patternedRecurrence;
  }
}
