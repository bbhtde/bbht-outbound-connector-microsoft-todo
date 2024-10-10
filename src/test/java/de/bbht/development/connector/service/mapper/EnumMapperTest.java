package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.graph.models.DayOfWeek;
import com.microsoft.graph.models.Importance;
import com.microsoft.graph.models.RecurrencePatternType;
import com.microsoft.graph.models.RecurrenceRangeType;
import com.microsoft.graph.models.TaskStatus;
import com.microsoft.graph.models.WeekIndex;
import com.microsoft.graph.models.WellknownListName;
import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.ImportanceDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;
import de.bbht.development.connector.service.dto.enums.TaskStatusDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;
import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EnumMapperTest {

  @ParameterizedTest
  @MethodSource("provideArgumentsForImportanceEnum")
  void shouldMapImportanceEnumCorrectly(Importance importance, ImportanceDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapImportance(importance);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideArgumentsForImportanceEnum() {
    return Stream.of(Arguments.of(Importance.Low, ImportanceDto.LOW),
        Arguments.of(Importance.High, ImportanceDto.HIGH),
        Arguments.of(Importance.Normal, ImportanceDto.NORMAL), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForImportanceDtoEnum")
  void shouldMapImportanceDtoCorrectly(ImportanceDto importanceDto, Importance expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapImportanceDto(importanceDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideArgumentsForImportanceDtoEnum() {
    return Stream.of(Arguments.of(ImportanceDto.LOW, Importance.Low),
        Arguments.of(ImportanceDto.HIGH, Importance.High),
        Arguments.of(ImportanceDto.NORMAL, Importance.Normal), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideWellknownListNameCorrectly")
  void shouldMapWellknownListNameCorrectly(WellknownListName wellknownListName,
      WellknownListNameDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapWellknownListName(wellknownListName);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideWellknownListNameCorrectly() {
    return Stream.of(Arguments.of(WellknownListName.DefaultList, WellknownListNameDto.DEFAULT_LIST),
        Arguments.of(WellknownListName.None, WellknownListNameDto.NONE),
        Arguments.of(WellknownListName.FlaggedEmails, WellknownListNameDto.FLAGGED_EMAILS),
        Arguments.of(WellknownListName.UnknownFutureValue,
            WellknownListNameDto.UNKNOWN_FUTURE_VALUE), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideWellknownListNameDtoCorrectly")
  void shouldMapWellknownListNameDtoCorrectly(WellknownListNameDto wellknownListNameDto,
      WellknownListName expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapWellknownListNameDto(wellknownListNameDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideWellknownListNameDtoCorrectly() {
    return Stream.of(Arguments.of(WellknownListNameDto.DEFAULT_LIST, WellknownListName.DefaultList),
        Arguments.of(WellknownListNameDto.NONE, WellknownListName.None),
        Arguments.of(WellknownListNameDto.FLAGGED_EMAILS, WellknownListName.FlaggedEmails),
        Arguments.of(WellknownListNameDto.UNKNOWN_FUTURE_VALUE,
            WellknownListName.UnknownFutureValue), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideTaskStatusCorrectly")
  void shouldMapTaskStatusCorrectly(TaskStatus taskStatus, TaskStatusDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapTaskStatus(taskStatus);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideTaskStatusCorrectly() {
    return Stream.of(Arguments.of(TaskStatus.NotStarted, TaskStatusDto.NOT_STARTED),
        Arguments.of(TaskStatus.InProgress, TaskStatusDto.IN_PROGRESS),
        Arguments.of(TaskStatus.Completed, TaskStatusDto.COMPLETED),
        Arguments.of(TaskStatus.Deferred, TaskStatusDto.DEFERRED),
        Arguments.of(TaskStatus.WaitingOnOthers, TaskStatusDto.WAITING_ON_OTHERS),
        Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideTaskStatusDtoCorrectly")
  void shouldMapTaskStatusDtoCorrectly(TaskStatusDto taskStatusDto, TaskStatus expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapTaskStatusDto(taskStatusDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideTaskStatusDtoCorrectly() {
    return Stream.of(Arguments.of(TaskStatusDto.NOT_STARTED, TaskStatus.NotStarted),
        Arguments.of(TaskStatusDto.IN_PROGRESS, TaskStatus.InProgress),
        Arguments.of(TaskStatusDto.COMPLETED, TaskStatus.Completed),
        Arguments.of(TaskStatusDto.DEFERRED, TaskStatus.Deferred),
        Arguments.of(TaskStatusDto.WAITING_ON_OTHERS, TaskStatus.WaitingOnOthers),
        Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideRecurrencePatternTypeCorrectly")
  void shouldMapRecurrencePatternTypeCorrectly(RecurrencePatternType recurrencePatternType,
      RecurrencePatternTypeDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapRecurrencePatternType(recurrencePatternType);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideRecurrencePatternTypeCorrectly() {
    return Stream.of(Arguments.of(RecurrencePatternType.Daily, RecurrencePatternTypeDto.DAILY),
        Arguments.of(RecurrencePatternType.Weekly, RecurrencePatternTypeDto.WEEKLY),
        Arguments.of(RecurrencePatternType.AbsoluteMonthly,
            RecurrencePatternTypeDto.ABSOLUTE_MONTHLY),
        Arguments.of(RecurrencePatternType.AbsoluteYearly,
            RecurrencePatternTypeDto.ABSOLUTE_YEARLY),
        Arguments.of(RecurrencePatternType.RelativeMonthly,
            RecurrencePatternTypeDto.RELATIVE_MONTHLY),
        Arguments.of(RecurrencePatternType.RelativeYearly,
            RecurrencePatternTypeDto.RELATIVE_YEARLY), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideRecurrencePatternTypeDtoCorrectly")
  void shouldMapRecurrencePatternTypeDtoCorrectly(RecurrencePatternTypeDto recurrencePatternTypeDto,
      RecurrencePatternType expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapRecurrencePatternTypeDto(recurrencePatternTypeDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideRecurrencePatternTypeDtoCorrectly() {
    return Stream.of(Arguments.of(RecurrencePatternTypeDto.DAILY, RecurrencePatternType.Daily),
        Arguments.of(RecurrencePatternTypeDto.WEEKLY, RecurrencePatternType.Weekly),
        Arguments.of(RecurrencePatternTypeDto.ABSOLUTE_MONTHLY,
            RecurrencePatternType.AbsoluteMonthly),
        Arguments.of(RecurrencePatternTypeDto.ABSOLUTE_YEARLY,
            RecurrencePatternType.AbsoluteYearly),
        Arguments.of(RecurrencePatternTypeDto.RELATIVE_MONTHLY,
            RecurrencePatternType.RelativeMonthly),
        Arguments.of(RecurrencePatternTypeDto.RELATIVE_YEARLY,
            RecurrencePatternType.RelativeYearly), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideRecurrenceRangeTypeCorrectly")
  void shouldMapRecurrenceRangeTypeCorrectly(RecurrenceRangeType recurrenceRangeType,
      RecurrenceRangeTypeDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapRecurrenceRangeType(recurrenceRangeType);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideRecurrenceRangeTypeCorrectly() {
    return Stream.of(Arguments.of(RecurrenceRangeType.EndDate, RecurrenceRangeTypeDto.END_DATE),
        Arguments.of(RecurrenceRangeType.NoEnd, RecurrenceRangeTypeDto.NO_END),
        Arguments.of(RecurrenceRangeType.Numbered, RecurrenceRangeTypeDto.NUMBERED),
        Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideRecurrenceRangeTypeDtoCorrectly")
  void shouldMapRecurrenceRangeTypeDtoCorrectly(RecurrenceRangeTypeDto recurrenceRangeTypeDto,
      RecurrenceRangeType expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapRecurrenceRangeTypeDto(recurrenceRangeTypeDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideRecurrenceRangeTypeDtoCorrectly() {
    return Stream.of(Arguments.of(RecurrenceRangeTypeDto.END_DATE, RecurrenceRangeType.EndDate),
        Arguments.of(RecurrenceRangeTypeDto.NO_END, RecurrenceRangeType.NoEnd),
        Arguments.of(RecurrenceRangeTypeDto.NUMBERED, RecurrenceRangeType.Numbered),
        Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideDayOfWeekCorrectly")
  void shouldMapDayOfWeekCorrectly(DayOfWeek dayOfWeek, DayOfWeekDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapDayOfWeek(dayOfWeek);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideDayOfWeekCorrectly() {
    return Stream.of(Arguments.of(DayOfWeek.Monday, DayOfWeekDto.MONDAY),
        Arguments.of(DayOfWeek.Tuesday, DayOfWeekDto.TUESDAY),
        Arguments.of(DayOfWeek.Wednesday, DayOfWeekDto.WEDNESDAY),
        Arguments.of(DayOfWeek.Thursday, DayOfWeekDto.THURSDAY),
        Arguments.of(DayOfWeek.Friday, DayOfWeekDto.FRIDAY),
        Arguments.of(DayOfWeek.Saturday, DayOfWeekDto.SATURDAY),
        Arguments.of(DayOfWeek.Sunday, DayOfWeekDto.SUNDAY), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideDayOfWeekDtoCorrectly")
  void shouldMapDayOfWeekDtoCorrectly(DayOfWeekDto dayOfWeekDto, DayOfWeek expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapDayOfWeekDto(dayOfWeekDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideDayOfWeekDtoCorrectly() {
    return Stream.of(Arguments.of(DayOfWeekDto.MONDAY, DayOfWeek.Monday),
        Arguments.of(DayOfWeekDto.TUESDAY, DayOfWeek.Tuesday),
        Arguments.of(DayOfWeekDto.WEDNESDAY, DayOfWeek.Wednesday),
        Arguments.of(DayOfWeekDto.THURSDAY, DayOfWeek.Thursday),
        Arguments.of(DayOfWeekDto.FRIDAY, DayOfWeek.Friday),
        Arguments.of(DayOfWeekDto.SATURDAY, DayOfWeek.Saturday),
        Arguments.of(DayOfWeekDto.SUNDAY, DayOfWeek.Sunday), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideWeekIndexCorrectly")
  void shouldMapWeekIndexCorrectly(WeekIndex weekIndex, WeekIndexDto expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapWeekIndex(weekIndex);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideWeekIndexCorrectly() {
    return Stream.of(Arguments.of(WeekIndex.First, WeekIndexDto.FIRST),
        Arguments.of(WeekIndex.Second, WeekIndexDto.SECOND),
        Arguments.of(WeekIndex.Third, WeekIndexDto.THIRD),
        Arguments.of(WeekIndex.Fourth, WeekIndexDto.FOURTH),
        Arguments.of(WeekIndex.Last, WeekIndexDto.LAST), Arguments.of(null, null));
  }

  @ParameterizedTest
  @MethodSource("provideWeekIndexDtoCorrectly")
  void shouldMapWeekIndexDtoCorrectly(WeekIndexDto weekIndexDto, WeekIndex expected) {
    // given: from method parameter

    // when
    var result = EnumMapper.mapWeekIndexDto(weekIndexDto);

    // then
    assertEquals(expected, result);
  }

  private static Stream<Arguments> provideWeekIndexDtoCorrectly() {
    return Stream.of(Arguments.of(WeekIndexDto.FIRST, WeekIndex.First),
        Arguments.of(WeekIndexDto.SECOND, WeekIndex.Second),
        Arguments.of(WeekIndexDto.THIRD, WeekIndex.Third),
        Arguments.of(WeekIndexDto.FOURTH, WeekIndex.Fourth),
        Arguments.of(WeekIndexDto.LAST, WeekIndex.Last), Arguments.of(null, null));
  }

  @Test
  void shouldMapListOfDayOfWeekCorrectly() {
    // given
    var listOfDayOfWeeks = List.of(DayOfWeek.Sunday, DayOfWeek.Friday, DayOfWeek.Wednesday,
        DayOfWeek.Monday, DayOfWeek.Tuesday, DayOfWeek.Thursday, DayOfWeek.Saturday);

    // when
    var result = EnumMapper.mapDaysOfWeek(listOfDayOfWeeks);

    // then
    var expected = Set.of(DayOfWeekDto.SUNDAY, DayOfWeekDto.FRIDAY, DayOfWeekDto.WEDNESDAY,
        DayOfWeekDto.MONDAY, DayOfWeekDto.TUESDAY, DayOfWeekDto.THURSDAY, DayOfWeekDto.SATURDAY);
    assertEquals(expected, result);
  }

  @Test
  void shouldMapNullDaysOfWeekCorrectly() {
    // given / when
    var result = EnumMapper.mapDaysOfWeek(null);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapListOfDayOfWeekDtoCorrectly() {
    // given
    var setOfDayOfWeekDtos = new LinkedHashSet<DayOfWeekDto>();
    setOfDayOfWeekDtos.add(DayOfWeekDto.SUNDAY);
    setOfDayOfWeekDtos.add(DayOfWeekDto.FRIDAY);
    setOfDayOfWeekDtos.add(DayOfWeekDto.WEDNESDAY);
    setOfDayOfWeekDtos.add(DayOfWeekDto.MONDAY);
    setOfDayOfWeekDtos.add(DayOfWeekDto.TUESDAY);
    setOfDayOfWeekDtos.add(DayOfWeekDto.THURSDAY);
    setOfDayOfWeekDtos.add(DayOfWeekDto.SATURDAY);

    // when
    var result = EnumMapper.mapDaysOfWeekDto(setOfDayOfWeekDtos);

    // then
    var expected = List.of(DayOfWeek.Sunday, DayOfWeek.Friday, DayOfWeek.Wednesday,
        DayOfWeek.Monday, DayOfWeek.Tuesday, DayOfWeek.Thursday, DayOfWeek.Saturday);
    assertEquals(expected, result);
  }

  @Test
  void shouldMapNullDaysOfWeekDtoCorrectly() {
    // given / when
    var result = EnumMapper.mapDaysOfWeekDto(null);

    // then
    assertThat(result).isNull();
  }
}
