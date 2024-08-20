package de.bbht.development.connector.service;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import de.bbht.development.connector.service.dto.GraphAuthenticationDto;

public class MsGraphServiceFactory implements IMsGraphServiceFactory {

  private static final String[] DEFAULT_SCOPE_LIST = new String[] {
      "https://graph.microsoft.com/.default"};

  @Override
  public MsGraphService create(GraphAuthenticationDto authInfo) {
    if (authInfo == null || authInfo.tenantId() == null || authInfo.clientId() == null
        || authInfo.clientSecret() == null) {
      throw new IllegalArgumentException("Graph Authentication Information is incomplete");
    }

    TokenCredential tokenCredential = new ClientSecretCredentialBuilder().tenantId(
            authInfo.tenantId())
        .clientId(authInfo.clientId())
        .clientSecret(authInfo.clientSecret())
        .build();

    return new MsGraphService(new GraphServiceClient(tokenCredential, DEFAULT_SCOPE_LIST));
  }
}
