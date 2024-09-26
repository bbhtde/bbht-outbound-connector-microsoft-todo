package de.bbht.development.connector.service.dto.tasklist;

import de.bbht.development.connector.service.dto.enums.WellknownListNameDto;

import java.util.Objects;

public final class TaskListDto {

  private String id;
  private String displayName;
  private Boolean owner;
  private Boolean shared;
  private WellknownListNameDto wellknownListName;

  public TaskListDto() {
    // leerer Construktor
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Boolean getOwner() {
    return owner;
  }

  public void setOwner(Boolean owner) {
    this.owner = owner;
  }

  public Boolean getShared() {
    return shared;
  }

  public void setShared(Boolean shared) {
    this.shared = shared;
  }

  public WellknownListNameDto getWellknownListName() {
    return wellknownListName;
  }

  public void setWellknownListName(WellknownListNameDto wellknownListName) {
    this.wellknownListName = wellknownListName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TaskListDto that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(displayName, that.displayName) && Objects.equals(owner,
        that.owner) && Objects.equals(shared, that.shared) && wellknownListName == that.wellknownListName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, displayName, owner, shared, wellknownListName);
  }

  @Override
  public String toString() {
    return "TaskListDto{" + "id='" + id + '\'' + ", displayName='" + displayName + '\'' + ", owner="
        + owner + ", shared=" + shared + ", wellknownListName=" + wellknownListName + '}';
  }
}
