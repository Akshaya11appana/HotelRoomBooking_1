package com.ey.dto;

import com.ey.domain.Role;
import com.ey.domain.User;
import java.time.LocalDateTime;
import java.util.Set;

public final class UserDto {
  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final Set<Role> roles;
  private final LocalDateTime createdAt;

  public UserDto(Long id, String firstName, String lastName, String email,
                 Set<Role> roles, LocalDateTime createdAt) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.roles = roles;
    this.createdAt = createdAt;
  }

  public Long getId() { return id; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getEmail() { return email; }
  public Set<Role> getRoles() { return roles; }
  public LocalDateTime getCreatedAt() { return createdAt; }

  public static UserDto from(User u){
    return new UserDto(
        u.getId(),
        u.getFirstName(),
        u.getLastName(),
        u.getEmail(),
        u.getRoles(),
        u.getCreatedAt()
    );
  }
}
