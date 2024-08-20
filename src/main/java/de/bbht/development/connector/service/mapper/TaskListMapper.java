package de.bbht.development.connector.service.mapper;

import com.microsoft.graph.models.TodoTaskList;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import java.util.List;

public class TaskListMapper {

  private TaskListMapper() {
    // private constructor to prevent instantiation.
  }

  public static List<TaskListDto> mapTaskLists(List<TodoTaskList> listOfTaskLists) {
    return listOfTaskLists.stream()
        .map(TaskListMapper::mapTaskList)
        .toList();
  }

  public static TaskListDto mapTaskList(TodoTaskList todoTaskList) {
    TaskListDto taskListDto = null;
    if (todoTaskList != null) {
      taskListDto = new TaskListDto();
      taskListDto.setId(todoTaskList.getId());
      taskListDto.setDisplayName(todoTaskList.getDisplayName());
      taskListDto.setShared(todoTaskList.getIsShared());
      taskListDto.setOwner(todoTaskList.getIsOwner());
      taskListDto.setWellknownListName(
          EnumMapper.mapWellknownListName(todoTaskList.getWellknownListName()));
    }
    return taskListDto;
  }

  public static TodoTaskList mapCreateUpdateTaskListDto(
      CreateUpdateTaskListDto createUpdateTaskListDto) {
    TodoTaskList todoTaskList = null;
    if (createUpdateTaskListDto != null) {
      todoTaskList = new TodoTaskList();
      todoTaskList.setDisplayName(createUpdateTaskListDto.getDisplayName());
    }
    return todoTaskList;
  }
}
