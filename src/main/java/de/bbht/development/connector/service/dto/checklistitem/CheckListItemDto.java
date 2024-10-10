package de.bbht.development.connector.service.dto.checklistitem;

import java.time.OffsetDateTime;
import java.util.Objects;

public class CheckListItemDto {

  private String id;
  private Boolean checked;
  private String displayName;
  private OffsetDateTime checkedDateTime;
  private OffsetDateTime createdDateTime;

  public CheckListItemDto() {
    // empty constructor
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public OffsetDateTime getCheckedDateTime() {
    return checkedDateTime;
  }

  public void setCheckedDateTime(OffsetDateTime checkedDateTime) {
    this.checkedDateTime = checkedDateTime;
  }

  public OffsetDateTime getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(OffsetDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CheckListItemDto that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(checked, that.checked) && Objects.equals(
        displayName, that.displayName) && Objects.equals(checkedDateTime, that.checkedDateTime)
        && Objects.equals(createdDateTime, that.createdDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, checked, displayName, checkedDateTime, createdDateTime);
  }

  @Override
  public String toString() {
    return "CheckListItemDto{" + "id='" + id + '\'' + ", checked=" + checked + ", displayName='"
        + displayName + '\'' + ", checkedDateTime=" + checkedDateTime + ", createdDateTime="
        + createdDateTime + '}';
  }
}