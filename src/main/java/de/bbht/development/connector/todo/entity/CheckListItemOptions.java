package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyCondition;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyConstraints;

public record CheckListItemOptions(@TemplateProperty(group = "checklistitem",
    label = "Display Name",
    description = "The displayed name of the Check List Item",
    constraints = @PropertyConstraints(notEmpty = true),
    condition = @PropertyCondition(property = "operation.operation",
        oneOf = {"CREATE_CHECK_LIST_ITEM"})) String displayName,

    @TemplateProperty(group = "checklistitem",
        label = "Checked",
        description = "Is the item currently checked or not?",
        constraints = @PropertyConstraints(notEmpty = true),
        condition = @PropertyCondition(property = "operation.operation",
            oneOf = {"CREATE_CHECK_LIST_ITEM"}),
        defaultValue = "true") Boolean checked) {

}
