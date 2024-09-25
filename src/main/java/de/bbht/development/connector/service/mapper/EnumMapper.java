package de.bbht.development.connector.service.mapper;

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
import java.util.ArrayList;
import java.util.List;

public final class EnumMapper {

  private EnumMapper() {
    // private constructor to prevent instantiation.
  }

  public static Importance mapImportanceDto(ImportanceDto dto) {
    return switch (dto) {
      case LOW -> Importance.Low;
      case HIGH -> Importance.High;
      case NORMAL -> Importance.Normal;
      case null -> null;
    };
  }

  public static ImportanceDto mapImportance(Importance importance) {
    return switch (importance) {
      case Low -> ImportanceDto.LOW;
      case High -> ImportanceDto.HIGH;
      case Normal -> ImportanceDto.NORMAL;
      case null, default -> null;
    };
  }

  public static WellknownListName mapWellknownListNameDto(WellknownListNameDto dto) {
    return switch (dto) {
      case DEFAULT_LIST -> WellknownListName.DefaultList;
      case FLAGGED_EMAILS -> WellknownListName.FlaggedEmails;
      case NONE -> WellknownListName.None;
      case UNKNOWN_FUTURE_VALUE -> WellknownListName.UnknownFutureValue;
      case null -> null;
    };
  }

  public static WellknownListNameDto mapWellknownListName(WellknownListName wellknownListName) {
    return switch (wellknownListName) {
      case DefaultList -> WellknownListNameDto.DEFAULT_LIST;
      case FlaggedEmails -> WellknownListNameDto.FLAGGED_EMAILS;
      case None -> WellknownListNameDto.NONE;
      case UnknownFutureValue -> WellknownListNameDto.UNKNOWN_FUTURE_VALUE;
      case null -> null;
    };
  }

  public static TaskStatus mapTaskStatusDto(TaskStatusDto dto) {
    return switch (dto) {
      case COMPLETED -> TaskStatus.Completed;
      case IN_PROGRESS -> TaskStatus.InProgress;
      case DEFERRED -> TaskStatus.Deferred;
      case NOT_STARTED -> TaskStatus.NotStarted;
      case WAITING_ON_OTHERS -> TaskStatus.WaitingOnOthers;
      case null, default -> null;
    };
  }

  public static TaskStatusDto mapTaskStatus(TaskStatus status) {
    return switch (status) {
      case Completed -> TaskStatusDto.COMPLETED;
      case InProgress -> TaskStatusDto.IN_PROGRESS;
      case Deferred -> TaskStatusDto.DEFERRED;
      case NotStarted -> TaskStatusDto.NOT_STARTED;
      case WaitingOnOthers -> TaskStatusDto.WAITING_ON_OTHERS;
      case null, default -> null;
    };
  }

  public static RecurrencePatternType mapRecurrencePatternTypeDto(RecurrencePatternTypeDto dto) {
    return switch(dto) {
      case DAILY -> RecurrencePatternType.Daily;
      case WEEKLY -> RecurrencePatternType.Weekly;
      case RELATIVE_MONTHLY -> RecurrencePatternType.RelativeMonthly;
      case RELATIVE_YEARLY -> RecurrencePatternType.RelativeYearly;
      case ABSOLUTE_MONTHLY -> RecurrencePatternType.AbsoluteMonthly;
      case ABSOLUTE_YEARLY -> RecurrencePatternType.AbsoluteYearly;
      case null -> null;
    };
  }

  public static RecurrencePatternTypeDto mapRecurrencePatternType(RecurrencePatternType recurrencePatternType) {
    return switch (recurrencePatternType) {
      case Daily -> RecurrencePatternTypeDto.DAILY;
      case Weekly -> RecurrencePatternTypeDto.WEEKLY;
      case RelativeMonthly -> RecurrencePatternTypeDto.RELATIVE_MONTHLY;
      case RelativeYearly -> RecurrencePatternTypeDto.RELATIVE_YEARLY;
      case AbsoluteMonthly -> RecurrencePatternTypeDto.ABSOLUTE_MONTHLY;
      case AbsoluteYearly -> RecurrencePatternTypeDto.ABSOLUTE_YEARLY;
      case null -> null;
    };
  }

  public static RecurrenceRangeType mapRecurrenceRangeTypeDto(RecurrenceRangeTypeDto dto) {
    return switch (dto) {
      case END_DATE -> RecurrenceRangeType.EndDate;
      case NO_END -> RecurrenceRangeType.NoEnd;
      case NUMBERED -> RecurrenceRangeType.Numbered;
      case null -> null;
    };
  }

  public static RecurrenceRangeTypeDto mapRecurrenceRangeType(RecurrenceRangeType recurrenceRangeType) {
    return switch(recurrenceRangeType) {
      case EndDate -> RecurrenceRangeTypeDto.END_DATE;
      case NoEnd -> RecurrenceRangeTypeDto.NO_END;
      case Numbered -> RecurrenceRangeTypeDto.NUMBERED;
      case null -> null;
    };
  }

  public static DayOfWeek mapDayOfWeekDto(DayOfWeekDto dto) {
    return switch (dto) {
      case MONDAY -> DayOfWeek.Monday;
      case TUESDAY -> DayOfWeek.Tuesday;
      case WEDNESDAY -> DayOfWeek.Wednesday;
      case THURSDAY -> DayOfWeek.Thursday;
      case FRIDAY -> DayOfWeek.Friday;
      case SATURDAY -> DayOfWeek.Saturday;
      case SUNDAY -> DayOfWeek.Sunday;
      case null -> null;
    };
  }

  public static DayOfWeekDto mapDayOfWeek(DayOfWeek dayOfWeek) {
    return switch (dayOfWeek) {
      case Monday -> DayOfWeekDto.MONDAY;
      case Tuesday -> DayOfWeekDto.TUESDAY;
      case Wednesday -> DayOfWeekDto.WEDNESDAY;
      case Thursday -> DayOfWeekDto.THURSDAY;
      case Friday -> DayOfWeekDto.FRIDAY;
      case Saturday -> DayOfWeekDto.SATURDAY;
      case Sunday -> DayOfWeekDto.SUNDAY;
      case null -> null;
    };
  }

  public static WeekIndex mapWeekIndexDto(WeekIndexDto dto) {
    return switch (dto) {
      case FIRST -> WeekIndex.First;
      case SECOND -> WeekIndex.Second;
      case THIRD -> WeekIndex.Third;
      case FOURTH -> WeekIndex.Fourth;
      case LAST -> WeekIndex.Last;
      case null -> null;
    };
  }

  public static WeekIndexDto mapWeekIndex(WeekIndex weekIndex) {
    return switch (weekIndex) {
      case First -> WeekIndexDto.FIRST;
      case Second -> WeekIndexDto.SECOND;
      case Third -> WeekIndexDto.THIRD;
      case Fourth -> WeekIndexDto.FOURTH;
      case Last -> WeekIndexDto.LAST;
      case null -> null;
    };
  }

  public static List<DayOfWeekDto> mapDaysOfWeek(List<DayOfWeek> daysOfWeek) {
    List<DayOfWeekDto> daysOfWeekDto = null;
    if (daysOfWeek != null) {
      daysOfWeekDto = new ArrayList<>();
      for (DayOfWeek dayOfWeek : daysOfWeek) {
        daysOfWeekDto.add(mapDayOfWeek(dayOfWeek));
      }
    }
    return daysOfWeekDto;
  }

  public static List<DayOfWeek> mapDaysOfWeekDto(List<DayOfWeekDto> daysOfWeekDto) {
    List<DayOfWeek> daysOfWeek = null;
    if (daysOfWeekDto != null) {
      daysOfWeek = new ArrayList<>();
      for (DayOfWeekDto dayOfWeekDto : daysOfWeekDto) {
        daysOfWeek.add(mapDayOfWeekDto(dayOfWeekDto));
      }
    }
    return daysOfWeek;
  }
}
