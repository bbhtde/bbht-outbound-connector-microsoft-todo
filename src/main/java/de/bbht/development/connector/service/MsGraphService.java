package de.bbht.development.connector.service;

import com.microsoft.graph.models.ChecklistItem;
import com.microsoft.graph.models.ChecklistItemCollectionResponse;
import com.microsoft.graph.models.TodoTask;
import com.microsoft.graph.models.TodoTaskCollectionResponse;
import com.microsoft.graph.models.TodoTaskList;
import com.microsoft.graph.models.TodoTaskListCollectionResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.kiota.ApiException;
import de.bbht.development.connector.service.dto.checklistitem.CheckListItemDto;
import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.TaskDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import de.bbht.development.connector.service.mapper.CheckListItemMapper;
import de.bbht.development.connector.service.mapper.TaskListMapper;
import de.bbht.development.connector.service.mapper.TaskMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MsGraphService {

  private final GraphServiceClient graphServiceClient;

  MsGraphService(GraphServiceClient graphServiceClient) {
    this.graphServiceClient = graphServiceClient;
  }

  public List<TaskListDto> getListOfTaskLists(final String userId) {
    return handleException(() -> {
      TodoTaskListCollectionResponse response = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .get();
      final List<TodoTaskList> listOfTaskLists;
      if (response != null) {
        listOfTaskLists = response.getValue();
      } else {
        listOfTaskLists = new ArrayList<>();
      }
      return TaskListMapper.mapTaskLists(listOfTaskLists);
    });
  }

  private <T> T handleException(Supplier<T> supplier) {
    try {
      return supplier.get();
    } catch (ApiException e) {
      throw new MsGraphException(e.getMessage(), String.valueOf(e.getResponseStatusCode()));
    } catch (Exception e) {
      throw new MsGraphException(e.getMessage());
    }

  }

  public Optional<TaskListDto> getTaskListById(String userId, String taskListId) {
    return handleException(() -> {
      final TodoTaskList taskList = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .get();
      return Optional.ofNullable(TaskListMapper.mapTaskList(taskList));
    });
  }

  public Optional<TaskListDto> getTaskListByDisplayName(String userId, String displayName) {
    return handleException(() -> {
      TodoTaskList taskList = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .get()
          .getValue()
          .stream()
          .filter(ftl -> displayName.equals(ftl.getDisplayName()))
          .findFirst()
          .orElse(null);
      return Optional.ofNullable(TaskListMapper.mapTaskList(taskList));
    });
  }

  public Optional<TaskListDto> createTaskList(String userId, CreateUpdateTaskListDto createTaskListDto) {
    return handleException(() -> {
      final TodoTaskList createTaskList = TaskListMapper.mapCreateUpdateTaskListDto(createTaskListDto);

      final TodoTaskList createdTaskList = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .post(createTaskList);
      return Optional.ofNullable(TaskListMapper.mapTaskList(createdTaskList));
    });
  }

  public Optional<TaskListDto> updateTaskList(String userId, String taskListId,
      CreateUpdateTaskListDto updateTaskListDto) {
    return handleException(() -> {
      final TodoTaskList updateTaskList = TaskListMapper.mapCreateUpdateTaskListDto(updateTaskListDto);

      final TodoTaskList updatedTaskList = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .patch(updateTaskList);
      return Optional.ofNullable(TaskListMapper.mapTaskList(updatedTaskList));
    });
  }

  public Void deleteTaskList(String userId, String taskListId) {
    return handleException(() -> {
      graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .delete();
      return null;
    });
  }

  public List<TaskDto> getListOfTasks(String userId, String taskListId) {
    return handleException(() -> {
      TodoTaskCollectionResponse response = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .get();
      final List<TodoTask> listOfTasks;
      if (response != null) {
        listOfTasks = response.getValue();
      } else {
        listOfTasks = new ArrayList<>();
      }
      return TaskMapper.mapTasks(listOfTasks);
    });
  }

  public Optional<TaskDto> getTask(String userId, String taskListId, String taskId) {
    return handleException(() -> {
      final TodoTask task = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .get();
      return Optional.ofNullable(TaskMapper.mapTask(task));
    });
  }

  public Optional<TaskDto> createTask(String userId, String taskListId, CreateUpdateTaskDto createTaskDto) {
    return handleException(() -> {
      final TodoTask createTask = TaskMapper.mapCreateUpdateTaskDto(createTaskDto);

      final TodoTask createdTask = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .post(createTask);
      return Optional.ofNullable(TaskMapper.mapTask(createdTask));
    });
  }

  public Optional<TaskDto> updateTask(String userId, String taskListId, String taskId,
      CreateUpdateTaskDto updateTaskDto) {
    return handleException(() -> {
      final TodoTask updateTask = TaskMapper.mapCreateUpdateTaskDto(updateTaskDto);

      final TodoTask updatedTask = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .patch(updateTask);
      return Optional.ofNullable(TaskMapper.mapTask(updatedTask));
    });
  }

  public Void deleteTask(String userId, String taskListId, String taskId) {
    return handleException(() -> {
      graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .delete();
      return null;
    });
  }

  public List<CheckListItemDto> getListOfCheckListItems(String userId, String taskListId, String taskId) {
    return handleException(() -> {
      ChecklistItemCollectionResponse response = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .checklistItems()
          .get();
      final List<ChecklistItem> listOfCheckListItems;
      if (response != null) {
        listOfCheckListItems = response.getValue();
      } else {
        listOfCheckListItems = new ArrayList<>();
      }
      return CheckListItemMapper.mapCheckListItems(listOfCheckListItems);
    });
  }

  public Optional<CheckListItemDto> getCheckListItem(String userId, String taskListId, String taskId,
      String checkListItemId) {
    return handleException(() -> {
      final ChecklistItem checkListItem = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .checklistItems()
          .byChecklistItemId(checkListItemId)
          .get();
      return Optional.ofNullable(CheckListItemMapper.mapCheckListItem(checkListItem));
    });
  }

  public Optional<CheckListItemDto> createCheckListItem(String userId, String taskListId, String taskId,
      CreateUpdateCheckListItemDto createCheckListItemDto) {
    return handleException(() -> {
      final ChecklistItem createCheckListItem = CheckListItemMapper.mapCreateUpdateCheckListItemDto(
          createCheckListItemDto);

      final ChecklistItem createdCheckListItem = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .checklistItems()
          .post(createCheckListItem);
      return Optional.ofNullable(CheckListItemMapper.mapCheckListItem(createdCheckListItem));
    });
  }

  public Optional<CheckListItemDto> updateCheckListItem(String userId, String taskListId, String taskId,
      String checkListItemId, CreateUpdateCheckListItemDto updateCheckListItemDto) {
    return handleException(() -> {
      final ChecklistItem updateCheckListItem = CheckListItemMapper.mapCreateUpdateCheckListItemDto(
          updateCheckListItemDto);

      final ChecklistItem updatedCheckListItem = graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .checklistItems()
          .byChecklistItemId(checkListItemId)
          .patch(updateCheckListItem);
      return Optional.ofNullable(CheckListItemMapper.mapCheckListItem(updatedCheckListItem));
    });
  }

  public Void deleteCheckListItem(String userId, String taskListId, String taskId, String checkListItemId) {
    return handleException(() -> {
      graphServiceClient.users()
          .byUserId(userId)
          .todo()
          .lists()
          .byTodoTaskListId(taskListId)
          .tasks()
          .byTodoTaskId(taskId)
          .checklistItems()
          .byChecklistItemId(checkListItemId)
          .delete();
      return null;
    });
  }
}
