package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyCondition;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyConstraints;

public record TaskListOptions(
        @TemplateProperty(
                group = "tasklist",
                label = "Display Name",
                description = "The Name displayed for the Task List",
                constraints = @PropertyConstraints(notEmpty = true),
                condition = @PropertyCondition(
                    property = "operation.operation",
                    oneOf = {"CREATE_TASK_LIST"
                        }))
        String displayName) {
}
