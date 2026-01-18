
package com.ey.dto.user;

import java.time.LocalDateTime;

public class UserDetail {
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String role; 
  private boolean enabled = true;
  private LocalDateTime createdAt;

  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
  public boolean isEnabled() { return enabled; }
  public void setEnabled(boolean enabled) { this.enabled = enabled; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
