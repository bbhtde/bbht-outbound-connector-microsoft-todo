package de.bbht.development.connector.service.dto.task;

import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;

import java.util.Objects;
import java.util.Set;

public class RecurrencePatternDto {

  private Integer dayOfMonth;
  private Set<DayOfWeekDto> daysOfWeek;
  private DayOfWeekDto firstDayOfWeek;
  private WeekIndexDto index;
  private Integer interval;
  private Integer month;
  private RecurrencePatternTypeDto type;

  public RecurrencePatternDto() {
    // empty constructor
  }

  public Integer getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public Set<DayOfWeekDto> getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(Set<DayOfWeekDto> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  public DayOfWeekDto getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  public void setFirstDayOfWeek(DayOfWeekDto firstDayOfWeek) {
    this.firstDayOfWeek = firstDayOfWeek;
  }

  public WeekIndexDto getIndex() {
    return index;
  }

  public void setIndex(WeekIndexDto index) {
    this.index = index;
  }

  public Integer getInterval() {
    return interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public RecurrencePatternTypeDto getType() {
    return type;
  }

  public void setType(RecurrencePatternTypeDto type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RecurrencePatternDto that)) {
      return false;
    }
    return Objects.equals(dayOfMonth, that.dayOfMonth) && Objects.equals(daysOfWeek, that.daysOfWeek)
        && firstDayOfWeek == that.firstDayOfWeek && index == that.index && Objects.equals(interval, that.interval)
        && Objects.equals(month, that.month) && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayOfMonth, daysOfWeek, firstDayOfWeek, index, interval, month, type);
  }

  @Override
  public String toString() {
    return "RecurrencePatternDto{" + "dayOfMonth=" + dayOfMonth + ", daysOfWeek=" + daysOfWeek + ", firstDayOfWeek="
        + firstDayOfWeek + ", index=" + index + ", interval=" + interval + ", month=" + month + ", type=" + type + '}';
  }
}
