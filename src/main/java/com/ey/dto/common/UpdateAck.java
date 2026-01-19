package com.ey.dto.common;

import java.time.OffsetDateTime;

public class UpdateAck {
  private String message;
  private String updatedBy;
  private OffsetDateTime updatedAt;

  public UpdateAck() {}
  public UpdateAck(String message, String updatedBy, OffsetDateTime updatedAt) {
    this.message = message; this.updatedBy = updatedBy; this.updatedAt = updatedAt;
  }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
  public String getUpdatedBy() { return updatedBy; }
  public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
