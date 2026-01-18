
package com.ey.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
  public String email() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) return null;
    Object principal = auth.getPrincipal();
    // We set subject=email in the JWT, and in JwtAuthFilter we used it as principal
    return principal != null ? principal.toString() : null;
  }
}
