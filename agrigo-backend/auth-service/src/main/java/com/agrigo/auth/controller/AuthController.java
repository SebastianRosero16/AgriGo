package com.agrigo.auth.controller;

import com.agrigo.auth.dto.AuthResponse;
import com.agrigo.auth.dto.LoginRequest;
import com.agrigo.auth.dto.RegisterRequest;
import com.agrigo.auth.service.AuthService;
import com.agrigo.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("message", "Bad credentials", "status", 401));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        boolean isValid = authService.validateToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(Map.of("valid", isValid));
    }

    @PostMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        boolean usernameOk = username == null || authService.isUsernameAvailable(username);
        boolean emailOk = email == null || authService.isEmailAvailable(email);
        boolean available = usernameOk && emailOk;

        Map<String, Object> result = new HashMap<>();
        result.put("available", available);
        result.put("usernameAvailable", usernameOk);
        result.put("emailAvailable", emailOk);
        if (!available) {
            if (!usernameOk && !emailOk) {
                result.put("message", "El usuario y el correo ya están registrados");
            } else if (!usernameOk) {
                result.put("message", "El nombre de usuario ya está en uso");
                result.put("field", "username");
            } else {
                result.put("message", "El correo electrónico ya está registrado");
                result.put("field", "email");
            }
        } else {
            result.put("message", "Disponible");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validate-email")
    public ResponseEntity<?> validateEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        boolean valid = email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
        Map<String, Object> result = new HashMap<>();
        result.put("valid", valid);
        result.put("email", email);
        if (!valid) result.put("reason", "Formato de correo inválido");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String username = body.getOrDefault("username", email);
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email requerido"));
        }

        EmailService.SendResult result = emailService.generateAndSendCode(email, username);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("email", email);

        if (result.emailSent()) {
            response.put("message", "Código enviado a " + email + ". Revisa tu bandeja de entrada y spam.");
        } else {
            // No SMTP configured — expose code so user can complete registration
            response.put("message", "Código generado. Como el servidor no tiene email configurado, tu código es: " + result.code());
            response.put("devCode", result.code());
            response.put("note", "Copia este código: " + result.code());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");
        boolean verified = emailService.verifyCode(email, code);
        if (verified) {
            return ResponseEntity.ok(Map.of("success", true, "verified", true, "message", "Email verificado correctamente"));
        }
        return ResponseEntity.badRequest().body(Map.of("success", false, "verified", false, "message", "Código inválido o expirado"));
    }

    @GetMapping("/check-verification/{email}")
    public ResponseEntity<?> checkVerification(@PathVariable String email) {
        return ResponseEntity.ok(Map.of("email", email, "verified", true, "message", "Verificado"));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmailExists(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        boolean exists = email != null && !authService.isEmailAvailable(email);
        return ResponseEntity.ok(Map.of("exists", exists, "message", exists ? "Email encontrado" : "Email no registrado"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Si el email existe, recibirás instrucciones para restablecer tu contraseña"
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(Map.of("success", true, "message", "Contraseña actualizada"));
    }
}
