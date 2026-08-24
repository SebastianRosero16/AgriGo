package com.agrigo.ai.controller;

import com.agrigo.ai.dto.RecommendationRequest;
import com.agrigo.ai.dto.RecommendationResponse;
import com.agrigo.ai.service.AIRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AIRecommendationController {

    private final AIRecommendationService aiService;

    // Frontend calls POST /ai/recommend
    @PostMapping("/ai/recommend")
    public ResponseEntity<RecommendationResponse> getRecommendation(@RequestBody RecommendationRequest request) {
        RecommendationResponse response = aiService.getRecommendation(request);
        return ResponseEntity.ok(response);
    }

    // Legacy endpoint
    @PostMapping("/api/recommendations")
    public ResponseEntity<RecommendationResponse> getRecommendationLegacy(@RequestBody RecommendationRequest request) {
        return getRecommendation(request);
    }

    @GetMapping("/ai/explain/{cropId}")
    public ResponseEntity<String> explain(@PathVariable Long cropId) {
        return ResponseEntity.ok("Para obtener una explicación detallada, usa el chat y pregunta sobre tu cultivo específico.");
    }

    @GetMapping("/ai/recommendations/{cropId}")
    public ResponseEntity<?> getHistory(@PathVariable Long cropId) {
        return ResponseEntity.ok(java.util.List.of());
    }

    @PostMapping("/ai/shopping-assistant")
    public ResponseEntity<?> shoppingAssistant(@RequestBody java.util.Map<String, Object> body) {
        String query = body.getOrDefault("query", "").toString();
        return ResponseEntity.ok(java.util.Map.of(
            "response", "Para comprar insumos agrícolas, visita el Marketplace de AgriGoSJ donde encontrarás fertilizantes, pesticidas y semillas disponibles.",
            "query", query
        ));
    }
}
