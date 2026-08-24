package com.agrigo.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String name; // fallback
    private String role;
    private String phone;

    public String getEffectiveName() {
        if (fullName != null && !fullName.isBlank()) return fullName;
        if (name != null && !name.isBlank()) return name;
        return username;
    }
}
