package com.agrigo.auth.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserDto user;

    @Data
    public static class UserDto {
        private Long id;
        private String username;
        private String email;
        private String role;
        private String fullName;
        private String createdAt;
    }

    public AuthResponse(String token, Long id, String username, String email, String name, String role, String createdAt) {
        this.token = token;
        this.user = new UserDto();
        this.user.id = id;
        this.user.username = username;
        this.user.email = email;
        this.user.role = role;
        this.user.fullName = name;
        this.user.createdAt = createdAt;
    }
}
