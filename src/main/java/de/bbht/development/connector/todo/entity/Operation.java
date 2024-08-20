package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Operation(@NotNull() @TemplateProperty(group = "operation", label = "To Do Operation",
                                                     description = "Select one of the available Operations for Microsoft To Do",
                                                     constraints = @TemplateProperty.PropertyConstraints(
                                                         notEmpty = true), choices = {
    @TemplateProperty.DropdownPropertyChoice(value = "LIST_TASK_LISTS",
                                             label = "Get List of Task Lists"),
    @TemplateProperty.DropdownPropertyChoice(value = "GET_TASK_LIST", label = "Get Task List"),
    @TemplateProperty.DropdownPropertyChoice(value = "CREATE_TASK_LIST",
                                             label = "Create Task List"),
    @TemplateProperty.DropdownPropertyChoice(value = "UPDATE_TASK_LIST",
                                             label = "Update Task List"),
    @TemplateProperty.DropdownPropertyChoice(value = "DELETE_TASK_LIST",
                                             label = "Delete Task List"),
    @TemplateProperty.DropdownPropertyChoice(value = "LIST_TASKS", label = "Get List of Tasks"),
    @TemplateProperty.DropdownPropertyChoice(value = "GET_TASK", label = "Get Task"),
    @TemplateProperty.DropdownPropertyChoice(value = "CREATE_TASK", label = "Create Task"),
    @TemplateProperty.DropdownPropertyChoice(value = "UPDATE_TASK", label = "Update Task"),
    @TemplateProperty.DropdownPropertyChoice(value = "DELETE_TASK", label = "Delete Task"),
    @TemplateProperty.DropdownPropertyChoice(value = "LIST_CHECK_LIST_ITEMS",
                                             label = "Get List of Check List Items"),
    @TemplateProperty.DropdownPropertyChoice(value = "GET_CHECK_LIST_ITEM",
                                             label = "Get Check List Item"),
    @TemplateProperty.DropdownPropertyChoice(value = "CREATE_CHECK_LIST_ITEM",
                                             label = "Create Check List Item"),
    @TemplateProperty.DropdownPropertyChoice(value = "UPDATE_CHECK_LIST_ITEM",
                                             label = "Update Check List Item"),
    @TemplateProperty.DropdownPropertyChoice(value = "DELETE_CHECK_LIST_ITEM",
                                             label = "Delete Check List Item")}) ToDoOperation operation,

                        @NotEmpty @TemplateProperty(group = "operation", label = "User ID",
                                                    description = "The User ID (or Principal Name) of the User whose Task List the operation is run for",
                                                    constraints = @TemplateProperty.PropertyConstraints(
                                                        notEmpty = true)) String userIdOrPrincipalName,

                        @TemplateProperty(group = "operation", label = "Task List ID",
                                          description = "The Task List ID for which this operation is run",
                                          constraints = @TemplateProperty.PropertyConstraints(
                                              notEmpty = true),
                                          condition = @TemplateProperty.PropertyCondition(
                                              property = "operation.operation",
                                              oneOf = {"GET_TASK_LIST", "UPDATE_TASK_LIST",
                                                  "DELETE_TASK_LIST", "LIST_TASKS", "GET_TASK",
                                                  "CREATE_TASK", "UPDATE_TASK", "DELETE_TASK",
                                                  "LIST_CHECK_LIST_ITEMS", "GET_CHECK_LIST_ITEM",
                                                  "CREATE_CHECK_LIST_ITEM",
                                                  "UPDATE_CHECK_LIST_ITEM",
                                                  "DELETE_CHECK_LIST_ITEM"})) String taskListId,

                        @TemplateProperty(group = "operation", label = "Task ID",
                                          description = "The Task ID for which this operation is run",
                                          constraints = @TemplateProperty.PropertyConstraints(
                                              notEmpty = true),
                                          condition = @TemplateProperty.PropertyCondition(
                                              property = "operation.operation",
                                              oneOf = {"GET_TASK", "UPDATE_TASK", "DELETE_TASK",
                                                  "LIST_CHECK_LIST_ITEMS", "GET_CHECK_LIST_ITEM",
                                                  "CREATE_CHECK_LIST_ITEM",
                                                  "UPDATE_CHECK_LIST_ITEM",
                                                  "DELETE_CHECK_LIST_ITEM"})) String taskId,

                        @TemplateProperty(group = "operation", label = "Check List Item ID",
                                          description = "The Check List Item ID for which this operation is run",
                                          constraints = @TemplateProperty.PropertyConstraints(
                                              notEmpty = true),
                                          condition = @TemplateProperty.PropertyCondition(
                                              property = "operation.operation",
                                              oneOf = {"GET_CHECK_LIST_ITEM",
                                                  "UPDATE_CHECK_LIST_ITEM",
                                                  "DELETE_CHECK_LIST_ITEM"})) String checkListItemId) {

}