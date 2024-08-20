package de.bbht.development.connector.service;

import de.bbht.development.connector.service.dto.GraphAuthenticationDto;

public interface IMsGraphServiceFactory {

  MsGraphService create(GraphAuthenticationDto authenticationInfo);
}
