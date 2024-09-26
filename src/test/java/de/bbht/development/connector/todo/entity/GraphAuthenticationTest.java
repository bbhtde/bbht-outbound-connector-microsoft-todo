package de.bbht.development.connector.todo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.connector.api.error.ConnectorInputException;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GraphAuthenticationTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReplaceTokens() throws Exception {
    // Given
    var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
        "{{secrets.CLIENT_SECRET}}");

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    var variables = context.bindVariables(GraphAuthentication.class);

    // then
    Assertions.assertThat(variables)
        .extracting("tenantId")
        .isEqualTo("secretTenantId");
    Assertions.assertThat(variables)
        .extracting("clientId")
        .isEqualTo("secretClientId");
    Assertions.assertThat(variables)
        .extracting("clientSecret")
        .isEqualTo("secretClientSecret");
  }

  @Test
  void shouldThrowExceptionOnNullTenantId() throws Exception {
    // Given
    var authentication = new GraphAuthentication(null, "{{secrets.CLIENT_ID}}",
        "{{secrets.CLIENT_SECRET}}");

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    assertThatThrownBy(() -> context.bindVariables(GraphAuthentication.class))
        // then
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("tenantId: Validation failed.");

  }

  @Test
  void shouldThrowExceptionOnEmptyTenantId() throws Exception {
    // Given
    var authentication = new GraphAuthentication("", "{{secrets.CLIENT_ID}}",
        "{{secrets.CLIENT_SECRET}}");

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    assertThatThrownBy(() -> context.bindVariables(GraphAuthentication.class))
        // then
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("tenantId: Validation failed.");

  }

  @Test
  void shouldThrowExceptionOnNullClientId() throws Exception {
    // Given
    var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", null,
        "{{secrets.CLIENT_SECRET}}");

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    assertThatThrownBy(() -> context.bindVariables(GraphAuthentication.class))
        // then
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("clientId: Validation failed.");

  }

  @Test
  void shouldThrowExceptionOnEmptyClientId() throws Exception {
    // Given
    var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "",
        "{{secrets.CLIENT_SECRET}}");

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    assertThatThrownBy(() -> context.bindVariables(GraphAuthentication.class))
        // then
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("clientId: Validation failed.");

  }

  @Test
  void shouldThrowExceptionOnNullClientSecret() throws Exception {
    // Given
    var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
        null);

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    assertThatThrownBy(() -> context.bindVariables(GraphAuthentication.class))
        // then
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("clientSecret: Validation failed.");

  }

  @Test
  void shouldThrowExceptionOnEmptyClientSecret() throws Exception {
    // Given
    var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
        "");

    var context = OutboundConnectorContextBuilder.create()
        .secret("TENANT_ID", "secretTenantId")
        .secret("CLIENT_ID", "secretClientId")
        .secret("CLIENT_SECRET", "secretClientSecret")
        .variables(objectMapper.writeValueAsString(authentication))
        .build();

    // when
    assertThatThrownBy(() -> context.bindVariables(GraphAuthentication.class))
        // then
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("clientSecret: Validation failed.");

  }
}
