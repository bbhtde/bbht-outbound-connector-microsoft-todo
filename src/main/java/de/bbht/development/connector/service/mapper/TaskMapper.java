package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.TodoTask;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.TaskDto;
import java.util.List;

public final class TaskMapper {

  private TaskMapper() {
    // private constructor to prevent instantiation
  }

  public static List<TaskDto> mapTasks(List<TodoTask> listOfTasks) {
    return listOfTasks.stream()
        .map(TaskMapper::mapTask)
        .toList();
  }

  public static TaskDto mapTask(TodoTask todoTask) {
    TaskDto taskDto = null;
    if (todoTask != null) {
      taskDto = new TaskDto();
      taskDto.setId(todoTask.getId());
      taskDto.setTitle(todoTask.getTitle());
      if (todoTask.getBody() != null) {
        taskDto.setBody(todoTask.getBody()
            .getContent());
      }
      taskDto.setBodyLastModifiedDateTime(todoTask.getBodyLastModifiedDateTime());
      taskDto.setCategories(todoTask.getCategories());
      taskDto.setCompletedDateTime(
          DateTimeTimeZoneMapper.mapDateTimeTimeZone(todoTask.getCompletedDateTime()));
      taskDto.setCreatedDateTime(todoTask.getCreatedDateTime());
      taskDto.setDueDateTime(DateTimeTimeZoneMapper.mapDateTimeTimeZone(todoTask.getDueDateTime()));
      taskDto.setImportance(EnumMapper.mapImportance(todoTask.getImportance()));
      taskDto.setLastModifiedDateTime(todoTask.getLastModifiedDateTime());
      taskDto.setRecurrence(PatternedRecurrenceMapper.mapPatternedRecurrence(todoTask.getRecurrence()));
      taskDto.setReminderOn(todoTask.getIsReminderOn());
      taskDto.setReminderDateTime(
              DateTimeTimeZoneMapper.mapDateTimeTimeZone(todoTask.getReminderDateTime()));
      taskDto.setStartDateTime(
          DateTimeTimeZoneMapper.mapDateTimeTimeZone(todoTask.getStartDateTime()));
      taskDto.setStatus(EnumMapper.mapTaskStatus(todoTask.getStatus()));
    }
    return taskDto;
  }

  public static TodoTask mapCreateUpdateTaskDto(CreateUpdateTaskDto createUpdateTaskDto) {
    TodoTask todoTask = null;
    if (createUpdateTaskDto != null) {
      todoTask = new TodoTask();
      if (createUpdateTaskDto.getBody() != null) {
        ItemBody itemBody = new ItemBody();
        itemBody.setContent(createUpdateTaskDto.getBody());
        itemBody.setContentType(BodyType.Text);
        todoTask.setBody(itemBody);
      }
      todoTask.setCategories(createUpdateTaskDto.getCategories());
      todoTask.setCompletedDateTime(DateTimeTimeZoneMapper.mapDateTimeTimeZoneDto(
          createUpdateTaskDto.getCompletedDateTime()));
      todoTask.setDueDateTime(
          DateTimeTimeZoneMapper.mapDateTimeTimeZoneDto(createUpdateTaskDto.getDueDateTime()));
      todoTask.setImportance(EnumMapper.mapImportanceDto(createUpdateTaskDto.getImportance()));
      todoTask.setRecurrence(PatternedRecurrenceMapper.mapPatternedRecurrenceDto(createUpdateTaskDto.getRecurrence()));
      todoTask.setIsReminderOn(createUpdateTaskDto.getReminderOn());
      todoTask.setReminderDateTime(
              DateTimeTimeZoneMapper.mapDateTimeTimeZoneDto(createUpdateTaskDto.getReminderDateTime()));
      todoTask.setStartDateTime(
          DateTimeTimeZoneMapper.mapDateTimeTimeZoneDto(createUpdateTaskDto.getStartDateTime()));
      todoTask.setStatus(EnumMapper.mapTaskStatusDto(createUpdateTaskDto.getStatus()));
      todoTask.setTitle(createUpdateTaskDto.getTitle());
    }
    return todoTask;
  }
}
