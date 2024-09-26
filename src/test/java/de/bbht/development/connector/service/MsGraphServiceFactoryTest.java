package de.bbht.development.connector.service;

import de.bbht.development.connector.service.dto.GraphAuthenticationDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MsGraphServiceFactoryTest {

  @Test
  void shouldCreateMsGraphService() {
    // Given
    var factoryUnderTest = new MsGraphServiceFactory();
    var authInfo = new GraphAuthenticationDto("tenantId", "clientId", "clientSecret");

    // When
    var service = factoryUnderTest.create(authInfo);

    // Then
    assertThat(service).isNotNull();
  }

  @Test
  void shouldNotCreateMsGraphServiceWithMissingAuthenticationInfo() {
    // Given
    var factoryUnderTest = new MsGraphServiceFactory();
    var authInfo = (GraphAuthenticationDto) null;

    // When
    assertThatThrownBy(() -> factoryUnderTest.create(authInfo))
        // then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Graph Authentication Information is incomplete");
  }

  @Test
  void shouldNotCreateMsGraphServiceWithMissingTenantId() {
    // Given
    var factoryUnderTest = new MsGraphServiceFactory();
    var authInfo = new GraphAuthenticationDto(null, "clientId", "clientSecret");

    // When
    assertThatThrownBy(() -> factoryUnderTest.create(authInfo))
        // then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Graph Authentication Information is incomplete");
  }

  @Test
  void shouldNotCreateMsGraphServiceWithMissingClientId() {
    // Given
    var factoryUnderTest = new MsGraphServiceFactory();
    var authInfo = new GraphAuthenticationDto("tenantId", null, "clientSecret");

    // When
    assertThatThrownBy(() -> factoryUnderTest.create(authInfo))
        // then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Graph Authentication Information is incomplete");
  }

  @Test
  void shouldNotCreateMsGraphServiceWithMissingClientSecret() {
    // Given
    var factoryUnderTest = new MsGraphServiceFactory();
    var authInfo = new GraphAuthenticationDto("tenantId", "clientId", null);

    // When
    assertThatThrownBy(() -> factoryUnderTest.create(authInfo))
        // then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Graph Authentication Information is incomplete");
  }
}
