package de.bbht.development.connector.todo;

import de.bbht.development.connector.service.IMsGraphServiceFactory;
import de.bbht.development.connector.service.MsGraphException;
import de.bbht.development.connector.service.MsGraphService;
import de.bbht.development.connector.service.MsGraphServiceFactory;
import de.bbht.development.connector.service.dto.GraphAuthenticationDto;
import de.bbht.development.connector.service.dto.checklistitem.CheckListItemDto;
import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.task.CreateUpdateTaskDto;
import de.bbht.development.connector.service.dto.task.TaskDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import de.bbht.development.connector.todo.entity.*;
import de.bbht.development.connector.todo.mapper.OptionMapper;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@OutboundConnector(name = "BBHT_TODO_CONNECTOR", inputVariables = {},
                   type = "bbht.connector:mstodo:1")
@ElementTemplate(id = "bbht.connector.MsToDo.v1", name = "Connector for Microsoft To Do",
                 version = 1, description = "Outbound Connector to manage items in Microsoft To Do",
                 documentationRef = "https://www.bbht.de/", icon = "icon.svg",
                 // in resources Folder
                 inputDataClass = ToDoConnectorRequest.class, propertyGroups = {
    @ElementTemplate.PropertyGroup(id = "authentication", label = "Graph Authentication"),
    @ElementTemplate.PropertyGroup(id = "operation", label = "Operations"),
    @ElementTemplate.PropertyGroup(id = "tasklist", label = "Task List Parameters"),
    @ElementTemplate.PropertyGroup(id = "task", label = "Task Parameters"),
    @ElementTemplate.PropertyGroup(id = "checklistitem", label = "Check List Item Parameters")})
public class ToDoConnectorFunction implements OutboundConnectorFunction {

  private IMsGraphServiceFactory graphServiceFactory;

  public ToDoConnectorFunction() {
    // empty constructor
  }

  public void setGraphServiceFactory(IMsGraphServiceFactory graphServiceFactory) {
    this.graphServiceFactory = graphServiceFactory;
  }

  @Override
  public Object execute(OutboundConnectorContext outboundConnectorContext) {
    final ToDoConnectorRequest connectorRequest = outboundConnectorContext.bindVariables(
        ToDoConnectorRequest.class);
    return switch (connectorRequest.operation()
        .operation()) {
      // execute task list operations
      case LIST_TASK_LISTS -> getListOfTaskLists(connectorRequest);
      case GET_TASK_LIST -> getTaskList(connectorRequest);
      case CREATE_TASK_LIST -> createTaskList(connectorRequest);
      case UPDATE_TASK_LIST -> updateTaskList(connectorRequest);
      case DELETE_TASK_LIST -> deleteTaskList(connectorRequest);

      // execute task operations
      case LIST_TASKS -> getListOfTasks(connectorRequest);
      case GET_TASK -> getTask(connectorRequest);
      case CREATE_TASK -> createTask(connectorRequest);
      case UPDATE_TASK -> updateTask(connectorRequest);
      case DELETE_TASK -> deleteTask(connectorRequest);

      // execute check list item operations
      case LIST_CHECK_LIST_ITEMS -> getListOfCheckListItems(connectorRequest);
      case GET_CHECK_LIST_ITEM -> getCheckListItem(connectorRequest);
      case CREATE_CHECK_LIST_ITEM -> createCheckListItem(connectorRequest);
      case UPDATE_CHECK_LIST_ITEM -> updateCheckListItem(connectorRequest);
      case DELETE_CHECK_LIST_ITEM -> deleteCheckListItem(connectorRequest);

      // fallback
      case null -> ConnectorResult.<Void>withErrorResult("No operation defined.", null);
    };
  }

  private MsGraphService createGraphService(GraphAuthentication authentication) {
    if (graphServiceFactory == null) {
      this.graphServiceFactory = new MsGraphServiceFactory();
    }
    final GraphAuthenticationDto authInfo = new GraphAuthenticationDto(authentication.tenantId(),
        authentication.clientId(), authentication.clientSecret());
    return graphServiceFactory.create(authInfo);
  }

  private <O, T> ConnectorResult<T> executeRequestInternal(ToDoConnectorRequest connectorRequest, Function<ToDoConnectorRequest, O> optionExtractor,

                                                           Predicate<T> additionalEmptyCheck, QuadFunction<GraphAuthentication, Operation, O, MsGraphService, T> serviceCall) {

    final GraphAuthentication authentication = connectorRequest.authentication();
    final Operation operation = connectorRequest.operation();
    final O options;
    if (optionExtractor != null) {
      options = optionExtractor.apply(connectorRequest);
    } else {
      options = null;
    }
    final MsGraphService service = createGraphService(authentication);

    try {
      T result = serviceCall.apply(authentication, operation, options, service);
      boolean nullResult = false;
      if (additionalEmptyCheck != null) {
        nullResult = additionalEmptyCheck.test(result);
      }
      if (result != null && !nullResult) {
        return ConnectorResult.withGivenResult(result);
      } else {
        return ConnectorResult.<T>withEmptyResult();
      }
    } catch (MsGraphException graphException) {
      return ConnectorResult.<T>withErrorResult(graphException.getMessage(), String.valueOf(graphException.getCode()));
    } catch (Exception e) {
      return ConnectorResult.<T>withErrorResult(e.getMessage(), null);
    }
  }

  private ConnectorResult<List<TaskListDto>> getListOfTaskLists(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskListOptions, MsGraphService, List<TaskListDto>> listTaskLists = (authentication, operation, options, service) -> service.getListOfTaskLists(
        operation.userIdOrPrincipalName());
    return executeRequestInternal(connectorRequest, null, List::isEmpty, listTaskLists);
  }

  private ConnectorResult<TaskListDto> getTaskList(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskListOptions, MsGraphService, TaskListDto> getTaskList = (authentication, operation, options, service) -> service.getTaskListById(
        operation.userIdOrPrincipalName(), operation.taskListId()).orElse(null);

    return executeRequestInternal(connectorRequest, null, null, getTaskList);
  }

  private ConnectorResult<TaskListDto> createTaskList(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskListOptions, MsGraphService, TaskListDto> createTaskList = (authentication, operation, options, service) -> {
      final CreateUpdateTaskListDto taskListToCreate = OptionMapper.mapFromTaskListOptions(options);
      return service.createTaskList(operation.userIdOrPrincipalName(), taskListToCreate).orElse(null);
    };

    return executeRequestInternal(connectorRequest, ToDoConnectorRequest::taskListOptions, null, createTaskList);
  }

  private ConnectorResult<TaskListDto> updateTaskList(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, UpdateTaskListOptions, MsGraphService, TaskListDto> updateTaskList = (authentication, operation, options, service) -> {
      final CreateUpdateTaskListDto taskListToUpdate = OptionMapper.mapFromUpdateTaskListOptions(options);
      return service.updateTaskList(operation.userIdOrPrincipalName(), operation.taskListId(),
          taskListToUpdate).orElse(null);
    };

    return executeRequestInternal(connectorRequest, ToDoConnectorRequest::updateTaskListOptions, null, updateTaskList);
  }

  private ConnectorResult<Void> deleteTaskList(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskListOptions, MsGraphService, Void> deleteTaskList = (authentication, operation, options, service) -> service.deleteTaskList(
        operation.userIdOrPrincipalName(), operation.taskListId());

    return executeRequestInternal(connectorRequest, null, null, deleteTaskList);
  }

  private ConnectorResult<List<TaskDto>> getListOfTasks(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskOptions, MsGraphService, List<TaskDto>> listTasks = (authentication, operation, options, service) -> service.getListOfTasks(
        operation.userIdOrPrincipalName(), operation.taskListId());
    return executeRequestInternal(connectorRequest, null, List::isEmpty, listTasks);
  }

  private ConnectorResult<TaskDto> getTask(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskOptions, MsGraphService, TaskDto> getTask = (authentication, operation, options, service) -> service.getTask(
        operation.userIdOrPrincipalName(), operation.taskListId(), operation.taskId()).orElse(null);

    return executeRequestInternal(connectorRequest, null, null, getTask);
  }

  private ConnectorResult<TaskDto> createTask(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskOptions, MsGraphService, TaskDto> createTask = (authentication, operation, options, service) -> {
      final CreateUpdateTaskDto taskToCreate = OptionMapper.mapFromTaskOptions(options);
      return service.createTask(operation.userIdOrPrincipalName(), operation.taskListId(),
          taskToCreate).orElse(null);
    };

    return executeRequestInternal(connectorRequest, ToDoConnectorRequest::taskOptions, null, createTask);
  }

  private ConnectorResult<TaskDto> updateTask(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, UpdateTaskOptions, MsGraphService, TaskDto> updateTask = (authentication, operation, options, service) -> {
      final CreateUpdateTaskDto taskToUpdate = OptionMapper.mapFromUpdateTaskOptions(options);
      return service.updateTask(operation.userIdOrPrincipalName(), operation.taskListId(),
          operation.taskId(), taskToUpdate).orElse(null);
    };

    return executeRequestInternal(connectorRequest, ToDoConnectorRequest::updateTaskOptions, null, updateTask);
  }

  private ConnectorResult<Void> deleteTask(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, TaskOptions, MsGraphService, Void> deleteTask = (authentication, operation, options, service) -> service.deleteTask(
        operation.userIdOrPrincipalName(), operation.taskListId(), operation.taskId());

    return executeRequestInternal(connectorRequest, null, null, deleteTask);
  }

  private ConnectorResult<List<CheckListItemDto>> getListOfCheckListItems(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, CheckListItemOptions, MsGraphService, List<CheckListItemDto>> listCheckListItems = (authentication, operation, options, service) -> service.getListOfCheckListItems(
        operation.userIdOrPrincipalName(), operation.taskListId(), operation.taskId());
    return executeRequestInternal(connectorRequest, null, List::isEmpty, listCheckListItems);
  }

  private ConnectorResult<CheckListItemDto> getCheckListItem(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, CheckListItemOptions, MsGraphService, CheckListItemDto> getCheckListItem = (authentication, operation, options, service) -> service.getCheckListItem(
        operation.userIdOrPrincipalName(), operation.taskListId(), operation.taskId(),
        operation.checkListItemId()).orElse(null);

    return executeRequestInternal(connectorRequest, null, null, getCheckListItem);
  }

  private ConnectorResult<CheckListItemDto> createCheckListItem(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, CheckListItemOptions, MsGraphService, CheckListItemDto> createCheckListItem = (authentication, operation, options, service) -> {
      final CreateUpdateCheckListItemDto checkListItemToCreate = OptionMapper.mapFromCheckListItemOptions(
          options);
      return service.createCheckListItem(operation.userIdOrPrincipalName(), operation.taskListId(),
          operation.taskId(), checkListItemToCreate).orElse(null);
    };
    return executeRequestInternal(connectorRequest, ToDoConnectorRequest::checkListItemOptions, null, createCheckListItem);
  }

  private ConnectorResult<CheckListItemDto> updateCheckListItem(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, UpdateCheckListItemOptions, MsGraphService, CheckListItemDto> updateCheckListItem = (authentication, operation, options, service) -> {
      final CreateUpdateCheckListItemDto checkListItemToUpdate = OptionMapper.mapFromUpdateCheckListItemOptions(options);
      return service.updateCheckListItem(operation.userIdOrPrincipalName(), operation.taskListId(),
          operation.taskId(), operation.checkListItemId(), checkListItemToUpdate).orElse(null);
    };
    return executeRequestInternal(connectorRequest, ToDoConnectorRequest::updateCheckListItemOptions, null, updateCheckListItem);
  }

  private ConnectorResult<Void> deleteCheckListItem(ToDoConnectorRequest connectorRequest) {
    QuadFunction<GraphAuthentication, Operation, CheckListItemOptions, MsGraphService, Void> deleteCheckListItem = (authentication, operation, options, service) -> service.deleteCheckListItem(
        operation.userIdOrPrincipalName(), operation.taskListId(), operation.taskId(),
        operation.checkListItemId());

    return executeRequestInternal(connectorRequest, null, null, deleteCheckListItem);
  }
}
