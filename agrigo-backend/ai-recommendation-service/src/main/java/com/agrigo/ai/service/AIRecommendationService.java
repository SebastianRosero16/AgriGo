package com.agrigo.ai.service;

import com.agrigo.ai.dto.RecommendationRequest;
import com.agrigo.ai.dto.RecommendationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class AIRecommendationService {

    public RecommendationResponse getRecommendation(RecommendationRequest request) {
        String question = request.getQuestion();
        String cropName = request.getEffectiveCropName();
        String cropType = request.getEffectiveCropType();
        String stage = request.getEffectiveStage();
        String soil = request.getEffectiveSoil();
        String climate = request.getEffectiveClimate();
        String area = request.getEffectiveArea();
        String type = request.getType() != null ? request.getType() : "GENERAL";

        log.info("AI Request - crop: {}, question: {}", cropName, question);

        String answer;

        if (question == null || question.isBlank()) {
            answer = generarBienvenida(cropName, cropType, stage);
        } else {
            answer = responderPregunta(question, cropName, cropType, stage, soil, climate, area, type);
        }

        RecommendationResponse response = new RecommendationResponse();
        response.setExplanation(answer);
        response.setCropType(cropType);
        response.setAiInsight(answer);
        return response;
    }

    private String generarBienvenida(String cropName, String cropType, String stage) {
        return String.format(
            "Hola! Estoy listo para ayudarte con tu cultivo de %s.\n\n" +
            "Puedo responder preguntas sobre:\n" +
            "• Riego y necesidades de agua\n" +
            "• Fertilización y nutrientes\n" +
            "• Control de plagas y enfermedades\n" +
            "• Cosecha y maduración\n" +
            "• Cuidados según la etapa actual (%s)\n\n" +
            "¿En qué te puedo ayudar hoy?",
            cropName, stage.isBlank() ? "en desarrollo" : traducirEtapa(stage)
        );
    }

    private String responderPregunta(String pregunta, String cropName, String cropType,
                                      String stage, String soil, String climate, String area, String type) {
        String q = pregunta.toLowerCase(Locale.ROOT);

        // Validar que la pregunta sea sobre agricultura/cultivos
        if (esPreguntaFueraDeContexto(q, cropName)) {
            return String.format(
                "Solo puedo responder preguntas relacionadas con tu cultivo de %s y agricultura en general.\n\n" +
                "Por ejemplo puedes preguntarme:\n" +
                "• ¿Cuándo debo regar mi %s?\n" +
                "• ¿Qué fertilizante necesita en etapa de %s?\n" +
                "• ¿Cómo controlar plagas en mi %s?\n" +
                "• ¿Cuándo está listo para cosechar?",
                cropName, cropName, traducirEtapa(stage), cropName
            );
        }

        // Riego
        if (contieneAlguna(q, "rieg", "agua", "irrigar", "regar", "humedad", "seco", "secar")) {
            return responderRiego(cropName, cropType, stage, climate, soil);
        }

        // Fertilización
        if (contieneAlguna(q, "fertiliz", "abono", "nutrient", "nitrogen", "nitrógeno", "potasio", "fósforo", "fosfor", "npk", "urea")) {
            return responderFertilizacion(cropName, cropType, stage, area);
        }

        // Plagas y enfermedades
        if (contieneAlguna(q, "plaga", "insecto", "enfermedad", "hongo", "bacteria", "virus", "amaril", "mancha", "pudrición", "pudr", "pest", "control", "fungo", "bicho")) {
            return responderPlagas(cropName, cropType, stage, climate);
        }

        // Cosecha
        if (contieneAlguna(q, "cosecha", "cosechar", "recolect", "madur", "listo", "cuándo", "cuando", "tiempo", "días", "semanas", "meses", "dura", "duración")) {
            return responderCosecha(cropName, cropType, stage);
        }

        // Suelo
        if (contieneAlguna(q, "suelo", "tierra", "sustrato", "ph", "arcilla", "arena", "turba")) {
            return responderSuelo(cropName, cropType, soil);
        }

        // Clima / temperatura
        if (contieneAlguna(q, "temperatura", "clima", "calor", "frío", "frio", "helada", "lluvia")) {
            return responderClima(cropName, cropType, climate);
        }

        // Siembra / trasplante
        if (contieneAlguna(q, "siembra", "sembrar", "semilla", "trasplant", "germinar", "germinación", "plantar")) {
            return responderSiembra(cropName, cropType, climate);
        }

        // Etapa actual
        if (contieneAlguna(q, "etapa", "fase", "estado", "ciclo", "desarrollo", "crecimiento")) {
            return responderEtapa(cropName, cropType, stage, soil, climate);
        }

        // Pregunta general sobre el cultivo
        return responderGeneral(cropName, cropType, stage, soil, climate, area, pregunta);
    }

    // ── Respuestas específicas ────────────────────────────────────────────────

    private String responderRiego(String cropName, String cropType, String stage, String climate, String soil) {
        String ct = cropType.toLowerCase(Locale.ROOT);
        String stg = stage.toLowerCase(Locale.ROOT);
        String clim = climate.toLowerCase(Locale.ROOT);

        // Frecuencia base según tipo de cultivo
        String frecuencia, cantidad, tips;

        if (contieneAlguna(ct, "maíz", "maiz", "corn")) {
            frecuencia = "cada 7-10 días en clima templado, cada 5-7 días en clima cálido";
            cantidad = "30-40 mm por riego (300-400 litros por metro cuadrado)";
            tips = "El maíz es muy sensible al estrés hídrico en la etapa de floración y llenado de grano. Nunca dejes que el suelo se seque completamente.";
        } else if (contieneAlguna(ct, "tomat", "tomato")) {
            frecuencia = "cada 2-3 días, manteniendo humedad constante";
            cantidad = "2-3 litros por planta por riego";
            tips = "El riego irregular en el tomate causa pudrición apical. Riega siempre a la misma hora, preferiblemente en la mañana.";
        } else if (contieneAlguna(ct, "papa", "potato")) {
            frecuencia = "cada 8-10 días, más frecuente en floración";
            cantidad = "25-30 mm por riego";
            tips = "Evita el exceso de agua en papa pues favorece la pudrición del tubérculo. El suelo debe estar húmedo pero no encharcado.";
        } else if (contieneAlguna(ct, "café", "coffee")) {
            frecuencia = "cada 15-20 días en invierno, cada 8-10 días en verano";
            cantidad = "20-25 litros por planta adulta por mes";
            tips = "El café necesita agua abundante en floración y formación del fruto, pero tolera algo de sequía en la fase de maduración.";
        } else if (contieneAlguna(ct, "arroz", "rice")) {
            frecuencia = "mantener lámina de agua de 5-10 cm durante el crecimiento activo";
            cantidad = "riego continuo o inundación controlada";
            tips = "El arroz requiere mucha agua especialmente en trasplante y macollamiento. Drena el campo 2 semanas antes de la cosecha.";
        } else {
            frecuencia = "cada 5-8 días según la temperatura";
            cantidad = "según el tamaño y necesidad del cultivo";
            tips = "Verifica siempre la humedad del suelo antes de regar introduciendo el dedo 5 cm. Si sale tierra húmeda, no es necesario regar aún.";
        }

        // Ajuste por etapa
        String ajusteEtapa = "";
        if (contieneAlguna(stg, "seedling", "plántula", "siembra")) {
            ajusteEtapa = "\n\n🌱 En etapa de plántula: riega con más frecuencia pero en menor cantidad para no dañar las raíces jóvenes. Usa regadera con ducha fina.";
        } else if (contieneAlguna(stg, "flowering", "florec", "flor")) {
            ajusteEtapa = "\n\n🌸 En floración (tu etapa actual): es crítico mantener humedad constante. La falta de agua en esta etapa reduce drásticamente el rendimiento.";
        } else if (contieneAlguna(stg, "harvest", "cosecha")) {
            ajusteEtapa = "\n\n🌾 Cerca de la cosecha: reduce el riego gradualmente para favorecer la maduración y facilitar la recolección.";
        }

        // Ajuste por clima
        String ajusteClima = "";
        if (contieneAlguna(clim, "seco", "árido", "calur", "caliente")) {
            ajusteClima = "\n\n☀️ Con tu clima cálido/seco: aumenta la frecuencia de riego y considera riego por goteo para reducir pérdidas por evaporación.";
        } else if (contieneAlguna(clim, "lluvi", "húmedo", "tropical")) {
            ajusteClima = "\n\n🌧️ Con tu clima lluvioso: en épocas de lluvia puedes suspender el riego artificial. Asegura buen drenaje para evitar encharcamiento.";
        }

        return String.format(
            "💧 Riego para tu %s:\n\n" +
            "• Frecuencia: %s\n" +
            "• Cantidad: %s\n\n" +
            "💡 %s" +
            "%s%s\n\n" +
            "Tip: El mejor momento para regar es temprano en la mañana (6-8 am) para minimizar la evaporación.",
            cropName, frecuencia, cantidad, tips, ajusteEtapa, ajusteClima
        );
    }

    private String responderFertilizacion(String cropName, String cropType, String stage, String area) {
        String ct = cropType.toLowerCase(Locale.ROOT);
        String stg = stage.toLowerCase(Locale.ROOT);

        String planBase;
        if (contieneAlguna(ct, "maíz", "maiz", "corn")) {
            planBase = "• Siembra: 100 kg/ha de DAP (18-46-0)\n• Aporque (25-30 días): 150 kg/ha de Urea (46-0-0)\n• Pre-floración: 50 kg/ha de KCl (0-0-60)";
        } else if (contieneAlguna(ct, "tomat", "tomato")) {
            planBase = "• Siembra: 200 kg/ha de 10-30-10\n• Vegetativo: 150 kg/ha de Urea cada 15 días\n• Fructificación: 100 kg/ha de KNO3 (13-0-46)";
        } else if (contieneAlguna(ct, "papa", "potato")) {
            planBase = "• Siembra: 200 kg/ha de 10-20-20\n• Aporque: 150 kg/ha de Urea + 50 kg/ha de KCl\n• Tuberización: 100 kg/ha de KSO4";
        } else if (contieneAlguna(ct, "café", "coffee")) {
            planBase = "• 3 aplicaciones/año: Febrero, Junio y Octubre\n• Por planta adulta: 200g de 17-6-18-2 o similar\n• Foliar: Boro y Zinc en floración";
        } else {
            planBase = "• Siembra/trasplante: fertilizante base rico en fósforo (P)\n• Crecimiento activo: nitrógeno (N) cada 2-3 semanas\n• Producción: potasio (K) para mejorar calidad";
        }

        String recomEtapa = "";
        if (contieneAlguna(stg, "seedling", "plántula")) {
            recomEtapa = "\n\n🌱 En tu etapa actual (plántula): usa fertilizantes de arranque con alto fósforo para estimular el desarrollo radicular. Evita exceso de nitrógeno.";
        } else if (contieneAlguna(stg, "vegetat")) {
            recomEtapa = "\n\n🌿 En tu etapa vegetativa: prioriza el nitrógeno para estimular el crecimiento del follaje. Aplica cada 15-20 días.";
        } else if (contieneAlguna(stg, "flower", "flor")) {
            recomEtapa = "\n\n🌸 En floración: reduce el nitrógeno y aumenta el potasio y fósforo para favorecer el cuajado de frutos y flores.";
        } else if (contieneAlguna(stg, "harvest", "cosecha")) {
            recomEtapa = "\n\n🌾 Cerca de cosecha: suspende la fertilización nitrogenada. Puedes aplicar potasio para mejorar calidad del fruto.";
        }

        return String.format(
            "🌿 Plan de fertilización para tu %s:\n\n%s%s\n\n" +
            "⚠️ Importante: Realiza un análisis de suelo antes de fertilizar para ajustar las dosis exactas a tu parcela. " +
            "Aplica los fertilizantes cuando el suelo esté húmedo y evita hacerlo en horas de mucho sol.",
            cropName, planBase, recomEtapa
        );
    }

    private String responderPlagas(String cropName, String cropType, String stage, String climate) {
        String ct = cropType.toLowerCase(Locale.ROOT);

        String plagasComunes, control;
        if (contieneAlguna(ct, "maíz", "maiz", "corn")) {
            plagasComunes = "gusano cogollero (Spodoptera frugiperda), barrenador del tallo, pulgones, trips";
            control = "• Gusano cogollero: Clorpirifos 2 ml/L o Bacillus thuringiensis (ecológico)\n• Pulgones: Imidacloprid 0.5 ml/L o jabón potásico\n• Barrenador: Lambda-cihalotrina 1 ml/L";
        } else if (contieneAlguna(ct, "tomat", "tomato")) {
            plagasComunes = "mosca blanca, trips, araña roja, tuta absoluta, alternaria, tizón tardío";
            control = "• Mosca blanca: Imidacloprid 0.5 ml/L o trampas amarillas\n• Trips: Spinosad 1 ml/L\n• Tizón: Mancozeb 2.5 g/L preventivo\n• Araña roja: Abamectina 1 ml/L";
        } else if (contieneAlguna(ct, "papa", "potato")) {
            plagasComunes = "tizón tardío (Phytophthora), polilla de la papa, pulgones, alternaria";
            control = "• Tizón tardío: Metalaxil+Mancozeb 2.5 g/L preventivo cada 7 días\n• Polilla: Clorpirifos 2 ml/L\n• Pulgones: Dimetoato 1.5 ml/L";
        } else if (contieneAlguna(ct, "café", "coffee")) {
            plagasComunes = "broca del café (Hypothenemus hampei), roya (Hemileia vastatrix), antracnosis";
            control = "• Broca: Beauveria bassiana (biológico) o Endosulfan donde permitido\n• Roya: Cobre + Mancozeb preventivo cada 21 días\n• Antracnosis: Propiconazol 0.5 ml/L";
        } else {
            plagasComunes = "pulgones, trips, mosca blanca, ácaros, hongos foliares";
            control = "• Insectos: Imidacloprid 0.5 ml/L o productos sistémicos\n• Hongos: Fungicidas a base de cobre preventivos\n• Ácaros: Abamectina 1 ml/L";
        }

        String climaTip = "";
        if (contieneAlguna(climate.toLowerCase(Locale.ROOT), "húmedo", "lluvi", "tropical")) {
            climaTip = "\n\n☔ Con tu clima húmedo: los hongos son la principal amenaza. Aplica fungicidas preventivos cada 10-14 días durante épocas lluviosas y asegura buena ventilación entre plantas.";
        }

        return String.format(
            "🐛 Control de plagas y enfermedades para tu %s:\n\n" +
            "Plagas y enfermedades más comunes: %s\n\n" +
            "Tratamientos recomendados:\n%s%s\n\n" +
            "🌿 Prevención: Mantén limpia la parcela de malezas, realiza rotación de cultivos y monitorea semanalmente. " +
            "Usa productos biológicos como primera opción cuando sea posible.",
            cropName, plagasComunes, control, climaTip
        );
    }

    private String responderCosecha(String cropName, String cropType, String stage) {
        String ct = cropType.toLowerCase(Locale.ROOT);
        String stg = stage.toLowerCase(Locale.ROOT);

        String duracion, indicadores, etapasRestantes;
        if (contieneAlguna(ct, "maíz", "maiz", "corn")) {
            duracion = "90 a 120 días desde la siembra";
            indicadores = "los estigmas se tornan cafés, el grano endurece y tiene contenido de humedad del 25-30%";
        } else if (contieneAlguna(ct, "tomat", "tomato")) {
            duracion = "60 a 90 días después del trasplante";
            indicadores = "el fruto cambia de color verde a rojo/amarillo según la variedad, se ablanda ligeramente al tacto";
        } else if (contieneAlguna(ct, "papa", "potato")) {
            duracion = "90 a 130 días según la variedad";
            indicadores = "el follaje se amarilla y seca naturalmente (senescencia), la piel del tubérculo no se pela fácilmente";
        } else if (contieneAlguna(ct, "café", "coffee")) {
            duracion = "8 a 9 meses después de la floración para que el fruto madure";
            indicadores = "el grano (cereza) tiene color rojo o amarillo intenso según la variedad, se desprende fácilmente al jalarlo";
        } else if (contieneAlguna(ct, "arroz", "rice")) {
            duracion = "100 a 130 días según la variedad";
            indicadores = "el 80-85% de los granos están dorados, la panícula se dobla por el peso";
        } else {
            duracion = "varía según la variedad, generalmente 60-120 días";
            indicadores = "observa el cambio de color, tamaño y firmeza característica de la especie";
        }

        // Estimación según etapa actual
        String estimacion = "";
        if (contieneAlguna(stg, "seedling", "plántula")) {
            estimacion = "\n\n📅 Desde tu etapa actual (plántula), tienes aproximadamente el 80-90% del ciclo por delante antes de cosechar.";
        } else if (contieneAlguna(stg, "vegetat")) {
            estimacion = "\n\n📅 Desde tu etapa vegetativa, tienes aproximadamente el 50-60% del ciclo por delante.";
        } else if (contieneAlguna(stg, "flower", "flor")) {
            estimacion = "\n\n📅 En tu etapa de floración, la cosecha está a aproximadamente 30-50 días dependiendo de la especie. ¡Ya estás en la recta final!";
        } else if (contieneAlguna(stg, "harvest", "cosecha", "fruiting", "fructif")) {
            estimacion = "\n\n📅 ¡Tu cultivo está en etapa de cosecha o fructificación! Revisa los indicadores de madurez y planifica la recolección pronto.";
        }

        return String.format(
            "🌾 Cosecha de tu %s:\n\n" +
            "• Ciclo total: %s\n" +
            "• Indicadores de madurez: %s%s\n\n" +
            "📌 Recomendación: Cosecha en las horas más frescas del día (mañana temprano) para conservar mejor la calidad del producto.",
            cropName, duracion, indicadores, estimacion
        );
    }

    private String responderSuelo(String cropName, String cropType, String soil) {
        return String.format(
            "🌍 Recomendaciones de suelo para tu %s:\n\n" +
            "Tu suelo actual: %s\n\n" +
            "• pH ideal: 6.0-7.0 para la mayoría de cultivos (neutro a ligeramente ácido)\n" +
            "• Prepara el suelo con arado profundo (25-30 cm) antes de sembrar\n" +
            "• Incorpora materia orgánica (compost, estiércol descompuesto) a razón de 2-3 ton/ha\n" +
            "• Asegura buen drenaje para evitar encharcamiento\n\n" +
            "💡 Realiza un análisis de suelo cada 2 años en laboratorio agrícola para conocer exactamente los niveles de nutrientes y ajustar tu plan de fertilización.",
            cropName, soil.isBlank() || soil.equals("No especificado") ? "no registrado (te recomendamos registrarlo en tu perfil de cultivo)" : soil
        );
    }

    private String responderClima(String cropName, String cropType, String climate) {
        return String.format(
            "🌡️ Consideraciones climáticas para tu %s:\n\n" +
            "Tu clima: %s\n\n" +
            "• Temperaturas óptimas: 18-28°C para la mayoría de cultivos tropicales y subtropicales\n" +
            "• En temperaturas extremas (>35°C o <10°C) el crecimiento se ve afectado\n" +
            "• Protege tu cultivo de heladas con plásticos o mallas antifrost si hay riesgo\n" +
            "• En climas muy calientes: riega más frecuente y usa mulch para conservar humedad\n" +
            "• En climas fríos o con lluvias frecuentes: asegura buen drenaje y ventilación\n\n" +
            "¿Tienes alguna pregunta específica sobre cómo el clima está afectando tu cultivo?",
            cropName, climate.isBlank() || climate.equals("No especificado") ? "no registrado" : climate
        );
    }

    private String responderSiembra(String cropName, String cropType, String climate) {
        String ct = cropType.toLowerCase(Locale.ROOT);
        String densidad, prof, distancia;

        if (contieneAlguna(ct, "maíz", "maiz")) {
            densidad = "50,000-60,000 plantas/ha"; prof = "3-5 cm"; distancia = "70-80 cm entre surcos, 20-25 cm entre plantas";
        } else if (contieneAlguna(ct, "tomat")) {
            densidad = "20,000-25,000 plantas/ha"; prof = "trasplante a 10-15 cm"; distancia = "1.2 m entre surcos, 40-50 cm entre plantas";
        } else if (contieneAlguna(ct, "papa")) {
            densidad = "25,000-35,000 plantas/ha"; prof = "10-15 cm"; distancia = "80-90 cm entre surcos, 30-40 cm entre plantas";
        } else {
            densidad = "según la variedad"; prof = "2-5 cm para semillas pequeñas, hasta 10 cm para tubérculos"; distancia = "consulta el empaque de la semilla";
        }

        return String.format(
            "🌱 Guía de siembra para %s:\n\n" +
            "• Densidad de siembra: %s\n" +
            "• Profundidad: %s\n" +
            "• Distancias: %s\n" +
            "• Temperatura del suelo óptima para germinar: 15-25°C\n" +
            "• Prepara el suelo al menos 2 semanas antes con abonado de fondo\n\n" +
            "La mejor época de siembra en Colombia depende de tu región: en climas medios, siembra al inicio de las lluvias (marzo-abril o septiembre-octubre).",
            cropName, densidad, prof, distancia
        );
    }

    private String responderEtapa(String cropName, String cropType, String stage, String soil, String climate) {
        String etapaTraducida = traducirEtapa(stage);
        String cuidados;

        String stg = stage.toLowerCase(Locale.ROOT);
        if (contieneAlguna(stg, "seedling", "plántula")) {
            cuidados = "• Riega con frecuencia pero en poca cantidad\n• Protege de vientos fuertes y sol directo intenso\n• Controla malezas manualmente\n• Aplica fertilizante de arranque con alto fósforo";
        } else if (contieneAlguna(stg, "vegetat")) {
            cuidados = "• Incrementa el riego gradualmente\n• Aplica nitrógeno para estimular el crecimiento\n• Realiza podas de formación si aplica\n• Monitorea plagas chupadores (pulgones, trips)";
        } else if (contieneAlguna(stg, "flower", "flor")) {
            cuidados = "• Mantén humedad constante — crítico para cuajado\n• Reduce nitrógeno, aumenta potasio y fósforo\n• No apliques pesticidas durante la floración si hay abejas\n• Monitorea hongos y ácaros";
        } else if (contieneAlguna(stg, "fruiting", "fructif")) {
            cuidados = "• Mantén riego regular y uniforme\n• Aplica potasio para mejorar tamaño y calidad del fruto\n• Controla hongos de fruto (botritis, alternaria)\n• Prepara equipos de cosecha";
        } else if (contieneAlguna(stg, "harvest", "cosecha")) {
            cuidados = "• Reduce el riego para facilitar la cosecha\n• Prepara canastas/sacos para la recolección\n• Cosecha en horas frescas (mañana temprano)\n• Almacena en lugar fresco y ventilado";
        } else {
            cuidados = "• Monitorea regularmente el estado de las plantas\n• Mantén riego y fertilización según el calendario\n• Controla malezas y plagas";
        }

        return String.format(
            "📊 Etapa actual de tu %s: %s\n\n" +
            "Cuidados recomendados para esta etapa:\n%s\n\n" +
            "💡 ¿Tienes alguna duda específica sobre el cuidado de tu %s en esta etapa? ¡Pregúntame!",
            cropName, etapaTraducida, cuidados, cropName
        );
    }

    private String responderGeneral(String cropName, String cropType, String stage,
                                     String soil, String climate, String area, String pregunta) {
        return String.format(
            "🌾 Sobre tu %s (%s):\n\n" +
            "Con base en tu pregunta: \"%s\"\n\n" +
            "Tu cultivo actualmente está en etapa de %s, en un suelo %s con clima %s en %s hectáreas.\n\n" +
            "Para darte la mejor recomendación, te sugiero ser más específico. Puedes preguntarme sobre:\n" +
            "• 💧 Riego: \"¿Con qué frecuencia riego mi %s?\"\n" +
            "• 🌿 Fertilización: \"¿Qué fertilizante necesita mi %s ahora?\"\n" +
            "• 🐛 Plagas: \"¿Cómo controlo las plagas en mi %s?\"\n" +
            "• 🌾 Cosecha: \"¿Cuándo está listo para cosechar mi %s?\"\n" +
            "• 📊 Etapa: \"¿Qué cuidados necesita mi %s en esta etapa?\"",
            cropName, cropType, pregunta,
            traducirEtapa(stage), soil, climate, area,
            cropName, cropName, cropName, cropName, cropName
        );
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private boolean esPreguntaFueraDeContexto(String q, String cropName) {
        // Palabras que claramente no son de agricultura
        String[] fueraDeContexto = {
            "fútbol", "futbol", "deporte", "política", "gobierno", "música", "musica",
            "película", "pelicula", "videojuego", "matemática", "historia", "geografía",
            "colombia juega", "partido", "selección", "seleccion", "receta", "cocina",
            "amor", "relación", "novio", "novia", "chiste", "broma", "cuento"
        };
        for (String fuera : fueraDeContexto) {
            if (q.contains(fuera)) return true;
        }
        return false;
    }

    private boolean contieneAlguna(String texto, String... palabras) {
        for (String p : palabras) {
            if (texto.contains(p)) return true;
        }
        return false;
    }

    private String traducirEtapa(String stage) {
        if (stage == null || stage.isBlank()) return "no especificada";
        return switch (stage.toUpperCase(Locale.ROOT)) {
            case "SEEDLING" -> "Plántula";
            case "VEGETATIVE" -> "Vegetativo";
            case "FLOWERING" -> "Floración";
            case "FRUITING" -> "Fructificación";
            case "HARVEST" -> "Cosecha";
            default -> stage;
        };
    }
}
