package com.agrigo.payment.factory;

import com.agrigo.payment.adapter.PaymentGatewayAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentGatewayFactory {
    
    private final List<PaymentGatewayAdapter> adapters;
    
    public PaymentGatewayAdapter getAdapter(String gatewayName) {
        return adapters.stream()
                .filter(adapter -> adapter.getGatewayName().equalsIgnoreCase(gatewayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Payment gateway not supported: " + gatewayName));
    }
}
