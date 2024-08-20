package de.bbht.development.connector.service.dto.tasklist;

import java.util.Objects;

public final class CreateUpdateTaskListDto {

  private String displayName;

  public CreateUpdateTaskListDto() {
    // leerer Construktor
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CreateUpdateTaskListDto that)) {
      return false;
    }
    return Objects.equals(displayName, that.displayName);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(displayName);
  }

  @Override
  public String toString() {
    return "CreateUpdateTaskListDto{" + "displayName='" + displayName + '\'' + '}';
  }
}
