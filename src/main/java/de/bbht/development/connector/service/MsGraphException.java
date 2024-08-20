package de.bbht.development.connector.service;

public class MsGraphException extends RuntimeException {

  private final String code;

  public MsGraphException(String message) {
    super(message);
    this.code = null;
  }

  public MsGraphException(String message, String code) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
