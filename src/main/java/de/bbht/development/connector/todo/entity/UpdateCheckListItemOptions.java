package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;

public record UpdateCheckListItemOptions(@TemplateProperty(group = "checklistitem",
                                                     label = "Display Name",
                                                     description = "The displayed name of the Check List Item",
                                                     optional = true,
                                                     condition = @TemplateProperty.PropertyCondition(
                                                         property = "operation.operation",
                                                         oneOf = {"UPDATE_CHECK_LIST_ITEM"})) String displayName,

                                         @TemplateProperty(group = "checklistitem", label = "Checked",
                                                     description = "Is the item currently checked or not?",
                                                     optional = true,
                                                     condition = @TemplateProperty.PropertyCondition(
                                                         property = "operation.operation",
                                                         oneOf = {"UPDATE_CHECK_LIST_ITEM"}),
                                                     defaultValue = "true") Boolean checked) {
}
