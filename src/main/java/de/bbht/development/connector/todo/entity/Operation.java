package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.DropdownPropertyChoice;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyCondition;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyConstraints;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Operation(@NotNull() @TemplateProperty(group = "operation",
                                                     label = "To Do Operation",
                                                     description = "Select one of the available Operations for Microsoft To Do",
                                                     constraints = @PropertyConstraints(notEmpty = true),
                                                     choices = {
                                                         @DropdownPropertyChoice(value = "LIST_TASK_LISTS",
                                                                                 label = "Get List of Task Lists"),
                                                         @DropdownPropertyChoice(value = "GET_TASK_LIST",
                                                                                 label = "Get Task List"),
                                                         @DropdownPropertyChoice(value = "CREATE_TASK_LIST",
                                                                                 label = "Create Task List"),
                                                         @DropdownPropertyChoice(value = "UPDATE_TASK_LIST",
                                                                                 label = "Update Task List"),
                                                         @DropdownPropertyChoice(value = "DELETE_TASK_LIST",
                                                                                 label = "Delete Task List"),
                                                         @DropdownPropertyChoice(value = "LIST_TASKS",
                                                                                 label = "Get List of Tasks"),
                                                         @DropdownPropertyChoice(value = "GET_TASK",
                                                                                 label = "Get Task"),
                                                         @DropdownPropertyChoice(value = "CREATE_TASK",
                                                                                 label = "Create Task"),
                                                         @DropdownPropertyChoice(value = "UPDATE_TASK",
                                                                                 label = "Update Task"),
                                                         @DropdownPropertyChoice(value = "DELETE_TASK",
                                                                                 label = "Delete Task"),
                                                         @DropdownPropertyChoice(value = "LIST_CHECK_LIST_ITEMS",
                                                                                 label = "Get List of Check List Items"),
                                                         @DropdownPropertyChoice(value = "GET_CHECK_LIST_ITEM",
                                                                                 label = "Get Check List Item"),
                                                         @DropdownPropertyChoice(value = "CREATE_CHECK_LIST_ITEM",
                                                                                 label = "Create Check List Item"),
                                                         @DropdownPropertyChoice(value = "UPDATE_CHECK_LIST_ITEM",
                                                                                 label = "Update Check List Item"),
                                                         @DropdownPropertyChoice(value = "DELETE_CHECK_LIST_ITEM",
                                                                                 label = "Delete Check List Item")}) ToDoOperation operation,

                        @NotEmpty @TemplateProperty(group = "operation",
                                                    label = "User ID",
                                                    description = "The User ID (or Principal Name) of the User whose Task List the operation is run for",
                                                    constraints = @PropertyConstraints(notEmpty = true)) String userIdOrPrincipalName,

                        @TemplateProperty(group = "operation",
                                          label = "Task List ID",
                                          description = "The Task List ID for which this operation is run",
                                          constraints = @PropertyConstraints(notEmpty = true),
                                          condition = @PropertyCondition(property = "operation.operation",
                                                                         oneOf = {"GET_TASK_LIST",
                                                                             "UPDATE_TASK_LIST",
                                                                             "DELETE_TASK_LIST",
                                                                             "LIST_TASKS",
                                                                             "GET_TASK",
                                                                             "CREATE_TASK",
                                                                             "UPDATE_TASK",
                                                                             "DELETE_TASK",
                                                                             "LIST_CHECK_LIST_ITEMS",
                                                                             "GET_CHECK_LIST_ITEM",
                                                                             "CREATE_CHECK_LIST_ITEM",
                                                                             "UPDATE_CHECK_LIST_ITEM",
                                                                             "DELETE_CHECK_LIST_ITEM"})) String taskListId,

                        @TemplateProperty(group = "operation",
                                          label = "Task ID",
                                          description = "The Task ID for which this operation is run",
                                          constraints = @PropertyConstraints(notEmpty = true),
                                          condition = @PropertyCondition(property = "operation.operation",
                                                                         oneOf = {"GET_TASK",
                                                                             "UPDATE_TASK",
                                                                             "DELETE_TASK",
                                                                             "LIST_CHECK_LIST_ITEMS",
                                                                             "GET_CHECK_LIST_ITEM",
                                                                             "CREATE_CHECK_LIST_ITEM",
                                                                             "UPDATE_CHECK_LIST_ITEM",
                                                                             "DELETE_CHECK_LIST_ITEM"})) String taskId,

                        @TemplateProperty(group = "operation",
                                          label = "Check List Item ID",
                                          description = "The Check List Item ID for which this operation is run",
                                          constraints = @PropertyConstraints(notEmpty = true),
                                          condition = @PropertyCondition(property = "operation.operation",
                                                                         oneOf = {
                                                                             "GET_CHECK_LIST_ITEM",
                                                                             "UPDATE_CHECK_LIST_ITEM",
                                                                             "DELETE_CHECK_LIST_ITEM"})) String checkListItemId) {

}