package com.ey.service;

import com.ey.dto.UserDto;
import com.ey.dto.common.UpdateAck;
import com.ey.dto.user.*;
import com.ey.domain.User;
import com.ey.repo.UserRepo;
import com.ey.security.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, CurrentUser currentUser, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.currentUser = currentUser;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto me() {
        User u = userRepo.findByEmail(currentUser.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.from(u);
    }


    @Transactional
    public void updateMe(UserUpdateRequest req) {

        User u = userRepo.findByEmail(currentUser.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getFirstName() != null && !req.getFirstName().isBlank())
            u.setFirstName(req.getFirstName().trim());

        if (req.getLastName() != null && !req.getLastName().isBlank())
            u.setLastName(req.getLastName().trim());

        if (req.getPhone() != null && !req.getPhone().isBlank())
            u.setPhone(req.getPhone().trim());

        userRepo.save(u);
    }

    @Transactional
    public UpdateAck updatePhone(PhoneUpdate req) {

        if (req.getPhone() == null || req.getPhone().isBlank())
            throw new RuntimeException("Phone number cannot be empty");

        User u = userRepo.findByEmail(currentUser.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        u.setPhone(req.getPhone().trim());
        userRepo.save(u);

        return new UpdateAck("Phone updated successfully");
    }


    @Transactional
    public UpdateAck updateEmail(EmailUpdate req) {

        if (req.getEmail() == null || req.getEmail().isBlank())
            throw new RuntimeException("Email cannot be empty");

        String newEmail = req.getEmail().trim();
        String current = currentUser.email();

        if (!newEmail.equalsIgnoreCase(current) && userRepo.existsByEmail(newEmail))
            throw new RuntimeException("Email already in use");

        User u = userRepo.findByEmail(current)
                .orElseThrow(() -> new RuntimeException("User not found"));

        u.setEmail(newEmail);
        userRepo.save(u);

        return new UpdateAck("Email updated successfully");
    }


    @Transactional
    public UpdateAck updatePassword(PasswordUpdate req) {

        User u = userRepo.findByEmail(currentUser.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getOldPassword(), u.getPasswordHash()))
            throw new RuntimeException("Old password is incorrect");

        u.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepo.save(u);

        return new UpdateAck("Password updated successfully");
    }

    public UserDetail getUser(Long userId) {
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDetail.from(u);
    }

 
    public UserActivityDto activity(Long userId) {
        return new UserActivityDto();
    }


    @Transactional
    public void updatePreferences(Long userId, PreferencesDto r) {
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        u.getPreferences().setBedType(r.getBedType());
        u.getPreferences().setFloorPreference(r.getFloorPreference());
        u.getPreferences().setLateCheckout(r.isLateCheckout());
        u.getPreferences().setSmoking(r.isSmoking());

        userRepo.save(u);
    }

    public PreferencesDto getPreferences(Long userId) {
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return PreferencesDto.from(u);
    }
}
