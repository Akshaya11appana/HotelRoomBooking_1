package com.ey.dto.auth;

public record RegisterRequest(
    String firstName,
    String lastName,
    String email,
    String phone,
    String password
) {}
