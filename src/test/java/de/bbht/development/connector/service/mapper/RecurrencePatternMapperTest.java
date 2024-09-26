package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.graph.models.DayOfWeek;
import com.microsoft.graph.models.RecurrencePattern;
import com.microsoft.graph.models.RecurrencePatternType;
import com.microsoft.graph.models.WeekIndex;
import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;
import de.bbht.development.connector.service.dto.task.RecurrencePatternDto;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

public class RecurrencePatternMapperTest {

  @Test
  void shouldMapRecurrencePattern() {
    // given
    var pattern = new RecurrencePattern();
    pattern.setDayOfMonth(20);
    pattern.setDaysOfWeek(List.of(DayOfWeek.Tuesday, DayOfWeek.Friday));
    pattern.setFirstDayOfWeek(DayOfWeek.Thursday);
    pattern.setIndex(WeekIndex.Second);
    pattern.setInterval(3);
    pattern.setMonth(3);
    pattern.setType(RecurrencePatternType.Daily);

    // when
    var result = RecurrencePatternMapper.mapRecurrencePattern(pattern);

    // then
    assertThat(result).returns(20, RecurrencePatternDto::getDayOfMonth)
        .returns(DayOfWeekDto.THURSDAY, RecurrencePatternDto::getFirstDayOfWeek)
        .returns(WeekIndexDto.SECOND, RecurrencePatternDto::getIndex)
        .returns(3, RecurrencePatternDto::getInterval)
        .returns(3, RecurrencePatternDto::getMonth)
        .returns(RecurrencePatternTypeDto.DAILY, RecurrencePatternDto::getType)
            .extracting(RecurrencePatternDto::getDaysOfWeek, as(InstanceOfAssertFactories.ITERABLE))
            .hasSize(2)
            .containsExactly(DayOfWeekDto.TUESDAY, DayOfWeekDto.FRIDAY);
//        .extracting(RecurrencePatternDto::getDaysOfWeek,  as(InstanceOfAssertFactories.LIST))
//        .hasSize(2)
//            .containsExactly(DayOfWeekDto.TUESDAY, DayOfWeekDto.FRIDAY);
//        .satisfiesExactly(dow1 -> assertThat(dow1).isNotNull()
//            .isEqualTo(DayOfWeekDto.TUESDAY), dow2 -> assertThat(dow2).isNotNull()
//            .isEqualTo(DayOfWeekDto.FRIDAY));
  }

  @Test
  void shouldMapNullRecurrencePattern() {
    // given
    var nullRecurrencePattern = (RecurrencePattern) null;

    // when
    var result= RecurrencePatternMapper.mapRecurrencePattern(nullRecurrencePattern);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapRecurrencePatternDto() {
    // given
    var patternDto = new RecurrencePatternDto();
    patternDto.setDayOfMonth(21);
    var daysOfWeek = new LinkedHashSet<DayOfWeekDto>();
    daysOfWeek.add(DayOfWeekDto.MONDAY);
    daysOfWeek.add(DayOfWeekDto.THURSDAY);
    patternDto.setDaysOfWeek(daysOfWeek);
    patternDto.setFirstDayOfWeek(DayOfWeekDto.WEDNESDAY);
    patternDto.setIndex(WeekIndexDto.THIRD);
    patternDto.setInterval(1);
    patternDto.setMonth(5);
    patternDto.setType(RecurrencePatternTypeDto.WEEKLY);

    // when
    var result = RecurrencePatternMapper.mapRecurrencePatternDto(patternDto);

    // then
    assertThat(result).returns(21, RecurrencePattern::getDayOfMonth)
        .returns(DayOfWeek.Wednesday, RecurrencePattern::getFirstDayOfWeek)
        .returns(WeekIndex.Third, RecurrencePattern::getIndex)
        .returns(1, RecurrencePattern::getInterval)
        .returns(5, RecurrencePattern::getMonth)
        .returns(RecurrencePatternType.Weekly, RecurrencePattern::getType)
        .extracting(RecurrencePattern::getDaysOfWeek, as(InstanceOfAssertFactories.LIST))
        .hasSize(2)
        .containsExactly(DayOfWeek.Monday, DayOfWeek.Thursday);
  }

  @Test
  void shouldMapNullRecurrencePatternDto() {
    // given
    var nullRecurrencePatternDto = (RecurrencePatternDto) null;

    // when
    var result= RecurrencePatternMapper.mapRecurrencePatternDto(nullRecurrencePatternDto);

    // then
    assertThat(result).isNull();
  }
}
