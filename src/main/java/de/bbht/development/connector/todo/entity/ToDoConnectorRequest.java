package de.bbht.development.connector.todo.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ToDoConnectorRequest(
        @Valid @NotNull GraphAuthentication authentication,
        @Valid @NotNull Operation operation,
        @Valid TaskListOptions taskListOptions,
        @Valid UpdateTaskListOptions updateTaskListOptions,
        @Valid TaskOptions taskOptions,
        @Valid UpdateTaskOptions updateTaskOptions,
        @Valid TaskRecurrenceOptions taskRecurrenceOptions,
        @Valid CheckListItemOptions checkListItemOptions,
        @Valid UpdateCheckListItemOptions updateCheckListItemOptions) {}
