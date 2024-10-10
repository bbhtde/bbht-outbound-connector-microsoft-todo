package de.bbht.development.connector.todo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;
import de.bbht.development.connector.todo.entity.TaskRecurrenceOptions;
import java.util.LinkedHashSet;
import java.util.stream.Stream;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OptionMapperTest {

  @Test
  void shouldNotMapNullCategories() {
    // given / when
    var result = OptionMapper.mapCategories(null);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapCategories() {
    // given
    var categories = "   Kategorie 1, Kategorie 2   ,    Kategorie 3,,  Kategorie 4  ,";

    // when
    var result = OptionMapper.mapCategories(categories);

    // then
    assertThat(result).asInstanceOf(InstanceOfAssertFactories.LIST)
                      .isNotNull()
                      .hasSize(4)
                      .containsExactly("Kategorie 1", "Kategorie 2", "Kategorie 3", "Kategorie 4");
  }

  @Test
  void shouldMapDaysOfWeek() {
    // given
    var daysOfWeek = "  Monday  ,, Tuesday , Friday,   Saturday,     Monday, Wednesday,  Thursday, ,   invalid day of week, Sunday   ,";

    // when
    var result = OptionMapper.mapDaysOfWeek(daysOfWeek);
    var expected = new LinkedHashSet<DayOfWeekDto>();
    expected.add(DayOfWeekDto.MONDAY);
    expected.add(DayOfWeekDto.TUESDAY);
    expected.add(DayOfWeekDto.FRIDAY);
    expected.add(DayOfWeekDto.SATURDAY);
    expected.add(DayOfWeekDto.WEDNESDAY);
    expected.add(DayOfWeekDto.THURSDAY);
    expected.add(DayOfWeekDto.SUNDAY);

    // then
    assertThat(result).asInstanceOf(InstanceOfAssertFactories.ITERABLE)
                      .isNotNull()
                      .hasSize(7)
                      .containsExactlyElementsOf(expected);
  }

  @Test
  void shouldMapNullLocalDate() {
    // given / when
    var result = OptionMapper.mapLocalDate(null);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapEmptyLocalDate() {
    // given / when
    var result = OptionMapper.mapLocalDate("");

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapInvalidLocalDate() {
    // given / when
    var result = OptionMapper.mapLocalDate("invalid");

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapNullDateTimeNullTimeZone() {
    // given / when
    var result = OptionMapper.mapDateTimeTimeZone(null, null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapNullDateTimeTimeZone() {
    // given / when
    var result = OptionMapper.mapDateTimeTimeZone(null, "UTC");

    assertThat(result).isNull();
  }

  @Test
  void shouldMapDateTimeNullTimeZone() {
    // given / when
    var result = OptionMapper.mapDateTimeTimeZone("2024-07-12T08:00:00.000000", null);

    assertThat(result).isNull();
  }

  @Test
  void shouldMapNullPatternRecurrence() {
    // given / when
    var result = OptionMapper.mapPatternedRecurrence(null);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapNullNonRecurringRecurrence() {
    // given
    var options = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_NON_RECURRING, null, null,
        null, null, null, null, null, null, null, null, null, null);
    // when
    var result = OptionMapper.mapPatternedRecurrence(options);

    // then
    assertThat(result).isNull();
  }

  @Test
  void shouldMapRecurrence() {
    // given
    var options = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_RECURRING, null, null, null,
        null, null, null, null, null, null, null, null, null);
    // when
    var result = OptionMapper.mapPatternedRecurrence(options);

    // then
    assertThat(result).isNotNull();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForMapPatternRecurrencePatternWithNonEmptyContent")
  void shouldMapPatternRecurrencePatternWithNonEmptyContent(RecurrencePatternTypeDto patternType,
      Integer dayOfMonth, String daysOfWeek, DayOfWeekDto firstDayOfWeek, WeekIndexDto index,
      Integer interval, Integer month) {
    // given
    var options = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_RECURRING, patternType,
        interval, dayOfMonth, daysOfWeek, firstDayOfWeek, index, month, null, null, null, null,
        null);

    // when
    var result = OptionMapper.mapPatternedRecurrence(options);

    // then
    assertThat(result).isNotNull();
  }

  private static Stream<Arguments> provideArgumentsForMapPatternRecurrencePatternWithNonEmptyContent() {
    return Stream.of(
        Arguments.of(RecurrencePatternTypeDto.WEEKLY, null, null, null, null, null, null),
        Arguments.of(null, 7, null, null, null, null, null),
        Arguments.of(null, null, "Tuesday    , Thursday", null, null, null, null),
        Arguments.of(null, null, null, DayOfWeekDto.MONDAY, null, null, null),
        Arguments.of(null, null, null, null, WeekIndexDto.FIRST, null, null),
        Arguments.of(null, null, null, null, null, 5, null),
        Arguments.of(null, null, null, null, null, null, 8));
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForMapPatternRecurrenceRangeWithNonEmptyContent")
  void shouldMapPatternRecurrenceRangeWithNonEmptyContent(RecurrenceRangeTypeDto rangeType,
      Integer numberOfOccurrences, String startDate, String endDate, String recurrenceTimeZone) {
    // given
    var options = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_RECURRING, null, null, null,
        null, null, null, null, rangeType, numberOfOccurrences, startDate, endDate,
        recurrenceTimeZone);

    // when
    var result = OptionMapper.mapPatternedRecurrence(options);

    // then
    assertThat(result).isNotNull();
  }

  private static Stream<Arguments> provideArgumentsForMapPatternRecurrenceRangeWithNonEmptyContent() {
    return Stream.of(Arguments.of(RecurrenceRangeTypeDto.NUMBERED, null, null, null, null),
        Arguments.of(null, 5, null, null, null), Arguments.of(null, null, "2024-07-01", null, null),
        Arguments.of(null, null, null, "2024-07-12", null),
        Arguments.of(null, null, null, null, "UTC"));
  }
}
