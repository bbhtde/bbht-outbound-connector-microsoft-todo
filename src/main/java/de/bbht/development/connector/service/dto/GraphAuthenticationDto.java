package de.bbht.development.connector.service.dto;

import java.util.Objects;

public record GraphAuthenticationDto(String tenantId,
                                     String clientId,
                                     String clientSecret) {

  private static final String NULL = "null";
  private static final String SECRET = "<secret>";

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GraphAuthenticationDto that)) {
      return false;
    }
    return Objects.equals(tenantId, that.tenantId) && Objects.equals(clientId, that.clientId) && Objects.equals(
        clientSecret, that.clientSecret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantId, clientId, clientSecret);
  }

  @Override
  public String toString() {
    return "GraphAuthInfo{" + "tenantId=" + (tenantId() == null ? NULL : SECRET) + ", clientId=" + (
        clientId() == null ? NULL : SECRET) + ", clientSecret=" + (clientSecret() == null ? NULL
        : SECRET) + '}';
  }
}
