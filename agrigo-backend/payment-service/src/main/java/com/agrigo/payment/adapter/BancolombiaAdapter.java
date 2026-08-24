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
public class BancolombiaAdapter implements PaymentGatewayAdapter {
    
    @Value("${bancolombia.api.url:https://api.bancolombia.com/v1/payments}")
    private String apiUrl;
    
    @Value("${bancolombia.api.key:}")
    private String apiKey;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            
            Map<String, Object> payload = new HashMap<>();
            payload.put("amount", request.getAmount());
            payload.put("currency", "COP");
            payload.put("description", request.getDescription());
            payload.put("reference", request.getReference());
            payload.put("customerEmail", request.getCustomerEmail());
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                return new PaymentResponse(
                        true,
                        "Payment processed successfully",
                        UUID.randomUUID().toString(),
                        "APPROVED",
                        "BANCOLOMBIA"
                );
            }
            
            return new PaymentResponse(false, "Payment failed", null, "REJECTED", "BANCOLOMBIA");
            
        } catch (Exception e) {
            log.error("Bancolombia payment error: {}", e.getMessage());
            return new PaymentResponse(false, "Payment error: " + e.getMessage(), null, "ERROR", "BANCOLOMBIA");
        }
    }
    
    @Override
    public String getGatewayName() {
        return "BANCOLOMBIA";
    }
}
