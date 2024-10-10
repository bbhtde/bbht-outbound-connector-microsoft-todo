package de.bbht.development.connector.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.ChecklistItem;
import com.microsoft.graph.models.Importance;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.TaskStatus;
import com.microsoft.graph.models.TodoTask;
import com.microsoft.graph.models.TodoTaskList;
import com.microsoft.graph.models.WellknownListName;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.users.item.todo.lists.item.TodoTaskListItemRequestBuilder;
import com.microsoft.graph.users.item.todo.lists.item.tasks.item.TodoTaskItemRequestBuilder;
import com.microsoft.graph.users.item.todo.lists.item.tasks.item.checklistitems.item.ChecklistItemItemRequestBuilder;
import de.bbht.development.connector.service.dto.checklistitem.CheckListItemDto;
import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.enums.ImportanceDto;
import de.bbht.development.connector.service.dto.enums.TaskStatusDto;
import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.TaskDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MsGraphServiceTest {

  private static final String TEST_TASK_LIST_NAME = "Test Task List";
  private static final String TEST_TASK_LIST_NAME_UPDATED = "Updated Test Task List";

  private static final String TEST_USER_ID = "test@test.onmicrosoft.com";
  private static final String TEST_TASK_LIST_ID = "TASKLIST_01";
  private static final String TEST_TASK_ID_01 = "ID1";
  private static final String TEST_TASK_ID_04 = "ID4";
  private static final String TEST_CHECK_LIST_ITEM_ID_01 = "ID1";
  private static final String TEST_CHECK_LIST_ITEM_ID_04 = "ID4";

  private static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.now();

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private GraphServiceClient graphServiceClient;

  private MsGraphService service;

  @BeforeEach
  void setup() {
    service = new MsGraphService(graphServiceClient);
  }

  private List<TodoTaskList> createMockTodoTaskLists(boolean hasCreatedList,
      boolean hasUpdateListName) {
    var todoTaskList1 = createMockTodoTaskList("ID1", "Tasks", WellknownListName.DefaultList);
    var todoTaskList2 = createMockTodoTaskList("ID2", "Flagged Emails",
        WellknownListName.FlaggedEmails);
    var todoTaskList3 = createMockTodoTaskList("ID3", "Specific Tasks", WellknownListName.None);
    if (hasCreatedList) {
      var todoTaskList4 = createMockTodoTaskList("ID4",
          hasUpdateListName ? TEST_TASK_LIST_NAME_UPDATED : TEST_TASK_LIST_NAME,
          WellknownListName.None);
      return List.of(todoTaskList1, todoTaskList2, todoTaskList3, todoTaskList4);
    } else {
      return List.of(todoTaskList1, todoTaskList2, todoTaskList3);
    }
  }

  private TodoTaskList createMockTodoTaskList(String id, String displayName,
      WellknownListName wellknownListName) {
    var todoTaskList = new TodoTaskList();
    todoTaskList.setId(id);
    todoTaskList.setDisplayName(displayName);
    todoTaskList.setWellknownListName(wellknownListName);
    todoTaskList.setIsOwner(true);
    todoTaskList.setIsShared(false);
    return todoTaskList;
  }

  @Test
  void shouldGetListOfTaskLists() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .get()
                            .getValue()).willReturn(createMockTodoTaskLists(false, false));

    // When
    var result = service.getListOfTaskLists(TEST_USER_ID);

    // Then
    assertThat(result).isNotNull()
                      .asInstanceOf(InstanceOfAssertFactories.list(TaskListDto.class))
                      .isNotEmpty()
                      .hasSize(3);
  }

  @Test
  void shouldGetEmptyListOfTaskLists() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .get()).willReturn(null);

    // When
    var result = service.getListOfTaskLists(TEST_USER_ID);

    // Then
    assertThat(result).isNotNull()
                      .asInstanceOf(InstanceOfAssertFactories.list(TaskDto.class))
                      .isEmpty();
  }

  @Test
  void shouldGetOneTaskListById() {
    // Mock Setup
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .get()
                            .getValue()).willReturn(createMockTodoTaskLists(true, false));
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .byTodoTaskListId("ID4")
                            .get()).willReturn(
        createMockTodoTaskList("ID4", TEST_TASK_LIST_NAME, WellknownListName.None));

    // Given
    var taskListForId = service.getTaskListByDisplayName(TEST_USER_ID, TEST_TASK_LIST_NAME);
    assertThat(taskListForId).isPresent()
                             .get()
                             .extracting("id")
                             .isNotNull();
    var taskListId = taskListForId.get()
                                  .getId();

    // When
    var taskList = service.getTaskListById(TEST_USER_ID, taskListId);

    // Then
    assertThat(taskList).isPresent()
                        .get()
                        .returns(TEST_TASK_LIST_NAME, TaskListDto::getDisplayName)
                        .extracting("id")
                        .isNotNull();
  }

  @Test
  void shouldGetNullTaskListById() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .get()).willReturn(null);

    // When
    var taskList = service.getTaskListById(TEST_USER_ID, TEST_TASK_LIST_ID);

    // Then
    assertThat(taskList).isEmpty();
  }

  @Test
  void shouldGetOneTaskListByDisplayName() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .get()
                            .getValue()).willReturn(createMockTodoTaskLists(true, false));

    // When
    var taskList = service.getTaskListByDisplayName(TEST_USER_ID, TEST_TASK_LIST_NAME);

    // Then
    assertThat(taskList).isPresent()
                        .get()
                        .returns(TEST_TASK_LIST_NAME, TaskListDto::getDisplayName)
                        .extracting("id")
                        .isNotNull();
  }

  @Test
  void shouldCreateTaskList() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .post(any(TodoTaskList.class))).willReturn(
        createMockTodoTaskList("ID4", TEST_TASK_LIST_NAME, WellknownListName.None));

    // When
    var newTaskList = new CreateUpdateTaskListDto();
    newTaskList.setDisplayName(TEST_TASK_LIST_NAME);
    var createdTaskList = service.createTaskList(TEST_USER_ID, newTaskList);

    // Then
    assertThat(createdTaskList).isPresent()
                               .get()
                               .returns(TEST_TASK_LIST_NAME, TaskListDto::getDisplayName)
                               .returns(true, TaskListDto::getOwner)
                               .returns(false, TaskListDto::getShared)
                               .returns(WellknownListNameDto.NONE,
                                   TaskListDto::getWellknownListName)
                               .extracting("id")
                               .isNotNull();
  }

  @Test
  void shouldUpdateTaskList() {
    // Mock Setup
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .get()
                            .getValue()).willReturn(createMockTodoTaskLists(true, false));
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .byTodoTaskListId("ID4")
                            .patch(any(TodoTaskList.class))).willReturn(
        createMockTodoTaskList("ID4", TEST_TASK_LIST_NAME_UPDATED, WellknownListName.None));

    // Given
    var taskListForId = service.getTaskListByDisplayName(TEST_USER_ID, TEST_TASK_LIST_NAME);
    assertThat(taskListForId).isPresent()
                             .get()
                             .extracting("id")
                             .isNotNull();
    var taskListId = taskListForId.get()
                                  .getId();

    // When
    var modifiedTaskList = new CreateUpdateTaskListDto();
    // only display name of task list is evaluated in update
    modifiedTaskList.setDisplayName(TEST_TASK_LIST_NAME_UPDATED);
    var updatedTaskList = service.updateTaskList(TEST_USER_ID, taskListId, modifiedTaskList);

    // Then
    assertThat(updatedTaskList).isPresent()
                               .get()
                               .returns(taskListId, TaskListDto::getId)
                               .returns(TEST_TASK_LIST_NAME_UPDATED, TaskListDto::getDisplayName)
                               .returns(true, TaskListDto::getOwner)
                               .returns(false, TaskListDto::getShared)
                               .returns(WellknownListNameDto.NONE,
                                   TaskListDto::getWellknownListName);
  }

  @Test
  void shouldDeleteTaskList() {
    // Mock Setup
    // use this flag to determine if a task list was deleted or not.
    final AtomicBoolean wasDeleted = new AtomicBoolean(false);
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .get()
                            .getValue()).willAnswer(answer -> {
      if (wasDeleted.get()) {
        return createMockTodoTaskLists(false, false);
      } else {
        return createMockTodoTaskLists(true, true);
      }
    });

    // because Mockito does not allow for deep requests to be stubbed when using doAnswer with void method, we have
    // mock the taskListItemRequestBuilder in this case manually.
    final var taskListItemRequestBuilder = mock(TodoTaskListItemRequestBuilder.class);
    doAnswer(answer -> {
      wasDeleted.set(true);
      return (Void) null;
    }).when(taskListItemRequestBuilder)
      .delete();
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId("ID4")).willReturn(taskListItemRequestBuilder);

    // Given
    var userId = TEST_USER_ID;

    var taskListForId = service.getTaskListByDisplayName(userId, TEST_TASK_LIST_NAME_UPDATED);
    assertThat(taskListForId).isPresent()
                             .get()
                             .returns("ID4", TaskListDto::getId);
    var taskListId = taskListForId.get()
                                  .getId();

    // When
    service.deleteTaskList(userId, taskListId);

    // Then
    var checkTaskList = service.getTaskListByDisplayName(userId, TEST_TASK_LIST_NAME_UPDATED);
    assertThat(checkTaskList).isNotNull();
    assertThat(wasDeleted).returns(true, AtomicBoolean::get);
  }

  private List<TodoTask> createMockTodoTasks(boolean hasCreatedTask, boolean hasUpdatedTaskName) {
    var todoTaskList1 = createMockTodoTask("ID1", "Task 1", Importance.Normal,
        TaskStatus.NotStarted);
    var todoTaskList2 = createMockTodoTask("ID2", "Task 2", Importance.High, TaskStatus.InProgress);
    var todoTaskList3 = createMockTodoTask("ID3", "Task 3", Importance.Low, TaskStatus.Deferred);
    if (hasCreatedTask) {
      var todoTaskList4 = createMockTodoTask("ID4",
          hasUpdatedTaskName ? "Updated Task 4" : "Task 4", Importance.Normal,
          TaskStatus.NotStarted);
      return List.of(todoTaskList1, todoTaskList2, todoTaskList3, todoTaskList4);
    } else {
      return List.of(todoTaskList1, todoTaskList2, todoTaskList3);
    }
  }

  private TodoTask createMockTodoTask(String id, String title, Importance importance,
      TaskStatus status) {
    var todoTask = new TodoTask();
    todoTask.setId(id);
    todoTask.setTitle(title);
    todoTask.setImportance(importance);
    todoTask.setStatus(status);
    var itemBody = new ItemBody();
    itemBody.setContent("Item Body");
    itemBody.setContentType(BodyType.Text);
    todoTask.setBody(itemBody);
    return todoTask;
  }

  @Test
  void shouldGetListOfTasks() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .get()
                            .getValue()).willReturn(createMockTodoTasks(false, false));

    // When
    var result = service.getListOfTasks(TEST_USER_ID, TEST_TASK_LIST_ID);

    // Then
    assertThat(result).isNotNull()
                      .asInstanceOf(InstanceOfAssertFactories.list(TaskDto.class))
                      .isNotEmpty()
                      .hasSize(3);
  }

  @Test
  void shouldGetEmptyListOfTasks() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .get()).willReturn(null);

    // When
    var result = service.getListOfTasks(TEST_USER_ID, TEST_TASK_LIST_ID);

    // Then
    assertThat(result).isNotNull()
                      .asInstanceOf(InstanceOfAssertFactories.list(TaskDto.class))
                      .isEmpty();

  }

  @Test
  void shouldGetTask() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .get()).willReturn(
        createMockTodoTask("ID1", "Title 01", Importance.High, TaskStatus.NotStarted));

    // When
    var task = service.getTask(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01);

    // Then
    assertThat(task).isNotEmpty()
                    .get()
                    .isNotNull()
                    .returns("ID1", TaskDto::getId)
                    .returns("Title 01", TaskDto::getTitle)
                    .returns(ImportanceDto.HIGH, TaskDto::getImportance)
                    .returns(TaskStatusDto.NOT_STARTED, TaskDto::getStatus);
  }

  @Test
  void shouldGetEmptyTask() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .get()).willReturn(null);

    // When
    var task = service.getTask(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01);

    // Then
    assertThat(task).isEmpty();
  }

  @Test
  void shouldCreateTask() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .post(any(TodoTask.class))).willReturn(
        createMockTodoTask(TEST_TASK_ID_04, "Title 04", Importance.High, TaskStatus.InProgress));

    // When
    var newTask = new CreateUpdateTaskDto();
    newTask.setTitle("Title 04");
    newTask.setBody("Item Body");
    newTask.setImportance(ImportanceDto.HIGH);
    newTask.setStatus(TaskStatusDto.IN_PROGRESS);
    var createdTask = service.createTask(TEST_USER_ID, TEST_TASK_LIST_ID, newTask);

    // Then
    assertThat(createdTask).isPresent()
                           .get()
                           .returns("Title 04", TaskDto::getTitle)
                           .returns(ImportanceDto.HIGH, TaskDto::getImportance)
                           .returns(TaskStatusDto.IN_PROGRESS, TaskDto::getStatus)
                           .returns("Item Body", TaskDto::getBody)
                           .extracting("id")
                           .isNotNull();
  }

  @Test
  void shouldUpdateTask() {
    // Mock Setup
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_04)
                            .get()).willReturn(
        createMockTodoTask(TEST_TASK_ID_04, "Task 04", Importance.Normal, TaskStatus.NotStarted));
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_04)
                            .patch(any(TodoTask.class))).willReturn(
        createMockTodoTask(TEST_TASK_ID_04, "Updated Task 04", Importance.Normal,
            TaskStatus.NotStarted));

    // Given
    var taskForId = service.getTask(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_04);
    assertThat(taskForId).isPresent()
                         .get()
                         .returns(TEST_TASK_ID_04, TaskDto::getId)
                         .returns("Task 04", TaskDto::getTitle);

    // When
    var modifiedTask = new CreateUpdateTaskDto();
    // only display name of task list is evaluated in update
    modifiedTask.setTitle("Updated Task 04");
    var updatedTask = service.updateTask(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_04,
        modifiedTask);

    // Then
    assertThat(updatedTask).isPresent()
                           .get()
                           .returns(TEST_TASK_ID_04, TaskDto::getId)
                           .returns("Updated Task 04", TaskDto::getTitle)
                           .returns(ImportanceDto.NORMAL, TaskDto::getImportance)
                           .returns(TaskStatusDto.NOT_STARTED, TaskDto::getStatus);
  }

  @Test
  void shouldDeleteTask() {
    // Mock Setup
    // use this flag to determine if a task  was deleted or not.
    final AtomicBoolean wasDeleted = new AtomicBoolean(false);
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .get()).willAnswer(answer -> {
      if (wasDeleted.get()) {
        return createMockTodoTasks(false, false);
      } else {
        return createMockTodoTasks(true, true);
      }
    });

    // because Mockito does not allow for deep requests to be stubbed when using doAnswer with void method, we have
    // mock the taskItemRequestBuilder in this case manually.
    final var taskItemRequestBuilder = mock(TodoTaskItemRequestBuilder.class);
    doAnswer(answer -> {
      wasDeleted.set(true);
      return (Void) null;
    }).when(taskItemRequestBuilder)
      .delete();
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_04)).willReturn(taskItemRequestBuilder);

    // Given / When
    service.deleteTask(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_04);

    // Then
    var checkTask = service.getTask(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_04);
    assertThat(checkTask).isEmpty();
    assertThat(wasDeleted).returns(true, AtomicBoolean::get);
  }

  private List<ChecklistItem> createMockCheckListItems(boolean hasCreatedCheckListItem,
      boolean hasUpdatedCheckListItemName) {
    var checkListItemList1 = createMockCheckListItem("ID1", "CheckListItem 1", Boolean.FALSE);
    var checkListItemList2 = createMockCheckListItem("ID2", "CheckListItem 2", Boolean.TRUE);
    var checkListItemList3 = createMockCheckListItem("ID3", "CheckListItem 3", Boolean.FALSE);
    if (hasCreatedCheckListItem) {
      var checkListItemList4 = createMockCheckListItem("ID4",
          hasUpdatedCheckListItemName ? "Updated CheckListItem 4" : "CheckListItem 4",
          Boolean.FALSE);
      return List.of(checkListItemList1, checkListItemList2, checkListItemList3,
          checkListItemList4);
    } else {
      return List.of(checkListItemList1, checkListItemList2, checkListItemList3);
    }
  }

  private ChecklistItem createMockCheckListItem(String id, String displayName, Boolean checked) {
    var checkListItem = new ChecklistItem();
    checkListItem.setId(id);
    checkListItem.setDisplayName(displayName);
    checkListItem.setIsChecked(checked);
    checkListItem.setCheckedDateTime(checked == Boolean.TRUE ? OFFSET_DATE_TIME : null);
    return checkListItem;
  }

  @Test
  void shouldGetListOfCheckListItems() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .get()
                            .getValue()).willReturn(createMockCheckListItems(false, false));

    // When
    var result = service.getListOfCheckListItems(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01);

    // Then
    assertThat(result).isNotNull()
                      .asInstanceOf(InstanceOfAssertFactories.list(CheckListItemDto.class))
                      .isNotEmpty()
                      .hasSize(3)
                      .satisfiesExactly(
                          item1 -> assertThat(item1).returns("ID1", CheckListItemDto::getId)
                                                    .returns("CheckListItem 1",
                                                        CheckListItemDto::getDisplayName)
                                                    .returns(Boolean.FALSE,
                                                        CheckListItemDto::getChecked)
                                                    .returns(null,
                                                        CheckListItemDto::getCheckedDateTime),
                          item2 -> assertThat(item2).returns("ID2", CheckListItemDto::getId)
                                                    .returns("CheckListItem 2",
                                                        CheckListItemDto::getDisplayName)
                                                    .returns(Boolean.TRUE,
                                                        CheckListItemDto::getChecked)
                                                    .returns(OFFSET_DATE_TIME,
                                                        CheckListItemDto::getCheckedDateTime),
                          item3 -> assertThat(item3).returns("ID3", CheckListItemDto::getId)
                                                    .returns("CheckListItem 3",
                                                        CheckListItemDto::getDisplayName)
                                                    .returns(Boolean.FALSE,
                                                        CheckListItemDto::getChecked)
                                                    .returns(null,
                                                        CheckListItemDto::getCheckedDateTime));
  }

  @Test
  void shouldGetEmptyListOfCheckListItems() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .get()).willReturn(null);

    // When
    var result = service.getListOfCheckListItems(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01);

    // Then
    assertThat(result).isNotNull()
                      .asInstanceOf(InstanceOfAssertFactories.list(CheckListItemDto.class))
                      .isEmpty();
  }

  @Test
  void shouldGetCheckListItem() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .byChecklistItemId(TEST_CHECK_LIST_ITEM_ID_01)
                            .get()).willReturn(
        createMockCheckListItem(TEST_CHECK_LIST_ITEM_ID_01, "CheckListItem 1", Boolean.FALSE));

    // When
    var checkListItem = service.getCheckListItem(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01,
        TEST_CHECK_LIST_ITEM_ID_01);

    // Then
    assertThat(checkListItem).isNotEmpty()
                             .get()
                             .isNotNull()
                             .returns(TEST_CHECK_LIST_ITEM_ID_01, CheckListItemDto::getId)
                             .returns("CheckListItem 1", CheckListItemDto::getDisplayName)
                             .returns(Boolean.FALSE, CheckListItemDto::getChecked)
                             .returns(null, CheckListItemDto::getCheckedDateTime);
  }

  @Test
  void shouldGetEmptyCheckListItem() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .byChecklistItemId(TEST_CHECK_LIST_ITEM_ID_01)
                            .get()).willReturn(null);

    // When
    var checkListItem = service.getCheckListItem(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01,
        TEST_CHECK_LIST_ITEM_ID_01);

    // Then
    assertThat(checkListItem).isEmpty();
  }

  @Test
  void shouldCreateCheckListItem() {
    // Given / Mock Setup
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .post(any(ChecklistItem.class))).willReturn(
        createMockCheckListItem(TEST_CHECK_LIST_ITEM_ID_04, "CheckListItem 4", Boolean.FALSE));

    // When
    var newCheckListItem = new CreateUpdateCheckListItemDto();
    newCheckListItem.setDisplayName("CheckListItem 4");
    newCheckListItem.setChecked(Boolean.FALSE);
    var createdCheckListItem = service.createCheckListItem(TEST_USER_ID, TEST_TASK_LIST_ID,
        TEST_TASK_ID_01, newCheckListItem);

    // Then
    assertThat(createdCheckListItem).isPresent()
                                    .get()
                                    .returns("CheckListItem 4", CheckListItemDto::getDisplayName)
                                    .returns(Boolean.FALSE, CheckListItemDto::getChecked)
                                    .returns(null, CheckListItemDto::getCheckedDateTime)
                                    .extracting(CheckListItemDto::getId)
                                    .isNotNull();
  }

  @Test
  void shouldUpdateCheckListItem() {
    // Mock Setup
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_04)
                            .checklistItems()
                            .byChecklistItemId(TEST_CHECK_LIST_ITEM_ID_04)
                            .get()).willReturn(
        createMockCheckListItem(TEST_CHECK_LIST_ITEM_ID_04, "CheckListItem 4", Boolean.FALSE));
    given(graphServiceClient.users()
                            .byUserId(anyString())
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_04)
                            .checklistItems()
                            .byChecklistItemId(TEST_CHECK_LIST_ITEM_ID_04)
                            .patch(any(ChecklistItem.class))).willReturn(
        createMockCheckListItem(TEST_CHECK_LIST_ITEM_ID_04, "Updated CheckListItem 4",
            Boolean.FALSE));

    // Given / When

    // When
    var modifiedCheckListItem = new CreateUpdateCheckListItemDto();
    // only display name of task list is evaluated in update
    modifiedCheckListItem.setDisplayName("Updated CheckListItem 4");
    var updatedCheckListItem = service.updateCheckListItem(TEST_USER_ID, TEST_TASK_LIST_ID,
        TEST_TASK_ID_04, TEST_CHECK_LIST_ITEM_ID_04, modifiedCheckListItem);

    // Then
    assertThat(updatedCheckListItem).isPresent()
                                    .get()
                                    .returns(TEST_CHECK_LIST_ITEM_ID_04, CheckListItemDto::getId)
                                    .returns("Updated CheckListItem 4",
                                        CheckListItemDto::getDisplayName)
                                    .returns(Boolean.FALSE, CheckListItemDto::getChecked)
                                    .returns(null, CheckListItemDto::getCheckedDateTime);
  }

  @Test
  void shouldDeleteCheckListItem() {
    // Mock Setup
    // use this flag to determine if a check list item  was deleted or not.
    final AtomicBoolean wasDeleted = new AtomicBoolean(false);
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .byChecklistItemId(TEST_CHECK_LIST_ITEM_ID_04)
                            .get()).willAnswer(answer -> {
      if (wasDeleted.get()) {
        return createMockCheckListItems(false, false);
      } else {
        return createMockCheckListItems(true, true);
      }
    });

    // because Mockito does not allow for deep requests to be stubbed when using doAnswer with void method, we have
    // mock the checkListItemRequestBuilder in this case manually.
    final var checklistItemRequestBuilder = mock(ChecklistItemItemRequestBuilder.class);
    doAnswer(answer -> {
      wasDeleted.set(true);
      return (Void) null;
    }).when(checklistItemRequestBuilder)
      .delete();
    given(graphServiceClient.users()
                            .byUserId(TEST_USER_ID)
                            .todo()
                            .lists()
                            .byTodoTaskListId(TEST_TASK_LIST_ID)
                            .tasks()
                            .byTodoTaskId(TEST_TASK_ID_01)
                            .checklistItems()
                            .byChecklistItemId(TEST_CHECK_LIST_ITEM_ID_04)).willReturn(
        checklistItemRequestBuilder);

    // Given / When
    service.deleteCheckListItem(TEST_USER_ID, TEST_TASK_LIST_ID, TEST_TASK_ID_01,
        TEST_CHECK_LIST_ITEM_ID_04);

    // Then
    var checkCheckListItem = service.getCheckListItem(TEST_USER_ID, TEST_TASK_LIST_ID,
        TEST_TASK_ID_01, TEST_CHECK_LIST_ITEM_ID_04);
    assertThat(checkCheckListItem).isEmpty();
    assertThat(wasDeleted).returns(true, AtomicBoolean::get);
  }
}
