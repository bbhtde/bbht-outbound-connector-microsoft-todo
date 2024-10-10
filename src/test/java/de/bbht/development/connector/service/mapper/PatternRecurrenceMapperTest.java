package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.graph.models.DayOfWeek;
import com.microsoft.graph.models.PatternedRecurrence;
import com.microsoft.graph.models.RecurrencePattern;
import com.microsoft.graph.models.RecurrencePatternType;
import com.microsoft.graph.models.RecurrenceRange;
import com.microsoft.graph.models.RecurrenceRangeType;
import com.microsoft.graph.models.WeekIndex;
import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;
import de.bbht.development.connector.service.dto.task.PatternedRecurrenceDto;
import de.bbht.development.connector.service.dto.task.RecurrencePatternDto;
import de.bbht.development.connector.service.dto.task.RecurrenceRangeDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class PatternRecurrenceMapperTest {

  private final LocalDate NOW = LocalDate.now();

  @Test
  void shouldMapPatternRecurrence() {
    // given
    var pattern = new RecurrencePattern();
    pattern.setDayOfMonth(4);
    pattern.setDaysOfWeek(List.of(DayOfWeek.Tuesday, DayOfWeek.Friday));
    pattern.setFirstDayOfWeek(DayOfWeek.Thursday);
    pattern.setIndex(WeekIndex.Second);
    pattern.setInterval(3);
    pattern.setMonth(3);
    pattern.setType(RecurrencePatternType.Daily);

    var range = new RecurrenceRange();
    range.setType(RecurrenceRangeType.EndDate);
    range.setRecurrenceTimeZone("UTC");
    range.setEndDate(NOW.plusDays(5));
    range.setStartDate(NOW.minusDays(5));
    range.setNumberOfOccurrences(5);

    var patternRecurrence = new PatternedRecurrence();
    patternRecurrence.setPattern(pattern);
    patternRecurrence.setRange(range);

    // when
    var result = PatternedRecurrenceMapper.mapPatternedRecurrence(patternRecurrence);

    // then
    assertThat(result).isNotNull()
                      .satisfies(pr -> assertThat(result.getPattern()).isNotNull())
                      .satisfies(pr -> assertThat(result.getRange()).isNotNull());
  }

  @Test
  void shouldMapPatternRecurrenceDto() {
    // given
    var pattern = new RecurrencePatternDto();
    pattern.setDayOfMonth(4);
    pattern.setDaysOfWeek(Set.of(DayOfWeekDto.TUESDAY, DayOfWeekDto.FRIDAY));
    pattern.setFirstDayOfWeek(DayOfWeekDto.THURSDAY);
    pattern.setIndex(WeekIndexDto.SECOND);
    pattern.setInterval(3);
    pattern.setMonth(3);
    pattern.setType(RecurrencePatternTypeDto.DAILY);

    var range = new RecurrenceRangeDto();
    range.setType(RecurrenceRangeTypeDto.END_DATE);
    range.setRecurrenceTimeZone("UTC");
    range.setEndDate(NOW.plusDays(5));
    range.setStartDate(NOW.minusDays(5));
    range.setNumberOfOccurrences(5);

    var patternRecurrence = new PatternedRecurrenceDto();
    patternRecurrence.setPattern(pattern);
    patternRecurrence.setRange(range);

    // when
    var result = PatternedRecurrenceMapper.mapPatternedRecurrenceDto(patternRecurrence);

    // then
    assertThat(result).isNotNull()
                      .satisfies(pr -> assertThat(result.getPattern()).isNotNull())
                      .satisfies(pr -> assertThat(result.getRange()).isNotNull());
  }
}
