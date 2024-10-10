package de.bbht.development.connector.service.dto.task;

import java.util.Objects;

public class PatternedRecurrenceDto {

  private RecurrencePatternDto pattern;
  private RecurrenceRangeDto range;

  public PatternedRecurrenceDto() {
    // empty constructor
  }

  public RecurrencePatternDto getPattern() {
    return pattern;
  }

  public void setPattern(RecurrencePatternDto pattern) {
    this.pattern = pattern;
  }

  public RecurrenceRangeDto getRange() {
    return range;
  }

  public void setRange(RecurrenceRangeDto range) {
    this.range = range;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PatternedRecurrenceDto that)) {
      return false;
    }
    return Objects.equals(pattern, that.pattern) && Objects.equals(range, that.range);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pattern, range);
  }

  @Override
  public String toString() {
    return "PatternedRecurrenceDto{" + "pattern=" + pattern + ", range=" + range + '}';
  }
}
