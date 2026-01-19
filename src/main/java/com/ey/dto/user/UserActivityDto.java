package com.ey.dto.user;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserActivityDto {
  public static class Event {
    private String type;
    private Long id;
    private OffsetDateTime at;

    public Event() {}
    public Event(String type, Long id, OffsetDateTime at) {
      this.type = type; this.id = id; this.at = at;
    }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public OffsetDateTime getAt() { return at; }
    public void setAt(OffsetDateTime at) { this.at = at; }
  }

  private Long userId;
  private List<Event> events = new ArrayList<>();

  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public List<Event> getEvents() { return events; }
  public void setEvents(List<Event> events) { this.events = events; }
}
