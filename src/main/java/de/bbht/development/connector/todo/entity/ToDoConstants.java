package de.bbht.development.connector.todo.entity;

class ToDoConstants {

  public static final String REGEX_DATETIME = "^([1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]T[0-2][0-9]:[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9][0-9][0-9][0-9])?$";

  public static final String REGEX_32_BIT_INTEGER = "^[1-9]\\d{0,9}$";

  public static final String REGEX_MONTHS = "^(1[0-2]|[1-9])$";

  public static final String REGEX_DAYS = "^([1-9]|1[0-9]|2[0-9]|3[01])$";

  public static final String REGEX_WEEKDAYS = "^([mM]onday|[tT]uesday|[wW]ednesday|[tT]hursday|[fF]riday|[sS]aturday|[sS]unday)(\\s*,\\s*(?<!\\1)([mM]onday|[tT]uesday|[wW]ednesday|[tT]hursday|[fF]riday|[sS]aturday|[sS]unday))*$";

  private ToDoConstants() {
    // prevent instantiation
  }
}
