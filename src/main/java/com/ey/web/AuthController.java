
package com.ey.web;

import com.ey.dto.UserDto;
import com.ey.dto.auth.LoginRequest;
import com.ey.dto.auth.RegisterRequest;
import com.ey.dto.auth.TokenResponse;
import com.ey.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;


  public AuthController(AuthService authService) {
    this.authService = authService;
  }


  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@RequestBody RegisterRequest req){
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(req));
  }


  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req){
    return ResponseEntity.ok(authService.login(req));
  }

  @PostMapping("/forgot-password")
  public Map<String,String> forgot(@RequestBody Map<String,String> body){
    authService.initiateReset(body.get("email"));
    return Map.of("message","Password reset email sent");
  }

 
  @PostMapping("/verify-reset-code")
  public Map<String,Object> verify(@RequestBody Map<String,String> body){
    boolean valid = authService.verifyReset(body.get("email"), body.get("code"));
    return Map.of("valid", valid, "message", valid ? "Code verified" : "Invalid");
  }
}
