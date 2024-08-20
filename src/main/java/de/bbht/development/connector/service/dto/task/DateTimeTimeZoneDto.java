package de.bbht.development.connector.service.dto.task;

import java.util.Objects;

public class DateTimeTimeZoneDto {

  private String dateTime;
  private String timeZone;

  public DateTimeTimeZoneDto() {
    // empty constructor
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DateTimeTimeZoneDto that)) {
      return false;
    }
    return Objects.equals(dateTime, that.dateTime) && Objects.equals(timeZone, that.timeZone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dateTime, timeZone);
  }

  @Override
  public String toString() {
    return "DateTimeTimeZoneDto{" + "dateTime='" + dateTime + '\'' + ", timeZone='" + timeZone
        + '\'' + '}';
  }
}
