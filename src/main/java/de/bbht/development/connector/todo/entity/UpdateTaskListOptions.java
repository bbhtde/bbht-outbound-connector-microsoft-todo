package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;

public record UpdateTaskListOptions(@TemplateProperty(group = "tasklist", label = "Display Name",
                                                description = "The Name displayed for the Task List",
                                                condition = @TemplateProperty.PropertyCondition(
                                                    property = "operation.operation",
                                                    oneOf = {
                                                        "UPDATE_TASK_LIST"})) String displayName) {

}
