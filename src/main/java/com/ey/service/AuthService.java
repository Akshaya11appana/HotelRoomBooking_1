
package com.ey.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ey.domain.Role;
import com.ey.domain.User;
import com.ey.dto.UserDto;
import com.ey.dto.auth.LoginRequest;
import com.ey.dto.auth.RegisterRequest;
import com.ey.dto.auth.TokenResponse;
import com.ey.repo.UserRepo;
import com.ey.security.JwtService;

@Service
public class AuthService {

  private final UserRepo userRepo;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UserRepo userRepo,
                     PasswordEncoder encoder,
                     JwtService jwt) {
    this.userRepo = userRepo;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  public UserDto register(RegisterRequest r){
    userRepo.findByEmail(r.email()).ifPresent(u -> {
      throw new RuntimeException("Email already exists");
    });

    User u = new User();
    u.setFirstName(r.firstName());
    u.setLastName(r.lastName());
    u.setEmail(r.email());
    u.setPhone(r.phone());
    u.setPasswordHash(encoder.encode(r.password()));
    u.setCreatedAt(LocalDateTime.now());
    u.setRoles(Set.of(Role.GUEST));

    userRepo.save(u);
    return UserDto.from(u);
  }

  public TokenResponse login(LoginRequest r){
    User u = userRepo.findByEmail(r.email())
        .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if(!encoder.matches(r.password(), u.getPasswordHash())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwt.generate(
        u.getEmail(),
        u.getRoles().stream().map(Enum::name).toList()
    );

    return new TokenResponse(token, "Bearer", 3600);
  }

  public void initiateReset(String email){
  }

  public boolean verifyReset(String email, String code){
    return true;
  }
}
