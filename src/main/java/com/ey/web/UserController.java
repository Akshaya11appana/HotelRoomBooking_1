
package com.ey.web;

import com.ey.dto.UserDto;
import com.ey.dto.common.UpdateAck;
import com.ey.dto.user.*;
import com.ey.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // B(5): GET /users/me
  @GetMapping("/me")
  public UserDto me() { return userService.me(); }

  // B(6): PUT /users/me
  @PutMapping("/me")
  public Map<String, String> update(@RequestBody UserUpdateRequest r) {
    userService.updateMe(r);
    return Map.of("message", "Profile updated");
  }

  // C(8): PUT /users/update-phone
  @PutMapping("/update-phone")
  public UpdateAck updatePhone(@RequestBody PhoneUpdate r) {
    return userService.updatePhone(r);
  }

  // C(9): PUT /users/update-email
  @PutMapping("/update-email")
  public UpdateAck updateEmail(@RequestBody EmailUpdate r) {
    return userService.updateEmail(r);
  }

  // C(10): PUT /users/update-password
  @PutMapping("/update-password")
  public UpdateAck updatePwd(@RequestBody PasswordUpdate r) {
    return userService.updatePassword(r);
  }

  // C(11): GET /users/{userId}
  @GetMapping("/{userId}")
  public UserDetail getUser(@PathVariable Long userId) {
    return userService.getUser(userId);
  }

  // C(12): GET /users/{userId}/activity
  @GetMapping("/{userId}/activity")
  public UserActivityDto activity(@PathVariable Long userId) {
    return userService.activity(userId);
  }

  // C(13): PUT /users/{userId}/preferences
  @PutMapping("/{userId}/preferences")
  public Map<String, String> updatePrefs(@PathVariable Long userId, @RequestBody PreferencesDto r) {
    userService.updatePreferences(userId, r);
    return Map.of("message", "Preferences updated");
  }

  // C(15): GET /users/{userId}/preferences
  @GetMapping("/{userId}/preferences")
  public PreferencesDto getPrefs(@PathVariable Long userId) {
    return userService.getPreferences(userId);
  }
}
