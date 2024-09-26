package de.bbht.development.connector.service.dto.task;

import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;

import java.time.LocalDate;
import java.util.Objects;

public class RecurrenceRangeDto {

  private LocalDate endDate;
  private Integer numberOfOccurrences;
  private String recurrenceTimeZone;
  private LocalDate startDate;
  private RecurrenceRangeTypeDto type;

  public RecurrenceRangeDto() {
    // empty constructor
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Integer getNumberOfOccurrences() {
    return numberOfOccurrences;
  }

  public void setNumberOfOccurrences(Integer numberOfOccurrences) {
    this.numberOfOccurrences = numberOfOccurrences;
  }

  public String getRecurrenceTimeZone() {
    return recurrenceTimeZone;
  }

  public void setRecurrenceTimeZone(String recurrenceTimeZone) {
    this.recurrenceTimeZone = recurrenceTimeZone;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public RecurrenceRangeTypeDto getType() {
    return type;
  }

  public void setType(RecurrenceRangeTypeDto type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RecurrenceRangeDto that)) {
      return false;
    }
    return Objects.equals(endDate, that.endDate) && Objects.equals(numberOfOccurrences, that.numberOfOccurrences)
        && Objects.equals(recurrenceTimeZone, that.recurrenceTimeZone) && Objects.equals(startDate, that.startDate)
        && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(endDate, numberOfOccurrences, recurrenceTimeZone, startDate, type);
  }

  @Override
  public String toString() {
    return "RecurrenceRangeDto{" + "endDate=" + endDate + ", numberOfOccurrences=" + numberOfOccurrences
        + ", recurrenceTimeZone='" + recurrenceTimeZone + '\'' + ", startDate=" + startDate + ", type=" + type + '}';
  }
}
