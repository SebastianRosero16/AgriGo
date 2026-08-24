package com.agrigo.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // can be username or email
    private String email;    // kept for compatibility
    private String password;

    public String getEffectiveIdentifier() {
        if (username != null && !username.isBlank()) return username;
        return email;
    }
}
