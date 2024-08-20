package de.bbht.development.connector.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.graph.models.Importance;
import com.microsoft.graph.models.TaskStatus;
import com.microsoft.graph.models.WellknownListName;
import de.bbht.development.connector.service.dto.enums.ImportanceDto;
import de.bbht.development.connector.service.dto.enums.TaskStatusDto;
import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;
import java.util.stream.Stream;
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

}
