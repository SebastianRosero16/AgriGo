package com.agrigo.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${app.mail.from:${spring.mail.username:noreply@agrigo.com}}")
    private String fromEmail;

    // code store: email -> code
    private final Map<String, String> codes = new ConcurrentHashMap<>();
    private final Map<String, Long> expiry = new ConcurrentHashMap<>();

    public SendResult generateAndSendCode(String toEmail, String username) {
        String code = String.format("%06d", new Random().nextInt(1000000));
        codes.put(toEmail, code);
        expiry.put(toEmail, System.currentTimeMillis() + 10 * 60 * 1000L);

        boolean mailConfigured = mailUsername != null && !mailUsername.isBlank();
        boolean sent = false;

        if (mailConfigured) {
            sent = trySendEmail(toEmail, username, code);
        }

        if (!sent) {
            log.info("╔══════════════════════════════════╗");
            log.info("  CÓDIGO DE VERIFICACIÓN");
            log.info("  Email  : {}", toEmail);
            log.info("  Código : {}", code);
            log.info("╚══════════════════════════════════╝");
        }

        return new SendResult(code, sent);
    }

    private boolean trySendEmail(String toEmail, String username, String code) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(toEmail);
            msg.setSubject("AgriGoSJ - Tu código de verificación");
            msg.setText(
                "Hola " + username + ",\n\n" +
                "Tu código de verificación para AgriGoSJ es:\n\n" +
                "        " + code + "\n\n" +
                "Este código expira en 10 minutos.\n\n" +
                "Si no solicitaste este registro, ignora este mensaje.\n\n" +
                "— Equipo AgriGoSJ"
            );
            mailSender.send(msg);
            log.info("✅ Código enviado por email a {}", toEmail);
            return true;
        } catch (Exception e) {
            log.error("❌ Error enviando email a {}: {}", toEmail, e.getMessage());
            return false;
        }
    }

    public boolean verifyCode(String email, String inputCode) {
        String stored = codes.get(email);
        Long exp = expiry.get(email);

        if (stored == null || exp == null) return false;
        if (System.currentTimeMillis() > exp) {
            codes.remove(email);
            expiry.remove(email);
            return false;
        }

        boolean valid = stored.equals(inputCode);
        if (valid) {
            codes.remove(email);
            expiry.remove(email);
        }
        return valid;
    }

    public record SendResult(String code, boolean emailSent) {}
}
