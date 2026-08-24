package com.agrigo.ai.dto;

import lombok.Data;
import java.util.Map;

@Data
public class RecommendationRequest {
    // Legacy fields
    private String cropType;
    private String soilType;
    private String climateZone;
    private String farmSize;
    private String currentSeason;

    // New fields from frontend
    private String type;       // GENERAL, FERTILIZER, PESTICIDE, OPTIMIZATION
    private Long cropId;
    private Map<String, Object> context; // question, cropName, cropStage, etc.

    public String getQuestion() {
        if (context == null) return null;
        Object q = context.get("question");
        if (q != null) return q.toString();
        Object m = context.get("userMessage");
        return m != null ? m.toString() : null;
    }

    public String getEffectiveCropName() {
        if (context != null && context.get("cropName") != null)
            return context.get("cropName").toString();
        return cropType != null ? cropType : "cultivo";
    }

    public String getEffectiveCropType() {
        if (context != null && context.get("cropType") != null)
            return context.get("cropType").toString();
        return cropType != null ? cropType : "";
    }

    public String getEffectiveStage() {
        if (context != null && context.get("cropStage") != null)
            return context.get("cropStage").toString();
        return "";
    }

    public String getEffectiveSoil() {
        if (context != null && context.get("soilType") != null)
            return context.get("soilType").toString();
        return soilType != null ? soilType : "no especificado";
    }

    public String getEffectiveClimate() {
        if (context != null && context.get("climate") != null)
            return context.get("climate").toString();
        return climateZone != null ? climateZone : "no especificado";
    }

    public String getEffectiveArea() {
        if (context != null && context.get("area") != null)
            return context.get("area").toString();
        return farmSize != null ? farmSize : "no especificada";
    }
}
