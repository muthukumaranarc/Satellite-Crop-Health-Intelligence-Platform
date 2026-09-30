package com.agrisight.analysis.service;

import com.agrisight.alert.entity.Alert;
import com.agrisight.alert.entity.AlertSeverity;
import com.agrisight.alert.entity.AlertStatus;
import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.field.entity.Field;
import com.agrisight.vegetation.entity.HealthClass;
import com.agrisight.vegetation.entity.VegetationObservation;
import com.agrisight.vegetation.repository.VegetationObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class HealthAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(HealthAnalysisService.class);

    private final HealthAnalysisRepository healthAnalysisRepository;
    private final VegetationObservationRepository observationRepository;
    private final AlertRepository alertRepository;

    public HealthAnalysisService(HealthAnalysisRepository healthAnalysisRepository,
                                 VegetationObservationRepository observationRepository,
                                 AlertRepository alertRepository) {
        this.healthAnalysisRepository = healthAnalysisRepository;
        this.observationRepository = observationRepository;
        this.alertRepository = alertRepository;
    }

    /**
     * Executes scientific health analysis comparing recent observations,
     * calculates percentage decline, classifies crop condition, and triggers stress alerts.
     */
    public HealthAnalysis analyzeFieldHealth(Field field) {
        List<VegetationObservation> recent = observationRepository.findFirst2ByFieldIdOrderByObservationDateDesc(field.getId());

        if (recent.isEmpty()) {
            // No observations available
            HealthAnalysis analysis = new HealthAnalysis(
                    field.getId(),
                    LocalDate.now(),
                    0.0, 0.0, 0.0, 0.0, 0.0,
                    HealthClass.INSUFFICIENT_DATA,
                    0.0,
                    0.0,
                    "RULE_ENGINE_V1",
                    "Insufficient satellite observations available to determine vegetation trend."
            );
            return healthAnalysisRepository.save(analysis);
        }

        VegetationObservation current = recent.get(0);
        VegetationObservation previous = (recent.size() > 1) ? recent.get(1) : current;

        double currNdvi = current.getNdvi();
        double prevNdvi = previous.getNdvi();
        double ndviDiff = currNdvi - prevNdvi;
        double changePercent = (prevNdvi > 0) ? (ndviDiff / prevNdvi) * 100.0 : 0.0;
        changePercent = Math.round(changePercent * 100.0) / 100.0;

        // Health Classification & Stress Scoring
        HealthClass healthClass;
        double stressScore;
        double confidence = (current.getValidPixelPercentage() >= 80.0) ? 0.90 : 0.65;
        String explanation;

        if (changePercent <= -25.0 || currNdvi < 0.38) {
            healthClass = HealthClass.POTENTIAL_STRESS;
            stressScore = Math.min(0.95, 0.65 + Math.abs(changePercent) / 100.0);
            explanation = String.format(
                    "Detected significant vegetation decline (NDVI %.2f -> %.2f, change: %.1f%%). " +
                    "Classified as potential crop stress. Field verification is advised to determine agronomical causes.",
                    prevNdvi, currNdvi, changePercent
            );
            triggerStressAlert(field, current.getObservationDate(), changePercent, currNdvi);
        } else if (changePercent <= -12.0 || (currNdvi >= 0.38 && currNdvi < 0.58)) {
            healthClass = HealthClass.MODERATE;
            stressScore = 0.40 + Math.abs(changePercent) / 200.0;
            explanation = String.format(
                    "Vegetation activity is at moderate vigor (NDVI %.2f, change: %.1f%%). " +
                    "Growth parameters are stable, continued monitoring recommended.",
                    currNdvi, changePercent
            );
        } else {
            healthClass = HealthClass.HEALTHY;
            stressScore = Math.max(0.05, 0.25 - (currNdvi / 4.0));
            explanation = String.format(
                    "Vegetation indicators reflect high photosynthetic activity and robust canopy vigor (NDVI %.2f).",
                    currNdvi
            );
        }

        stressScore = Math.round(stressScore * 1000.0) / 1000.0;

        HealthAnalysis analysis = new HealthAnalysis(
                field.getId(),
                current.getObservationDate(),
                currNdvi,
                prevNdvi,
                changePercent,
                current.getNdre(),
                current.getNdmi(),
                healthClass,
                stressScore,
                confidence,
                "TEMPORAL_THRESHOLD_V1",
                explanation
        );

        return healthAnalysisRepository.save(analysis);
    }

    private void triggerStressAlert(Field field, LocalDate date, double changePercent, double ndvi) {
        List<Alert> existingActive = alertRepository.findByFieldIdAndStatus(field.getId(), AlertStatus.ACTIVE);
        if (!existingActive.isEmpty()) {
            return; // Already an active alert for this field
        }

        AlertSeverity severity = (changePercent <= -30.0 || ndvi < 0.30) ? AlertSeverity.HIGH : AlertSeverity.MEDIUM;
        String title = String.format("Vegetation Decline Detected in %s (%s)", field.getName(), field.getCode());
        String description = String.format(
                "Satellite analysis indicates a drop of %.1f%% in NDVI (current: %.2f) on %s. " +
                "Potential crop stress detected. Physical inspection recommended.",
                Math.abs(changePercent), ndvi, date
        );

        Alert alert = new Alert(
                field.getId(),
                "POTENTIAL_VEGETATION_DECLINE",
                severity,
                title,
                description,
                Instant.now(),
                AlertStatus.ACTIVE
        );
        alertRepository.save(alert);
        log.info("Triggered {} alert for field: {}", severity, field.getCode());
    }
}
