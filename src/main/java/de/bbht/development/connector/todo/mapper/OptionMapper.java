package de.bbht.development.connector.todo.mapper;

import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.DateTimeTimeZoneDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.todo.entity.*;

import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.not;

public class OptionMapper {

    private OptionMapper() {
        // private constructor to prevent instantiation.
    }

    public static CreateUpdateTaskListDto mapFromTaskListOptions(TaskListOptions taskListOptions) {
        final CreateUpdateTaskListDto createUpdateTaskListDto = new CreateUpdateTaskListDto();
        createUpdateTaskListDto.setDisplayName(taskListOptions.displayName());
        return createUpdateTaskListDto;
    }

    public static CreateUpdateTaskListDto mapFromUpdateTaskListOptions(UpdateTaskListOptions updateTaskListOptions) {
        final CreateUpdateTaskListDto createUpdateTaskListDto = new CreateUpdateTaskListDto();
        createUpdateTaskListDto.setDisplayName(updateTaskListOptions.displayName());
        return createUpdateTaskListDto;
    }

    public static CreateUpdateTaskDto mapFromTaskOptions(TaskOptions taskOptions) {
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
        return createUpdateTaskDto;
    }

    public static CreateUpdateTaskDto mapFromUpdateTaskOptions(UpdateTaskOptions updateTaskOptions) {
        final CreateUpdateTaskDto createUpdateTaskDto = new CreateUpdateTaskDto();
        createUpdateTaskDto.setTitle(updateTaskOptions.title());
        createUpdateTaskDto.setBody(updateTaskOptions.body());
        createUpdateTaskDto.setImportance(updateTaskOptions.importance());
        createUpdateTaskDto.setStatus(updateTaskOptions.status());
        createUpdateTaskDto.setCategories(mapCategories(updateTaskOptions.categories()));
        createUpdateTaskDto.setStartDateTime(
                mapDateTimeTimeZone(updateTaskOptions.startDateTime(), updateTaskOptions.startDateTimeTimeZone()));
        createUpdateTaskDto.setDueDateTime(
                mapDateTimeTimeZone(updateTaskOptions.dueDateTime(), updateTaskOptions.startDateTimeTimeZone()));
        createUpdateTaskDto.setCompletedDateTime(mapDateTimeTimeZone(updateTaskOptions.completedDateTime(),
                updateTaskOptions.completedDateTimeTimeZone()));
        return createUpdateTaskDto;
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
