package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;

public record CheckListItemOptions(@TemplateProperty(group = "checklistitem",
                                                     label = "Display Name",
                                                     description = "The displayed name of the Check List Item",
                                                     constraints = @TemplateProperty.PropertyConstraints(
                                                         notEmpty = true),
                                                     condition = @TemplateProperty.PropertyCondition(
                                                         property = "operation.operation",
                                                         oneOf = {"CREATE_CHECK_LIST_ITEM"})) String displayName,

                                   @TemplateProperty(group = "checklistitem", label = "Checked",
                                                     description = "Is the item currently checked or not?",
                                                     constraints = @TemplateProperty.PropertyConstraints(
                                                         notEmpty = true),
                                                     condition = @TemplateProperty.PropertyCondition(
                                                         property = "operation.operation",
                                                         oneOf = {"CREATE_CHECK_LIST_ITEM" }),
                                                     defaultValue = "true") Boolean checked) {

}
