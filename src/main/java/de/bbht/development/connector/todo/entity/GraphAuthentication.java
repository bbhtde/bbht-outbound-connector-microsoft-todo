package de.bbht.development.connector.todo.entity;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;

public record GraphAuthentication(@NotEmpty @TemplateProperty(group = "authentication",
                                                              label = "Tenant ID",
                                                              description = "Your Microsoft Tenant ID",
                                                              defaultValue = "{{secrets.TENANT_ID}}"
                                                              // Mark as secret
                                                              ) String tenantId,
                                  @NotEmpty @TemplateProperty(group = "authentication",
                                                              label = "Client ID",
                                                              description = "Your Microsoft Client ID",
                                                              defaultValue = "{{secrets.CLIENT_ID}}"
                                                              // Mark as secret
                                                              ) String clientId,

                                  @NotEmpty @TemplateProperty(group = "authentication",
                                                              label = "Client Secret",
                                                              description = "Your Microsoft Client Secret",
                                                              defaultValue = "{{secrets.CLIENT_SECRET}}"
                                                              // Mark as secret
                                                              ) String clientSecret) {

}
