
package com.ey.service;

import com.ey.domain.User;
import com.ey.domain.UserPreferences;
import com.ey.dto.UserDto;
import com.ey.dto.common.UpdateAck;
import com.ey.dto.user.*;
import com.ey.repo.UserRepo;
import com.ey.security.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class UserService {

  private final UserRepo userRepo;
  private final CurrentUser currentUser;
  private final PasswordEncoder encoder;

  public UserService(UserRepo userRepo, CurrentUser currentUser, PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.currentUser = currentUser;
    this.encoder = encoder;
  }

  public UserDto me() {
    String email = currentUser.email();
    if (email == null) throw new RuntimeException("Unauthorized");
    User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    return UserDto.from(u);
  }

  public void updateMe(UserUpdateRequest r) {
    String email = currentUser.email();
    if (email == null) throw new RuntimeException("Unauthorized");
    User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    if (r.getFirstName() != null) u.setFirstName(r.getFirstName());
    if (r.getLastName() != null)  u.setLastName(r.getLastName());
    if (r.getPhone() != null)     u.setPhone(r.getPhone());
    userRepo.save(u);
  }

  public UpdateAck updatePhone(PhoneUpdate r) {
    String email = currentUser.email();
    if (email == null) throw new RuntimeException("Unauthorized");
    User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    u.setPhone(r.getNewPhoneNumber());
    userRepo.save(u);
    return new UpdateAck("Phone updated", "CUSTOMER", OffsetDateTime.now());
  }

  public UpdateAck updateEmail(EmailUpdate r) {
    String email = currentUser.email();
    if (email == null) throw new RuntimeException("Unauthorized");
    User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    userRepo.findByEmail(r.getNewEmail()).ifPresent(x -> { throw new RuntimeException("Email already exists"); });

    u.setEmail(r.getNewEmail());
    userRepo.save(u);
    return new UpdateAck("Email updated", "CUSTOMER", OffsetDateTime.now());
  }

  public UpdateAck updatePassword(PasswordUpdate r) {
    String email = currentUser.email();
    if (email == null) throw new RuntimeException("Unauthorized");
    User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    if (r.getOldPassword() == null || r.getNewPassword() == null) throw new RuntimeException("Invalid payload");
    if (!encoder.matches(r.getOldPassword(), u.getPasswordHash())) throw new RuntimeException("Invalid old password");

    u.setPasswordHash(encoder.encode(r.getNewPassword()));
    userRepo.save(u);
    return new UpdateAck("Password updated", "CUSTOMER", OffsetDateTime.now());
  }

  public UserDetail getUser(Long userId) {
    User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    UserDetail d = new UserDetail();
    d.setUserId(u.getId());
    d.setFirstName(u.getFirstName());
    d.setLastName(u.getLastName());
    d.setEmail(u.getEmail());
    d.setPhoneNumber(u.getPhone());
    d.setRole(u.getRoles() != null && !u.getRoles().isEmpty() ? u.getRoles().iterator().next().name() : "GUEST");
    d.setCreatedAt(u.getCreatedAt() != null ? u.getCreatedAt() : LocalDateTime.now());
    return d;
  }

  public UserActivityDto activity(Long userId) {
    UserActivityDto dto = new UserActivityDto();
    dto.setUserId(userId);
    dto.getEvents().add(new UserActivityDto.Event("BOOKING_CREATED", 9001L, OffsetDateTime.now().minusDays(1)));
    dto.getEvents().add(new UserActivityDto.Event("PAYMENT_SUCCESS", 77701L, OffsetDateTime.now().minusDays(1)));
    return dto;
  }


  public void updatePreferences(Long userId, PreferencesDto r) {
    User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    UserPreferences p = u.getPreferences() != null ? u.getPreferences() : new UserPreferences();
    if (r.getBedType() != null)        p.setBedType(r.getBedType());
    if (r.getSmoking() != null)        p.setSmoking(r.getSmoking());
    if (r.getLateCheckout() != null)   p.setLateCheckout(r.getLateCheckout());
    if (r.getFloorPreference() != null)p.setFloorPreference(r.getFloorPreference());
    u.setPreferences(p);
    userRepo.save(u);
  }

 
  public PreferencesDto getPreferences(Long userId) {
    User u = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    UserPreferences p = u.getPreferences();
    PreferencesDto dto = new PreferencesDto();
    if (p != null) {
      dto.setBedType(p.getBedType());
      dto.setSmoking(p.getSmoking());
      dto.setLateCheckout(p.getLateCheckout());
      dto.setFloorPreference(p.getFloorPreference());
    }
    return dto;
  }
}
