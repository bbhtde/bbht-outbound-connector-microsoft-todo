package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.Importance;
import com.microsoft.graph.models.TaskStatus;
import com.microsoft.graph.models.WellknownListName;
import de.bbht.development.connector.service.dto.enums.ImportanceDto;
import de.bbht.development.connector.service.dto.enums.TaskStatusDto;
import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;

public class EnumMapper {

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
}
