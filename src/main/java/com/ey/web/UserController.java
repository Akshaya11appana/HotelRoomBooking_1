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

    @GetMapping("/me")
    public UserDto me() {
        return userService.me();
    }

    @PutMapping("/me")
    public Map<String, String> update(@RequestBody UserUpdateRequest r) {
        userService.updateMe(r);
        return Map.of("message", "Profile updated");
    }

    @PutMapping("/update-phone")
    public UpdateAck updatePhone(@RequestBody PhoneUpdate r) {
        return userService.updatePhone(r);
    }

    @PutMapping("/update-email")
    public UpdateAck updateEmail(@RequestBody EmailUpdate r) {
        return userService.updateEmail(r);
    }

    @PutMapping("/update-password")
    public UpdateAck updatePassword(@RequestBody PasswordUpdate r) {
        return userService.updatePassword(r);
    }

    @GetMapping("/{userId}")
    public UserDetail getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/activity")
    public UserActivityDto activity(@PathVariable Long userId) {
        return userService.activity(userId);
    }

    @PutMapping("/{userId}/preferences")
    public Map<String, String> updatePreferences(
            @PathVariable Long userId,
            @RequestBody PreferencesDto r
    ) {
        userService.updatePreferences(userId, r);
        return Map.of("message", "Preferences updated");
    }

    @GetMapping("/{userId}/preferences")
    public PreferencesDto getPreferences(@PathVariable Long userId) {
        return userService.getPreferences(userId);
    }
}
