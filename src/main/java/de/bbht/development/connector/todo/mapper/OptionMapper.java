package de.bbht.development.connector.todo.mapper;

import static java.util.function.Predicate.not;

import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.DateTimeTimeZoneDto;
import de.bbht.development.connector.service.dto.task.PatternedRecurrenceDto;
import de.bbht.development.connector.service.dto.task.RecurrencePatternDto;
import de.bbht.development.connector.service.dto.task.RecurrenceRangeDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.todo.entity.CheckListItemOptions;
import de.bbht.development.connector.todo.entity.TaskListOptions;
import de.bbht.development.connector.todo.entity.TaskOptions;
import de.bbht.development.connector.todo.entity.TaskRecurrenceOptions;
import de.bbht.development.connector.todo.entity.UpdateCheckListItemOptions;
import de.bbht.development.connector.todo.entity.UpdateTaskListOptions;
import de.bbht.development.connector.todo.entity.UpdateTaskOptions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class OptionMapper {

  private OptionMapper() {
    // private constructor to prevent instantiation.
  }

  public static CreateUpdateTaskListDto mapFromTaskListOptions(TaskListOptions taskListOptions) {
    final CreateUpdateTaskListDto createUpdateTaskListDto = new CreateUpdateTaskListDto();
    createUpdateTaskListDto.setDisplayName(taskListOptions.displayName());
    return createUpdateTaskListDto;
  }

  public static CreateUpdateTaskListDto mapFromUpdateTaskListOptions(
      UpdateTaskListOptions updateTaskListOptions) {
    final CreateUpdateTaskListDto createUpdateTaskListDto = new CreateUpdateTaskListDto();
    createUpdateTaskListDto.setDisplayName(updateTaskListOptions.displayName());
    return createUpdateTaskListDto;
  }

  public static CreateUpdateTaskDto mapFromTaskOptions(TaskOptions taskOptions,
      TaskRecurrenceOptions taskRecurrenceOptions) {
    final CreateUpdateTaskDto createUpdateTaskDto = new CreateUpdateTaskDto();
    createUpdateTaskDto.setTitle(taskOptions.title());
    createUpdateTaskDto.setBody(taskOptions.body());
    createUpdateTaskDto.setImportance(taskOptions.importance());
    createUpdateTaskDto.setStatus(taskOptions.status());
    createUpdateTaskDto.setCategories(mapCategories(taskOptions.categories()));
    createUpdateTaskDto.setStartDateTime(
        mapDateTimeTimeZone(taskOptions.startDateTime(), taskOptions.startDateTimeTimeZone()));
    createUpdateTaskDto.setDueDateTime(
        mapDateTimeTimeZone(taskOptions.dueDateTime(), taskOptions.startDateTimeTimeZone()));
    createUpdateTaskDto.setCompletedDateTime(mapDateTimeTimeZone(taskOptions.completedDateTime(),
        taskOptions.completedDateTimeTimeZone()));
    createUpdateTaskDto.setRecurrence(mapPatternedRecurrence(taskRecurrenceOptions));
    createUpdateTaskDto.setReminderDateTime(mapDateTimeTimeZone(taskOptions.reminderDateTime(),
        taskOptions.reminderDateTimeTimeZone()));
    createUpdateTaskDto.setReminderOn(taskOptions.reminderOn());
    return createUpdateTaskDto;
  }

  public static CreateUpdateTaskDto mapFromUpdateTaskOptions(UpdateTaskOptions updateTaskOptions,
      TaskRecurrenceOptions taskRecurrenceOptions) {
    final CreateUpdateTaskDto createUpdateTaskDto = new CreateUpdateTaskDto();
    createUpdateTaskDto.setTitle(updateTaskOptions.title());
    createUpdateTaskDto.setBody(updateTaskOptions.body());
    createUpdateTaskDto.setImportance(updateTaskOptions.importance());
    createUpdateTaskDto.setStatus(updateTaskOptions.status());
    createUpdateTaskDto.setCategories(mapCategories(updateTaskOptions.categories()));
    createUpdateTaskDto.setStartDateTime(mapDateTimeTimeZone(updateTaskOptions.startDateTime(),
        updateTaskOptions.startDateTimeTimeZone()));
    createUpdateTaskDto.setDueDateTime(mapDateTimeTimeZone(updateTaskOptions.dueDateTime(),
        updateTaskOptions.startDateTimeTimeZone()));
    createUpdateTaskDto.setCompletedDateTime(
        mapDateTimeTimeZone(updateTaskOptions.completedDateTime(),
            updateTaskOptions.completedDateTimeTimeZone()));
    createUpdateTaskDto.setRecurrence(mapPatternedRecurrence(taskRecurrenceOptions));
    createUpdateTaskDto.setReminderDateTime(
        mapDateTimeTimeZone(updateTaskOptions.reminderDateTime(),
            updateTaskOptions.reminderDateTimeTimeZone()));
    createUpdateTaskDto.setReminderOn(updateTaskOptions.reminderOn());
    return createUpdateTaskDto;
  }

  public static PatternedRecurrenceDto mapPatternedRecurrence(
      TaskRecurrenceOptions taskRecurrenceOptions) {
    if (taskRecurrenceOptions != null && "RECURRING".equals(taskRecurrenceOptions.recurring())) {
      // only map if recurrence options are available and set to RECURRING
      final PatternedRecurrenceDto patternedRecurrenceDto = new PatternedRecurrenceDto();

      if (taskRecurrenceOptions.patternType() != null || taskRecurrenceOptions.dayOfMonth() != null
          || taskRecurrenceOptions.daysOfWeek() != null
          || taskRecurrenceOptions.firstDayOfWeek() != null || taskRecurrenceOptions.index() != null
          || taskRecurrenceOptions.interval() != null || taskRecurrenceOptions.month() != null) {
        final RecurrencePatternDto recurrencePatternDto = new RecurrencePatternDto();
        recurrencePatternDto.setType(taskRecurrenceOptions.patternType());
        recurrencePatternDto.setDayOfMonth(taskRecurrenceOptions.dayOfMonth());
        recurrencePatternDto.setDaysOfWeek(
            OptionMapper.mapDaysOfWeek(taskRecurrenceOptions.daysOfWeek()));
        recurrencePatternDto.setFirstDayOfWeek(taskRecurrenceOptions.firstDayOfWeek());
        recurrencePatternDto.setIndex(taskRecurrenceOptions.index());
        recurrencePatternDto.setInterval(taskRecurrenceOptions.interval());
        recurrencePatternDto.setMonth(taskRecurrenceOptions.month());
        patternedRecurrenceDto.setPattern(recurrencePatternDto);
      }

      if (taskRecurrenceOptions.rangeType() != null || taskRecurrenceOptions.endDate() != null
          || taskRecurrenceOptions.recurrenceTimeZone() != null
          || taskRecurrenceOptions.numberOfOccurrences() != null
          || taskRecurrenceOptions.startDate() != null) {
        final RecurrenceRangeDto recurrenceRangeDto = new RecurrenceRangeDto();
        recurrenceRangeDto.setType(taskRecurrenceOptions.rangeType());
        recurrenceRangeDto.setEndDate(mapLocalDate(taskRecurrenceOptions.endDate()));
        recurrenceRangeDto.setRecurrenceTimeZone(taskRecurrenceOptions.recurrenceTimeZone());
        recurrenceRangeDto.setNumberOfOccurrences(taskRecurrenceOptions.numberOfOccurrences());
        recurrenceRangeDto.setStartDate(mapLocalDate(taskRecurrenceOptions.startDate()));
        patternedRecurrenceDto.setRange(recurrenceRangeDto);
      }

      return patternedRecurrenceDto;
    }
    return null;
  }

  public static List<String> mapCategories(String categories) {
    final List<String> result;
    if (categories != null) {
      result = Arrays.stream(categories.split(","))
                     .map(String::trim)
                     .filter(not(String::isEmpty))
                     .toList();
    } else {
      result = null;
    }
    return result;
  }

  public static Set<DayOfWeekDto> mapDaysOfWeek(String daysOfWeek) {
    final Set<DayOfWeekDto> result;
    if (daysOfWeek != null) {
      result = new LinkedHashSet<>();
      Arrays.stream(daysOfWeek.split(","))
            .map(String::trim)
            .filter(not(String::isEmpty))
            .map(String::toUpperCase)
            .map(daysOfWeekString -> {
              try {
                return DayOfWeekDto.valueOf(daysOfWeekString);
              } catch (IllegalArgumentException e) {
                System.out.println("- " + daysOfWeekString + " ist ILLEGAL");
                return null;
              }
            })
            .filter(Objects::nonNull)
            .forEach(result::add);
    } else {
      result = null;
    }
    return result;
  }

  public static LocalDate mapLocalDate(String dateTime) {
    if (dateTime != null && !dateTime.isEmpty()) {
      try {
        return LocalDate.parse(dateTime, DateTimeFormatter.ISO_DATE);
      } catch (DateTimeParseException e) {
        return null;
      }
    }
    return null;
  }

  public static DateTimeTimeZoneDto mapDateTimeTimeZone(String dateTime, String timeZone) {
    final DateTimeTimeZoneDto dateTimeTimeZoneDto;
    if (dateTime != null && timeZone != null) {
      dateTimeTimeZoneDto = new DateTimeTimeZoneDto();
      dateTimeTimeZoneDto.setDateTime(dateTime);
      dateTimeTimeZoneDto.setTimeZone(timeZone);
    } else {
      dateTimeTimeZoneDto = null;
    }
    return dateTimeTimeZoneDto;
  }

  public static CreateUpdateCheckListItemDto mapFromCheckListItemOptions(
      CheckListItemOptions checkListItemOptions) {
    final CreateUpdateCheckListItemDto createUpdateCheckListItemDto = new CreateUpdateCheckListItemDto();
    createUpdateCheckListItemDto.setChecked(checkListItemOptions.checked());
    createUpdateCheckListItemDto.setDisplayName(checkListItemOptions.displayName());
    return createUpdateCheckListItemDto;
  }

  public static CreateUpdateCheckListItemDto mapFromUpdateCheckListItemOptions(
      UpdateCheckListItemOptions updateCheckListItemOptions) {
    final CreateUpdateCheckListItemDto createUpdateCheckListItemDto = new CreateUpdateCheckListItemDto();
    createUpdateCheckListItemDto.setChecked(updateCheckListItemOptions.checked());
    createUpdateCheckListItemDto.setDisplayName(updateCheckListItemOptions.displayName());
    return createUpdateCheckListItemDto;
  }
}
