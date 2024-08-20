package de.bbht.development.connector.todo.entity;

public class ConnectorError {

  private final String errorMessage;
  private final String errorCode;

  public ConnectorError(String errorMessage, String errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
