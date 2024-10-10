package de.bbht.development.connector.todo.entity;

public class ConnectorResult<T> {

  private T result;
  private boolean empty;
  private ConnectorError error;

  public static <T> ConnectorResult<T> withGivenResult(T result) {
    ConnectorResult<T> connectorResult = new ConnectorResult<>();
    connectorResult.setResult(result);
    connectorResult.setEmpty(false);
    connectorResult.setError(null);
    return connectorResult;
  }

  public static <T> ConnectorResult<T> withEmptyResult() {
    ConnectorResult<T> connectorResult = new ConnectorResult<>();
    connectorResult.setResult(null);
    connectorResult.setEmpty(true);
    connectorResult.setError(null);
    return connectorResult;
  }

  public static <T> ConnectorResult<T> withErrorResult(String errorMessage, String errorCode) {
    ConnectorResult<T> connectorResult = new ConnectorResult<>();
    connectorResult.setResult(null);
    connectorResult.setEmpty(true);
    ConnectorError connectorError = new ConnectorError(errorMessage, errorCode);
    connectorResult.setError(connectorError);
    return connectorResult;
  }

  public ConnectorResult() {
    // empty constructor
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public boolean isEmpty() {
    return empty;
  }

  public void setEmpty(boolean empty) {
    this.empty = empty;
  }

  public ConnectorError getError() {
    return error;
  }

  public void setError(ConnectorError error) {
    this.error = error;
  }
}
