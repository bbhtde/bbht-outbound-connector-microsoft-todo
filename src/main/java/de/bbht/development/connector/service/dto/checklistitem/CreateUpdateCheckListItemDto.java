package de.bbht.development.connector.service.dto.checklistitem;

import java.util.Objects;

public class CreateUpdateCheckListItemDto {

  private Boolean checked;
  private String displayName;

  public CreateUpdateCheckListItemDto() {
    // empty constructor
  }

  public Boolean getChecked() {
    return checked;
  }

  public void setChecked(Boolean checked) {
    this.checked = checked;
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
    if (!(o instanceof CreateUpdateCheckListItemDto that)) {
      return false;
    }
    return Objects.equals(checked, that.checked) && Objects.equals(displayName, that.displayName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(checked, displayName);
  }

  @Override
  public String toString() {
    return "CreateUpdateCheckListItemDto{" + "checked=" + checked + ", displayName='" + displayName
        + '\'' + '}';
  }
}
