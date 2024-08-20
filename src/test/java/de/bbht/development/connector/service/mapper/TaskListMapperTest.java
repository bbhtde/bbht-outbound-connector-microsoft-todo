package de.bbht.development.connector.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.graph.models.TodoTaskList;
import com.microsoft.graph.models.WellknownListName;
import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import java.util.List;
import org.junit.jupiter.api.Test;

class TaskListMapperTest {

  @Test
  void shouldMapTaskListToTaskListDto() {
    // given
    var todoTaskList = new TodoTaskList();
    todoTaskList.setId("ID");
    todoTaskList.setWellknownListName(WellknownListName.None);
    todoTaskList.setIsOwner(true);
    todoTaskList.setIsShared(true);
    todoTaskList.setDisplayName("Display Name");

    // when
    var taskListDto = TaskListMapper.mapTaskList(todoTaskList);

    // then
    assertThat(taskListDto).returns("ID", TaskListDto::getId)
        .returns(WellknownListNameDto.NONE, TaskListDto::getWellknownListName)
        .returns(true, TaskListDto::getOwner)
        .returns(true, TaskListDto::getShared)
        .returns("Display Name", TaskListDto::getDisplayName);
  }

  @Test
  void shouldMapCreateUpdateTaskListDtoToTaskList() {
    // given
    var createUpdateTaskListDto = new CreateUpdateTaskListDto();
    createUpdateTaskListDto.setDisplayName("Display Name");

    // when
    var todoTaskList = TaskListMapper.mapCreateUpdateTaskListDto(createUpdateTaskListDto);

    // then
    assertThat(todoTaskList).returns("Display Name", TodoTaskList::getDisplayName)
        .returns(null, TodoTaskList::getId)
        .returns(null, TodoTaskList::getWellknownListName);
  }

  @Test
  void shouldMapIncompleteCreateUpdateTaskListDtoToTaskList() {
    // given
    var createUpdateTaskListDto = new CreateUpdateTaskListDto();

    // when
    var todoTaskList = TaskListMapper.mapCreateUpdateTaskListDto(createUpdateTaskListDto);

    // then
    assertThat(todoTaskList).returns(null, TodoTaskList::getDisplayName)
        .returns(null, TodoTaskList::getId)
        .returns(null, TodoTaskList::getWellknownListName);
  }

  @Test
  void shouldMapListOfTodoTaskListToListOfTaskListDto() {
    // given
    var todoTaskList1 = new TodoTaskList();
    todoTaskList1.setId("ID1");
    todoTaskList1.setWellknownListName(WellknownListName.None);
    todoTaskList1.setIsOwner(true);
    todoTaskList1.setIsShared(true);
    todoTaskList1.setDisplayName("Display Name 1");

    var todoTaskList2 = new TodoTaskList();
    todoTaskList2.setId("ID2");
    todoTaskList2.setWellknownListName(WellknownListName.DefaultList);
    todoTaskList2.setIsOwner(false);
    todoTaskList2.setIsShared(false);
    todoTaskList2.setDisplayName("Display Name 2");

    var listOfTodoTaskLists = List.of(todoTaskList1, todoTaskList2);

    // when
    var listOfTaskListDto = TaskListMapper.mapTaskLists(listOfTodoTaskLists);

    // then
    assertThat(listOfTaskListDto).hasSize(2)
        .satisfiesExactly(taskList1 -> assertThat(taskList1).returns("ID1", TaskListDto::getId)
                .returns(WellknownListNameDto.NONE, TaskListDto::getWellknownListName)
                .returns(true, TaskListDto::getOwner)
                .returns(true, TaskListDto::getShared)
                .returns("Display Name 1", TaskListDto::getDisplayName),
            taskList2 -> assertThat(taskList2).returns("ID2", TaskListDto::getId)
                .returns(WellknownListNameDto.DEFAULT_LIST, TaskListDto::getWellknownListName)
                .returns(false, TaskListDto::getOwner)
                .returns(false, TaskListDto::getShared)
                .returns("Display Name 2", TaskListDto::getDisplayName));
  }
}
