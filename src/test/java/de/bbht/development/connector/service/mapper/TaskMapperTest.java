package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.Importance;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.TaskStatus;
import com.microsoft.graph.models.TodoTask;
import de.bbht.development.connector.service.dto.enums.ImportanceDto;
import de.bbht.development.connector.service.dto.enums.TaskStatusDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.DateTimeTimeZoneDto;
import de.bbht.development.connector.service.dto.task.TaskDto;
import java.time.OffsetDateTime;
import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

class TaskMapperTest {

  private static final String DATE_TIME = "2024-08-13T09:48:42.011434";
  private static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.now();

  @Test
  void shouldMapTaskToTaskDto() {
    // given
    var todoTask = new TodoTask();
    todoTask.setId("ID");
    todoTask.setTitle("Title");
    var itemBody = new ItemBody();
    itemBody.setContent("ItemBody");
    itemBody.setContentType(BodyType.Text);
    todoTask.setBody(itemBody);
    todoTask.setImportance(Importance.High);
    todoTask.setStatus(TaskStatus.InProgress);

    todoTask.setBodyLastModifiedDateTime(OFFSET_DATE_TIME);
    todoTask.setLastModifiedDateTime(OFFSET_DATE_TIME);
    todoTask.setCreatedDateTime(OFFSET_DATE_TIME);
    todoTask.setCategories(List.of("Category1", "Category2"));

    todoTask.setStartDateTime(createDateTimeTimeZone());
    todoTask.setDueDateTime(createDateTimeTimeZone());
    todoTask.setCompletedDateTime(createDateTimeTimeZone());
    todoTask.setReminderDateTime(createDateTimeTimeZone());
    todoTask.setIsReminderOn(Boolean.TRUE);

    // when
    var taskDto = TaskMapper.mapTask(todoTask);

    // then
    assertThat(taskDto).returns("ID", TaskDto::getId)
        .returns("Title", TaskDto::getTitle)
        .returns("ItemBody", TaskDto::getBody)
        .returns(ImportanceDto.HIGH, TaskDto::getImportance)
        .returns(TaskStatusDto.IN_PROGRESS, TaskDto::getStatus)
        .returns(OFFSET_DATE_TIME, TaskDto::getBodyLastModifiedDateTime)
        .returns(OFFSET_DATE_TIME, TaskDto::getLastModifiedDateTime)
        .returns(OFFSET_DATE_TIME, TaskDto::getCreatedDateTime)
        .returns(Boolean.TRUE, TaskDto::getReminderOn)
        .satisfies(taskDto1 -> assertThat(taskDto1.getStartDateTime()).returns(DATE_TIME,
                DateTimeTimeZoneDto::getDateTime)
            .returns("UTC", DateTimeTimeZoneDto::getTimeZone))
        .satisfies(taskDto1 -> assertThat(taskDto1.getCompletedDateTime()).returns(DATE_TIME,
                DateTimeTimeZoneDto::getDateTime)
            .returns("UTC", DateTimeTimeZoneDto::getTimeZone))
        .satisfies(taskDto1 -> assertThat(taskDto1.getDueDateTime()).returns(DATE_TIME,
                DateTimeTimeZoneDto::getDateTime)
            .returns("UTC", DateTimeTimeZoneDto::getTimeZone))
        .satisfies(taskDto1 -> assertThat(taskDto1.getReminderDateTime()).returns(DATE_TIME,
                DateTimeTimeZoneDto::getDateTime)
            .returns("UTC", DateTimeTimeZoneDto::getTimeZone))
        .extracting(TaskDto::getCategories, as(InstanceOfAssertFactories.LIST))
        .hasSize(2)
        .satisfiesExactly(item1 -> assertThat(item1).isNotNull()
            .asString()
            .contains("Category1"), item2 -> assertThat(item2).isNotNull()
            .asString()
            .contains("Category2"));
  }

  DateTimeTimeZone createDateTimeTimeZone() {
    var dateTimeTimeZone = new DateTimeTimeZone();
    dateTimeTimeZone.setDateTime(DATE_TIME);
    dateTimeTimeZone.setTimeZone("UTC");
    return dateTimeTimeZone;
  }

  @Test
  void shouldMapCreateUpdateTaskDtoToTask() {
    // given
    var createUpdateTaskDto = new CreateUpdateTaskDto();
    createUpdateTaskDto.setTitle("Title");
    createUpdateTaskDto.setBody("ItemBody");
    createUpdateTaskDto.setImportance(ImportanceDto.HIGH);
    createUpdateTaskDto.setStatus(TaskStatusDto.IN_PROGRESS);

    createUpdateTaskDto.setCategories(List.of("Category1", "Category2"));

    createUpdateTaskDto.setStartDateTime(createDateTimeTimeZoneDto());
    createUpdateTaskDto.setDueDateTime(createDateTimeTimeZoneDto());
    createUpdateTaskDto.setCompletedDateTime(createDateTimeTimeZoneDto());

    // when
    var todoTask = TaskMapper.mapCreateUpdateTaskDto(createUpdateTaskDto);

    // then
    assertThat(todoTask).returns(null, TodoTask::getId)
        .returns("Title", TodoTask::getTitle)
        .satisfies(todoTask1 -> assertThat(todoTask1.getBody()).isNotNull()
            .returns("ItemBody", ItemBody::getContent)
            .returns(BodyType.Text, ItemBody::getContentType))
        .returns(Importance.High, TodoTask::getImportance)
        .returns(TaskStatus.InProgress, TodoTask::getStatus)
        .returns(null, TodoTask::getBodyLastModifiedDateTime)
        .returns(null, TodoTask::getLastModifiedDateTime)
        .returns(null, TodoTask::getCreatedDateTime)
        .satisfies(todoTask1 -> assertThat(todoTask1.getStartDateTime()).returns(DATE_TIME,
                DateTimeTimeZone::getDateTime)
            .returns("UTC", DateTimeTimeZone::getTimeZone))
        .satisfies(todoTask1 -> assertThat(todoTask1.getCompletedDateTime()).returns(DATE_TIME,
                DateTimeTimeZone::getDateTime)
            .returns("UTC", DateTimeTimeZone::getTimeZone))
        .satisfies(todoTask1 -> assertThat(todoTask1.getDueDateTime()).returns(DATE_TIME,
                DateTimeTimeZone::getDateTime)
            .returns("UTC", DateTimeTimeZone::getTimeZone))
        .extracting(TodoTask::getCategories, as(InstanceOfAssertFactories.LIST))
        .hasSize(2)
        .satisfiesExactly(item1 -> assertThat(item1).isNotNull()
            .asString()
            .contains("Category1"), item2 -> assertThat(item2).isNotNull()
            .asString()
            .contains("Category2"));
  }

  DateTimeTimeZoneDto createDateTimeTimeZoneDto() {
    var dateTimeTimeZoneDto = new DateTimeTimeZoneDto();
    dateTimeTimeZoneDto.setDateTime(DATE_TIME);
    dateTimeTimeZoneDto.setTimeZone("UTC");
    return dateTimeTimeZoneDto;
  }

  @Test
  void shouldMapIncompleteCreateUpdateTaskDtoToTask() {
    // given
    var createUpdateTaskDto = new CreateUpdateTaskDto();

    // when
    var todoTask = TaskMapper.mapCreateUpdateTaskDto(createUpdateTaskDto);

    // then
    assertThat(todoTask).returns(null, TodoTask::getId);
  }

  @Test
  void shouldMapListOfTodoTaskToListOfTasksDto() {
    // given
    var todoTask1 = new TodoTask();
    todoTask1.setId("ID1");

    var todoTask2 = new TodoTask();
    todoTask2.setId("ID2");

    var listOfTodoTasks = List.of(todoTask1, todoTask2);

    // when
    var listOfTaskDto = TaskMapper.mapTasks(listOfTodoTasks);

    // then
    assertThat(listOfTaskDto).hasSize(2)
        .satisfiesExactly(task1 -> assertThat(task1).returns("ID1", TaskDto::getId),
            task2 -> assertThat(task2).returns("ID2", TaskDto::getId)

        );
  }

  @Test
  void shouldMapNullTaskDto() {
    // Given
    var taskDto = (CreateUpdateTaskDto) null;

    // When
    var result = TaskMapper.mapCreateUpdateTaskDto(taskDto);

    // Then
    assertThat(result).isNull();

  }

  @Test
  void shouldMapNullTask() {
    // Given
    var task = (TodoTask) null;

    // When
    var result = TaskMapper.mapTask(task);

    // Then
    assertThat(result).isNull();
  }
}
