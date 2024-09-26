package de.bbht.development.connector.service.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GraphAuthenticationDtoTest {

  @Test
  void shouldEnsureHashCodeContractOnEqualObjects() {
    var auth1 = new GraphAuthenticationDto("tenant1", "client1", "secret1");
    var auth2 = new GraphAuthenticationDto("tenant1", "client1", "secret1");

    var hashCode1 = auth1.hashCode();
    var hashCode2 = auth2.hashCode();

    assertThat(hashCode1).isEqualTo(hashCode2);
  }

  @Test
  void shouldEnsureHashCodeContractOnDifferentObjects() {
    var auth1 = new GraphAuthenticationDto("tenant1", "client1", "secret1");
    var auth2 = new GraphAuthenticationDto("tenant2", "client1", "secret1");
    var auth3 = new GraphAuthenticationDto("tenant1", "client2", "secret1");
    var auth4 = new GraphAuthenticationDto("tenant1", "client1", "secret2");

    var hashCode1 = auth1.hashCode();
    var hashCode2 = auth2.hashCode();
    var hashCode3 = auth3.hashCode();
    var hashCode4 = auth4.hashCode();

    assertThat(hashCode1).isNotEqualTo(hashCode2)
        .isNotEqualTo(hashCode3)
        .isNotEqualTo(hashCode4);
  }

  @Test
  void shouldEnsureEqualsContractOnEqualObjects() {
    var auth1 = new GraphAuthenticationDto("tenant1", "client1", "secret1");
    var auth2 = new GraphAuthenticationDto("tenant1", "client1", "secret1");

    var equals = auth1.equals(auth2);

    assertThat(equals).isTrue();
  }

  @Test
  void shouldEnsureEqualsContractOnDifferentObjectClasses() {
    var auth1 = new GraphAuthenticationDto("tenant1", "client1", "secret1");
    var auth2 = "I am definitely not a GraphAuthInfo instance";

    var equals = auth1.equals(auth2);

    assertThat(equals).isFalse();
  }

  @Test
  void shouldEnsureEqualsContractOnNullComparator() {
    var auth1 = new GraphAuthenticationDto("tenant1", "client1", "secret1");
    var auth2 = (GraphAuthenticationDto) null;

    var equals = auth1.equals(auth2);

    assertThat(equals).isFalse();
  }

  @Test
  void shouldEnsureEqualsContractOnDifferentInstancesOfSameClass() {
    var auth1 = new GraphAuthenticationDto("tenant1", "client1", "secret1");
    var auth2 = new GraphAuthenticationDto("tenant2", "client2", "secret2");

    var equals = auth1.equals(auth2);

    assertThat(equals).isFalse();
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForToStringTests")
  void shouldEnsureToStringHidesSecrets(String tenantId, String clientId, String secret,
      String expectedTenantIdText, String expectedClientIdText, String expectedClientSecretText) {
    var auth = new GraphAuthenticationDto(tenantId, clientId, secret);

    var result = auth.toString();

    assertThat(result).isEqualTo(
        String.format("GraphAuthInfo{tenantId=%1$s, clientId=%2$s, clientSecret=%3$s}",
            expectedTenantIdText, expectedClientIdText, expectedClientSecretText));
  }

  private static Stream<Arguments> provideArgumentsForToStringTests() {
    return Stream.of(
        Arguments.of("tenant1", "client1", "secret1", "<secret>", "<secret>", "<secret>"),
        Arguments.of(null, null, null, "null", "null", "null"));
  }
}
