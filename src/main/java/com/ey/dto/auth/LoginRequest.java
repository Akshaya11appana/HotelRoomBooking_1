
package com.ey.dto.auth;

public record LoginRequest(
    String email,
    String password
) {}
