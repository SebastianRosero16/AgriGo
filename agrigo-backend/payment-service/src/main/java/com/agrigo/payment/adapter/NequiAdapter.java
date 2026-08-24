package com.agrigo.payment.adapter;

import com.agrigo.payment.dto.PaymentRequest;
import com.agrigo.payment.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class NequiAdapter implements PaymentGatewayAdapter {
    
    @Value("${nequi.api.url:https://api.nequi.com.co/payments/v2/payment}")
    private String apiUrl;
    
    @Value("${nequi.api.key:}")
    private String apiKey;
    
    @Value("${nequi.client.id:}")
    private String clientId;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", apiKey);
            headers.set("x-client-id", clientId);
            
            Map<String, Object> payload = new HashMap<>();
            payload.put("phoneNumber", request.getPhoneNumber());
            payload.put("value", request.getAmount());
            payload.put("code", "1");
            payload.put("reference", request.getReference());
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return new PaymentResponse(
                        true,
                        "Payment processed successfully",
                        UUID.randomUUID().toString(),
                        "APPROVED",
                        "NEQUI"
                );
            }
            
            return new PaymentResponse(false, "Payment failed", null, "REJECTED", "NEQUI");
            
        } catch (Exception e) {
            log.error("Nequi payment error: {}", e.getMessage());
            return new PaymentResponse(false, "Payment error: " + e.getMessage(), null, "ERROR", "NEQUI");
        }
    }
    
    @Override
    public String getGatewayName() {
        return "NEQUI";
    }
}
