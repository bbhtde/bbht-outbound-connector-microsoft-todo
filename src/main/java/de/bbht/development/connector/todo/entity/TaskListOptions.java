package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;

public record TaskListOptions(@TemplateProperty(group = "tasklist", label = "Display Name",
                                                description = "The Name displayed for the Task List",
                                                constraints = @TemplateProperty.PropertyConstraints(
                                                    notEmpty = true),
                                                condition = @TemplateProperty.PropertyCondition(
                                                    property = "operation.operation",
                                                    oneOf = {"CREATE_TASK_LIST"
                                                        })) String displayName) {

}
