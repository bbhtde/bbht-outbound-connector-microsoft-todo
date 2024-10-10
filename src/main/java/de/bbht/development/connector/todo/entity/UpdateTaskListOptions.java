package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyCondition;

public record UpdateTaskListOptions(@TemplateProperty(group = "tasklist",
    label = "Display Name",
    description = "The Name displayed for the Task List",
    condition = @PropertyCondition(property = "operation.operation",
        oneOf = {"UPDATE_TASK_LIST"})) String displayName) {

}
