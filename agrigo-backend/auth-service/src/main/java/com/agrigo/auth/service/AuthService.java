package com.agrigo.auth.service;

import com.agrigo.auth.dto.AuthResponse;
import com.agrigo.auth.dto.LoginRequest;
import com.agrigo.auth.dto.RegisterRequest;
import com.agrigo.auth.entity.User;
import com.agrigo.auth.repository.UserRepository;
import com.agrigo.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        if (request.getUsername() != null && userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Nombre de usuario ya en uso");
        }
        
        User user = new User();
        user.setUsername(request.getUsername() != null ? request.getUsername() : request.getEmail());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getEffectiveName());
        user.setRole(request.getRole() != null ? request.getRole().toUpperCase() : "BUYER");
        user.setPhone(request.getPhone());
        
        userRepository.save(user);
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(),
                user.getName(), user.getRole(),
                user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
    }
    
    public AuthResponse login(LoginRequest request) {
        String identifier = request.getEffectiveIdentifier();
        
        // Try to find by username first, then by email
        User user = null;
        if (identifier != null && identifier.contains("@")) {
            user = userRepository.findByEmail(identifier).orElse(null);
        }
        if (user == null) {
            user = userRepository.findByUsername(identifier).orElse(null);
        }
        if (user == null) {
            user = userRepository.findByEmail(identifier).orElse(null);
        }
        if (user == null) {
            throw new RuntimeException("Bad credentials");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(),
                user.getName(), user.getRole(),
                user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
    }
    
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
    
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }
    
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }
}
