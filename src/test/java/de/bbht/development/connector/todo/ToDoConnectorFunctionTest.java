package de.bbht.development.connector.todo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bbht.development.connector.service.IMsGraphServiceFactory;
import de.bbht.development.connector.service.MsGraphException;
import de.bbht.development.connector.service.MsGraphService;
import de.bbht.development.connector.service.dto.GraphAuthenticationDto;
import de.bbht.development.connector.service.dto.checklistitem.CheckListItemDto;
import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.ImportanceDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;
import de.bbht.development.connector.service.dto.enums.TaskStatusDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;
import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.DateTimeTimeZoneDto;
import de.bbht.development.connector.service.dto.task.PatternedRecurrenceDto;
import de.bbht.development.connector.service.dto.task.RecurrencePatternDto;
import de.bbht.development.connector.service.dto.task.RecurrenceRangeDto;
import de.bbht.development.connector.service.dto.task.TaskDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import de.bbht.development.connector.todo.entity.CheckListItemOptions;
import de.bbht.development.connector.todo.entity.ConnectorError;
import de.bbht.development.connector.todo.entity.ConnectorResult;
import de.bbht.development.connector.todo.entity.GraphAuthentication;
import de.bbht.development.connector.todo.entity.Operation;
import de.bbht.development.connector.todo.entity.TaskListOptions;
import de.bbht.development.connector.todo.entity.TaskOptions;
import de.bbht.development.connector.todo.entity.TaskRecurrenceOptions;
import de.bbht.development.connector.todo.entity.ToDoConnectorRequest;
import de.bbht.development.connector.todo.entity.ToDoOperation;
import de.bbht.development.connector.todo.entity.UpdateCheckListItemOptions;
import de.bbht.development.connector.todo.entity.UpdateTaskListOptions;
import de.bbht.development.connector.todo.entity.UpdateTaskOptions;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder.TestConnectorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ToDoConnectorFunctionTest {

  static class TestMsGraphServiceFactory implements IMsGraphServiceFactory {

    private final MsGraphService service;

    public TestMsGraphServiceFactory(MsGraphService msGraphService) {
      this.service = msGraphService;
    }

    @Override
    public MsGraphService create(GraphAuthenticationDto authenticationInfo) {
      return service;
    }
  }

  @Mock
  private MsGraphService msGraphService;

  private ToDoConnectorFunction connectorUnderTest;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private GraphAuthentication authentication = null;
  private String testUserId;

  @BeforeEach
  void init() {
    this.authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
        "{{secrets.CLIENT_SECRET}}");
    this.testUserId = "test@testsystem.onmicrosoft.com";
    var msGraphServiceFactory = new TestMsGraphServiceFactory(msGraphService);
    this.connectorUnderTest = new ToDoConnectorFunction();
    this.connectorUnderTest.setGraphServiceFactory(msGraphServiceFactory);
  }

  List<TaskListDto> createListOfTaskLists() {
    var taskList1 = new TaskListDto();
    taskList1.setId("1");
    taskList1.setDisplayName("Task List 1");
    taskList1.setWellknownListName(WellknownListNameDto.NONE);
    taskList1.setOwner(true);
    taskList1.setShared(false);

    var taskList2 = new TaskListDto();
    taskList2.setId("2");
    taskList2.setDisplayName("Task List 2");
    taskList2.setWellknownListName(WellknownListNameDto.DEFAULT_LIST);
    taskList2.setOwner(true);
    taskList2.setShared(true);

    var taskList3 = new TaskListDto();
    taskList3.setId("3");
    taskList3.setDisplayName("Task List 3");
    taskList3.setWellknownListName(WellknownListNameDto.FLAGGED_EMAILS);
    taskList3.setOwner(true);
    taskList3.setShared(false);

    return List.of(taskList1, taskList2, taskList3);
  }

  TaskListDto createTaskList() {
    var taskList = new TaskListDto();
    taskList.setId("1");
    taskList.setDisplayName("Task List 1");
    taskList.setWellknownListName(WellknownListNameDto.NONE);
    taskList.setOwner(true);
    taskList.setShared(false);
    return taskList;
  }

  @Test
  void shouldReturnListOfTaskLists() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.LIST_TASK_LISTS, testUserId, null, null, null), null, null,
        null, null, null, null, null);

    // Mock Setup
    final var listOfTaskLists = createListOfTaskLists();
    given(msGraphService.getListOfTaskLists(testUserId)).willReturn(listOfTaskLists);

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isInstanceOfSatisfying(ConnectorResult.class,
        connectorResult -> assertThat(connectorResult).extracting(ConnectorResult::getResult)
            .asInstanceOf(InstanceOfAssertFactories.list(TaskListDto.class))
            .hasSize(3)
            .satisfiesExactly(item1 -> assertThat(item1).returns("1", TaskListDto::getId)
                    .returns("Task List 1", TaskListDto::getDisplayName)
                    .returns(WellknownListNameDto.NONE, TaskListDto::getWellknownListName)
                    .returns(true, TaskListDto::getOwner)
                    .returns(false, TaskListDto::getShared),
                item2 -> assertThat(item2).returns("2", TaskListDto::getId)
                    .returns("Task List 2", TaskListDto::getDisplayName)
                    .returns(WellknownListNameDto.DEFAULT_LIST, TaskListDto::getWellknownListName)
                    .returns(true, TaskListDto::getOwner)
                    .returns(true, TaskListDto::getShared),
                item3 -> assertThat(item3).returns("3", TaskListDto::getId)
                    .returns("Task List 3", TaskListDto::getDisplayName)
                    .returns(WellknownListNameDto.FLAGGED_EMAILS, TaskListDto::getWellknownListName)
                    .returns(true, TaskListDto::getOwner)
                    .returns(false, TaskListDto::getShared)));
  }

  private TestConnectorContext createDefaultOutboundConnectorContext(Operation operation,
      TaskListOptions taskListOption, UpdateTaskListOptions updateTaskListOptions,
      TaskOptions taskOptions, UpdateTaskOptions updateTaskOptions,
      TaskRecurrenceOptions taskRecurrenceOptions, CheckListItemOptions checkListItemOptions,
      UpdateCheckListItemOptions updateCheckListItemOptions) throws Exception {
    var tenant = "testTenantId";
    var client = "testClientId";
    var secret = "testClientSecret";

    var request = new ToDoConnectorRequest(authentication, operation, taskListOption,
        updateTaskListOptions, taskOptions, updateTaskOptions, taskRecurrenceOptions,
        checkListItemOptions, updateCheckListItemOptions);
    return OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", tenant)
        .secret("CLIENT_ID", client)
        .secret("CLIENT_SECRET", secret)
        .variables(objectMapper.writeValueAsString(request))
        .build();
  }

  @Test
  void shouldReturnTaskListById() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.GET_TASK_LIST, testUserId, "1", null, null), null, null, null,
        null, null, null, null);

    // Mock Setup
    final var taskList = createTaskList();
    given(msGraphService.getTaskListById(testUserId, "1")).willReturn(Optional.of(taskList));

    // When
    var result = connectorUnderTest.execute(context);

    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(TaskListDto.class))
        .returns("1", TaskListDto::getId)
        .returns("Task List 1", TaskListDto::getDisplayName)
        .returns(WellknownListNameDto.NONE, TaskListDto::getWellknownListName)
        .returns(true, TaskListDto::getOwner)
        .returns(false, TaskListDto::getShared);
  }

  @Test
  void shouldCreateNewTaskList() throws Exception {
    // Given
    var taskListOptions = new TaskListOptions("Task List 1");
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.CREATE_TASK_LIST, testUserId, "1", null, null), taskListOptions,
        null, null, null, null, null, null);

    // Mock Setup
    final var createTaskList = new CreateUpdateTaskListDto();
    createTaskList.setDisplayName("Task List 1");
    final var taskList = createTaskList();
    given(msGraphService.createTaskList(testUserId, createTaskList)).willReturn(
        Optional.of(taskList));

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(TaskListDto.class))
        .returns("1", TaskListDto::getId)
        .returns("Task List 1", TaskListDto::getDisplayName)
        .returns(WellknownListNameDto.NONE, TaskListDto::getWellknownListName)
        .returns(true, TaskListDto::getOwner)
        .returns(false, TaskListDto::getShared);

  }

  @Test
  void shouldUpdateTaskList() throws Exception {
    // Given
    var updateTaskListOptions = new UpdateTaskListOptions("Updated Task List 1");
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.UPDATE_TASK_LIST, testUserId, "1", null, null), null,
        updateTaskListOptions, null, null, null, null, null);

    // Mock Setup
    final var updateTaskList = new CreateUpdateTaskListDto();
    updateTaskList.setDisplayName("Updated Task List 1");
    final var taskList = createTaskList();
    taskList.setDisplayName("Updated Task List 1");
    given(msGraphService.updateTaskList(testUserId, "1", updateTaskList)).willReturn(
        Optional.of(taskList));

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(TaskListDto.class))
        .returns("1", TaskListDto::getId)
        .returns("Updated Task List 1", TaskListDto::getDisplayName)
        .returns(WellknownListNameDto.NONE, TaskListDto::getWellknownListName)
        .returns(true, TaskListDto::getOwner)
        .returns(false, TaskListDto::getShared);
  }

  @Test
  void shouldDeleteTaskList() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.DELETE_TASK_LIST, testUserId, "1", null, null), null, null,
        null, null, null, null, null);

    // Mock Setup
    doNothing().when(msGraphService)
        .deleteTaskList(testUserId, "1");

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .returns(true, ConnectorResult::isEmpty);
  }

  List<TaskDto> createListOfTasks() {
    var task1 = new TaskDto();
    task1.setId("1");
    task1.setTitle("Title 1");
    task1.setBody("Item Body 1");
    task1.setImportance(ImportanceDto.HIGH);
    task1.setStatus(TaskStatusDto.IN_PROGRESS);
    task1.setCategories(List.of("Category 1", "Category 2"));
    task1.setStartDateTime(createDateTime(2023));
    task1.setDueDateTime(createDateTime(2024));

    var task2 = new TaskDto();
    task2.setId("2");
    task2.setTitle("Title 2");
    task2.setBody("Item Body 2");
    task2.setImportance(ImportanceDto.LOW);
    task2.setStatus(TaskStatusDto.DEFERRED);
    task2.setCategories(List.of("Category 1", "Category 3"));
    task2.setStartDateTime(createDateTime(2023));
    task2.setDueDateTime(createDateTime(2024));
    task2.setReminderDateTime(createDateTime(2024));

    var task3 = new TaskDto();
    task3.setId("3");
    task3.setTitle("Title 3");
    task3.setBody("Item Body 3");
    task3.setImportance(ImportanceDto.NORMAL);
    task3.setStatus(TaskStatusDto.COMPLETED);
    task3.setCategories(List.of("Category 2", "Category 3"));
    task3.setStartDateTime(createDateTime(2023));
    task3.setDueDateTime(createDateTime(2024));
    task3.setCompletedDateTime(createDateTime(2025));
    task3.setReminderDateTime(createDateTime(2024));

    return List.of(task1, task2, task3);
  }

  private DateTimeTimeZoneDto createDateTime(int year) {
    var result = new DateTimeTimeZoneDto();
    result.setDateTime(year + "-08-13T14:38:43.104312");
    result.setTimeZone("UTC");
    return result;
  }

  TaskDto createTask(boolean withRecurrence) {
    var task = new TaskDto();
    task.setId("1");
    task.setTitle("Title");
    task.setBody("Item Body");
    task.setImportance(ImportanceDto.HIGH);
    task.setStatus(TaskStatusDto.COMPLETED);
    task.setCategories(List.of("Category 1", "Category 2"));
    task.setStartDateTime(createDateTime(2023));
    task.setDueDateTime(createDateTime(2024));
    task.setCompletedDateTime(createDateTime(2025));
    task.setReminderDateTime(createDateTime(2024));

    if (withRecurrence) {
      var recurrencePattern = new RecurrencePatternDto();
      recurrencePattern.setType(RecurrencePatternTypeDto.DAILY);
      recurrencePattern.setDayOfMonth(12);
      var daysOfWeek = new LinkedHashSet<DayOfWeekDto>();
      daysOfWeek.add(DayOfWeekDto.MONDAY);
      daysOfWeek.add(DayOfWeekDto.THURSDAY);
      recurrencePattern.setDaysOfWeek(daysOfWeek);
      recurrencePattern.setFirstDayOfWeek(DayOfWeekDto.MONDAY);
      recurrencePattern.setIndex(WeekIndexDto.FIRST);
      recurrencePattern.setInterval(5);
      recurrencePattern.setMonth(7);

      var recurrenceRange = new RecurrenceRangeDto();
      recurrenceRange.setType(RecurrenceRangeTypeDto.NUMBERED);
      recurrenceRange.setRecurrenceTimeZone("UTC");
      recurrenceRange.setNumberOfOccurrences(10);
      recurrenceRange.setStartDate(LocalDate.parse("2024-08-01", DateTimeFormatter.ISO_DATE));
      recurrenceRange.setEndDate(LocalDate.parse("2024-12-01", DateTimeFormatter.ISO_DATE));

      var recurrence = new PatternedRecurrenceDto();
      recurrence.setPattern(recurrencePattern);
      recurrence.setRange(recurrenceRange);
      task.setRecurrence(recurrence);
    }
    return task;
  }

  @Test
  void shouldReturnListOfTasks() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.LIST_TASKS, testUserId, "1", null, null), null, null, null,
        null, null, null, null);

    // Mock Setup
    final var listOfTasks = createListOfTasks();
    given(msGraphService.getListOfTasks(testUserId, "1")).willReturn(listOfTasks);

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isInstanceOfSatisfying(ConnectorResult.class,
        connectorResult -> assertThat(connectorResult).extracting(ConnectorResult::getResult)
            .asInstanceOf(InstanceOfAssertFactories.list(TaskDto.class))
            .hasSize(3)
            .satisfiesExactly(item1 -> assertThat(item1).returns("1", TaskDto::getId)
                    .returns("Title 1", TaskDto::getTitle)
                    .returns("Item Body 1", TaskDto::getBody)
                    .returns(ImportanceDto.HIGH, TaskDto::getImportance)
                    .returns(TaskStatusDto.IN_PROGRESS, TaskDto::getStatus),
                item2 -> assertThat(item2).returns("2", TaskDto::getId)
                    .returns("Title 2", TaskDto::getTitle)
                    .returns("Item Body 2", TaskDto::getBody)
                    .returns(ImportanceDto.LOW, TaskDto::getImportance)
                    .returns(TaskStatusDto.DEFERRED, TaskDto::getStatus),
                item3 -> assertThat(item3).returns("3", TaskDto::getId)
                    .returns("Title 3", TaskDto::getTitle)
                    .returns("Item Body 3", TaskDto::getBody)
                    .returns(ImportanceDto.NORMAL, TaskDto::getImportance)
                    .returns(TaskStatusDto.COMPLETED, TaskDto::getStatus)));
  }

  @Test
  void shouldReturnTask() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.GET_TASK, testUserId, "1", "1", null), null, null, null, null,
        null, null, null);

    // Mock Setup
    final var task = createTask(false);
    given(msGraphService.getTask(testUserId, "1", "1")).willReturn(Optional.of(task));

    // When
    var result = connectorUnderTest.execute(context);

    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(TaskDto.class))
        .returns("1", TaskDto::getId)
        .returns("Title", TaskDto::getTitle)
        .returns("Item Body", TaskDto::getBody)
        .returns(ImportanceDto.HIGH, TaskDto::getImportance)
        .returns(TaskStatusDto.COMPLETED, TaskDto::getStatus);
  }

  @Test
  void shouldCreateNewTask() throws Exception {
    // Given
    var taskOptions = new TaskOptions("Title", "Item Body", "Category 1, Category 2",
        ImportanceDto.HIGH, TaskStatusDto.COMPLETED, "2023-08-13T14:38:43.104312", "UTC", null,
        null, null, null, "2024-08-13T14:38:43.104312", "UTC", Boolean.TRUE);
    var taskRecurrenceOptions = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_RECURRING,
        RecurrencePatternTypeDto.DAILY, 5, 12, "Monday, Thursday", DayOfWeekDto.MONDAY,
        WeekIndexDto.FIRST, 7, RecurrenceRangeTypeDto.NUMBERED, 10, "2024-08-01", "2024-12-01",
        "UTC");
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.CREATE_TASK, testUserId, "1", null, null), null, null,
        taskOptions, null, taskRecurrenceOptions, null, null);

    // Mock Setup
    final var createTask = new CreateUpdateTaskDto();
    createTask.setTitle("Title");
    createTask.setBody("Item Body");
    createTask.setCategories(List.of("Category 1", "Category 2"));
    createTask.setImportance(ImportanceDto.HIGH);
    createTask.setStatus(TaskStatusDto.COMPLETED);
    var startDateTime = new DateTimeTimeZoneDto();
    startDateTime.setDateTime("2023-08-13T14:38:43.104312");
    startDateTime.setTimeZone("UTC");
    createTask.setStartDateTime(startDateTime);

    var recurrencePattern = new RecurrencePatternDto();
    recurrencePattern.setType(RecurrencePatternTypeDto.DAILY);
    recurrencePattern.setDayOfMonth(12);
    var daysOfWeek = new LinkedHashSet<DayOfWeekDto>();
    daysOfWeek.add(DayOfWeekDto.MONDAY);
    daysOfWeek.add(DayOfWeekDto.THURSDAY);
    recurrencePattern.setDaysOfWeek(daysOfWeek);
    recurrencePattern.setFirstDayOfWeek(DayOfWeekDto.MONDAY);
    recurrencePattern.setIndex(WeekIndexDto.FIRST);
    recurrencePattern.setInterval(5);
    recurrencePattern.setMonth(7);

    var recurrenceRange = new RecurrenceRangeDto();
    recurrenceRange.setType(RecurrenceRangeTypeDto.NUMBERED);
    recurrenceRange.setRecurrenceTimeZone("UTC");
    recurrenceRange.setNumberOfOccurrences(10);
    recurrenceRange.setStartDate(LocalDate.parse("2024-08-01", DateTimeFormatter.ISO_DATE));
    recurrenceRange.setEndDate(LocalDate.parse("2024-12-01", DateTimeFormatter.ISO_DATE));

    var recurrence = new PatternedRecurrenceDto();
    recurrence.setPattern(recurrencePattern);
    recurrence.setRange(recurrenceRange);
    createTask.setRecurrence(recurrence);

    createTask.setReminderOn(Boolean.TRUE);
    var reminderDateTime = new DateTimeTimeZoneDto();
    reminderDateTime.setDateTime("2024-08-13T14:38:43.104312");
    reminderDateTime.setTimeZone("UTC");
    createTask.setReminderDateTime(reminderDateTime);
    final var task = createTask(true);
    given(msGraphService.createTask(testUserId, "1", createTask)).willReturn(Optional.of(task));

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(TaskDto.class))
        .returns("1", TaskDto::getId)
        .returns("Title", TaskDto::getTitle)
        .returns("Item Body", TaskDto::getBody)
        .returns(ImportanceDto.HIGH, TaskDto::getImportance)
        .returns(TaskStatusDto.COMPLETED, TaskDto::getStatus)
        .satisfies(taskDto1 -> assertThat(taskDto1.getStartDateTime()).returns(
                "2023-08-13T14:38:43.104312", DateTimeTimeZoneDto::getDateTime)
            .returns("UTC", DateTimeTimeZoneDto::getTimeZone))
        .satisfies(taskDto2 -> assertThat(taskDto2.getReminderDateTime()).returns(
                "2024-08-13T14:38:43.104312", DateTimeTimeZoneDto::getDateTime)
            .returns("UTC", DateTimeTimeZoneDto::getTimeZone))
        .extracting(TaskDto::getRecurrence)
        .satisfies(rec -> assertThat(rec.getPattern()).returns(RecurrencePatternTypeDto.DAILY,
                RecurrencePatternDto::getType)
            .returns(12, RecurrencePatternDto::getDayOfMonth)
            .returns(5, RecurrencePatternDto::getInterval)
            .returns(DayOfWeekDto.MONDAY, RecurrencePatternDto::getFirstDayOfWeek)
            .returns(7, RecurrencePatternDto::getMonth)
            .returns(WeekIndexDto.FIRST, RecurrencePatternDto::getIndex)
            .satisfies(
                recurrentPattern -> assertThat(recurrentPattern.getDaysOfWeek()).asInstanceOf(
                        InstanceOfAssertFactories.ITERABLE)
                    .hasSize(2)
                    .containsExactly(DayOfWeekDto.MONDAY, DayOfWeekDto.THURSDAY)))
        .satisfies(rec -> assertThat(rec.getRange()).returns(RecurrenceRangeTypeDto.NUMBERED,
                RecurrenceRangeDto::getType)
            .returns(10, RecurrenceRangeDto::getNumberOfOccurrences)
            .returns(LocalDate.parse("2024-08-01", DateTimeFormatter.ISO_DATE),
                RecurrenceRangeDto::getStartDate)
            .returns(LocalDate.parse("2024-12-01", DateTimeFormatter.ISO_DATE),
                RecurrenceRangeDto::getEndDate)
            .returns("UTC", RecurrenceRangeDto::getRecurrenceTimeZone));
  }

  @Test
  void shouldUpdateTask() throws Exception {
    // Given
    var updateTaskOptions = new UpdateTaskOptions("New Title", null, "", null, null, null, null,
        null, null, null, null, null, null, null);
    var taskRecurrenceOptions = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_RECURRING,
        RecurrencePatternTypeDto.WEEKLY, null, null, null, null, null, null, null, null, null, null,
        null);
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.UPDATE_TASK, testUserId, "1", "1", null), null, null, null,
        updateTaskOptions, taskRecurrenceOptions, null, null);

    // Mock Setup
    final var updateTask = new CreateUpdateTaskDto();
    updateTask.setTitle("New Title");
    updateTask.setCategories(new ArrayList<>());
    final var recurrencePattern = new RecurrencePatternDto();
    recurrencePattern.setType(RecurrencePatternTypeDto.WEEKLY);
    final var recurrence = new PatternedRecurrenceDto();
    recurrence.setPattern(recurrencePattern);
    updateTask.setRecurrence(recurrence);

    final var task = createTask(true);
    task.setTitle("New Title");
    task.setCategories(null);
    task.getRecurrence()
        .getPattern()
        .setType(RecurrencePatternTypeDto.WEEKLY);
    given(msGraphService.updateTask(testUserId, "1", "1", updateTask)).willReturn(
        Optional.of(task));

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(TaskDto.class))
        .returns("1", TaskDto::getId)
        .returns("New Title", TaskDto::getTitle)
        .returns("Item Body", TaskDto::getBody)
        .returns(ImportanceDto.HIGH, TaskDto::getImportance)
        .returns(TaskStatusDto.COMPLETED, TaskDto::getStatus)
        .extracting(TaskDto::getRecurrence)
        .extracting(PatternedRecurrenceDto::getPattern)
        .isNotNull()
        .returns(RecurrencePatternTypeDto.WEEKLY, RecurrencePatternDto::getType);
  }

  @Test
  void shouldDeleteTask() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.DELETE_TASK, testUserId, "1", "1", null), null, null, null,
        null, null, null, null);

    // Mock Setup
    doNothing().when(msGraphService)
        .deleteTask(testUserId, "1", "1");

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .returns(true, ConnectorResult::isEmpty);
  }

  List<CheckListItemDto> createListOfCheckListItems() {
    var item1 = new CheckListItemDto();
    item1.setId("1");
    item1.setDisplayName("Check List Item 1");
    item1.setChecked(Boolean.FALSE);

    var item2 = new CheckListItemDto();
    item2.setId("2");
    item2.setDisplayName("Check List Item 2");
    item2.setChecked(Boolean.TRUE);

    var item3 = new CheckListItemDto();
    item3.setId("3");
    item3.setDisplayName("Check List Item 3");
    item3.setChecked(Boolean.FALSE);

    return List.of(item1, item2, item3);
  }

  CheckListItemDto createCheckListItem() {
    var checkListItem = new CheckListItemDto();
    checkListItem.setId("1");
    checkListItem.setDisplayName("Check List Item");
    checkListItem.setChecked(Boolean.FALSE);
    return checkListItem;
  }

  @Test
  void shouldReturnListOfCheckListItems() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.LIST_CHECK_LIST_ITEMS, testUserId, "1", "1", null), null, null,
        null, null, null, null, null);

    // Mock Setup
    final var listOfCheckListItems = createListOfCheckListItems();
    given(msGraphService.getListOfCheckListItems(testUserId, "1", "1")).willReturn(
        listOfCheckListItems);

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isInstanceOfSatisfying(ConnectorResult.class,
        taskList -> assertThat(taskList).extracting(ConnectorResult::getResult)
            .asInstanceOf(InstanceOfAssertFactories.list(CheckListItemDto.class))
            .hasSize(3)
            .satisfiesExactly(item1 -> assertThat(item1).returns("1", CheckListItemDto::getId)
                    .returns("Check List Item 1", CheckListItemDto::getDisplayName)
                    .returns(Boolean.FALSE, CheckListItemDto::getChecked),
                item2 -> assertThat(item2).returns("2", CheckListItemDto::getId)
                    .returns("Check List Item 2", CheckListItemDto::getDisplayName)
                    .returns(Boolean.TRUE, CheckListItemDto::getChecked),
                item3 -> assertThat(item3).returns("3", CheckListItemDto::getId)
                    .returns("Check List Item 3", CheckListItemDto::getDisplayName)
                    .returns(Boolean.FALSE, CheckListItemDto::getChecked)));

  }

  @Test
  void shouldReturnCheckListItem() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.GET_CHECK_LIST_ITEM, testUserId, "1", "1", "1"), null, null,
        null, null, null, null, null);

    // Mock Setup
    final var checkListItem = createCheckListItem();
    given(msGraphService.getCheckListItem(testUserId, "1", "1", "1")).willReturn(
        Optional.of(checkListItem));

    // When
    var result = connectorUnderTest.execute(context);

    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(CheckListItemDto.class))
        .returns("1", CheckListItemDto::getId)
        .returns("Check List Item", CheckListItemDto::getDisplayName)
        .returns(Boolean.FALSE, CheckListItemDto::getChecked);
  }

  @Test
  void shouldCreateNewCheckListItem() throws Exception {
    // Given
    var checkListItemOptions = new CheckListItemOptions("Display Name", Boolean.FALSE);
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.CREATE_CHECK_LIST_ITEM, testUserId, "1", "1", null), null, null,
        null, null, null, checkListItemOptions, null);

    // Mock Setup
    final var createCheckListItem = new CreateUpdateCheckListItemDto();
    createCheckListItem.setDisplayName("Display Name");
    createCheckListItem.setChecked(Boolean.FALSE);
    final var checkListItem = createCheckListItem();
    given(msGraphService.createCheckListItem(testUserId, "1", "1", createCheckListItem)).willReturn(
        Optional.of(checkListItem));

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(CheckListItemDto.class))
        .returns("1", CheckListItemDto::getId)
        .returns("Check List Item", CheckListItemDto::getDisplayName)
        .returns(Boolean.FALSE, CheckListItemDto::getChecked);
  }

  @Test
  void shouldUpdateCheckListItem() throws Exception {
    // Given
    var updateCheckListItemOptions = new UpdateCheckListItemOptions("Updated Check List Item",
        null);
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.UPDATE_CHECK_LIST_ITEM, testUserId, "1", "1", "1"), null, null,
        null, null, null, null, updateCheckListItemOptions);

    // Mock Setup
    final var updateCheckListItem = new CreateUpdateCheckListItemDto();
    updateCheckListItem.setDisplayName("Updated Check List Item");
    final var checkListItem = createCheckListItem();
    checkListItem.setDisplayName("Updated Check List Item");
    given(msGraphService.updateCheckListItem(testUserId, "1", "1", "1",
        updateCheckListItem)).willReturn(Optional.of(checkListItem));

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .extracting(ConnectorResult::getResult)
        .isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(CheckListItemDto.class))
        .returns("1", CheckListItemDto::getId)
        .returns("Updated Check List Item", CheckListItemDto::getDisplayName)
        .returns(Boolean.FALSE, CheckListItemDto::getChecked);
  }

  @Test
  void shouldDeleteCheckListItem() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.DELETE_CHECK_LIST_ITEM, testUserId, "1", "1", "1"), null, null,
        null, null, null, null, null);

    // Mock Setup
    doNothing().when(msGraphService)
        .deleteCheckListItem(testUserId, "1", "1", "1");

    // When
    var result = connectorUnderTest.execute(context);

    // Then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .returns(true, ConnectorResult::isEmpty);
  }

  @Test
  void shouldReturnErrorResponseOnMsGraphException() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.GET_TASK_LIST, testUserId, "999", null, null), null, null, null,
        null, null, null, null);

    // Mock Setup
    var msGraphException = new MsGraphException("Task List with ID 999 not found", "404");
    given(msGraphService.getTaskListById(testUserId, "999")).willThrow(msGraphException);

    // when
    var result = connectorUnderTest.execute(context);

    // then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .returns(null, ConnectorResult::getResult)
        .returns(true, ConnectorResult::isEmpty)
        .extracting(ConnectorResult::getError)
        .returns("Task List with ID 999 not found", ConnectorError::getErrorMessage)
        .returns("404", ConnectorError::getErrorCode);
  }

  @Test
  void shouldReturnErrorResponseOnOtherException() throws Exception {
    // Given
    var context = createDefaultOutboundConnectorContext(
        new Operation(ToDoOperation.GET_TASK_LIST, testUserId, "999", null, null), null, null, null,
        null, null, null, null);

    // Mock Setup
    var runtimeException = new RuntimeException("Some other exception");
    given(msGraphService.getTaskListById(testUserId, "999")).willThrow(runtimeException);

    // when
    var result = connectorUnderTest.execute(context);

    // then
    assertThat(result).isNotNull()
        .asInstanceOf(InstanceOfAssertFactories.type(ConnectorResult.class))
        .returns(null, ConnectorResult::getResult)
        .returns(true, ConnectorResult::isEmpty)
        .extracting(ConnectorResult::getError)
        .returns("Some other exception", ConnectorError::getErrorMessage)
        .returns(null, ConnectorError::getErrorCode);
  }
}
