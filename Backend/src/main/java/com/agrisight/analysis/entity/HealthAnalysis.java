package com.agrisight.analysis.entity;

import com.agrisight.vegetation.entity.HealthClass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Document(collection = "health_analyses")
@CompoundIndexes({
        @CompoundIndex(name = "field_analysis_date_idx", def = "{'fieldId': 1, 'analysisDate': -1}")
})
public class HealthAnalysis {

    @Id
    private String id;

    @Indexed
    private String fieldId;

    private LocalDate analysisDate;

    private double currentNdvi;
    private double previousNdvi;
    private double ndviChangePercent;

    private double currentNdre;
    private double currentNdmi;

    private HealthClass healthClass = HealthClass.HEALTHY;

    private double stressScore; // 0.00 to 1.00
    private double confidence;  // 0.00 to 1.00

    private String analysisMethod = "TEMPORAL_THRESHOLD_V1";

    private String explanation;

    @CreatedDate
    private Instant createdAt = Instant.now();

    public HealthAnalysis() {
    }

    public HealthAnalysis(String fieldId, LocalDate analysisDate,
                          double currentNdvi, double previousNdvi, double ndviChangePercent,
                          double currentNdre, double currentNdmi,
                          HealthClass healthClass, double stressScore, double confidence,
                          String analysisMethod, String explanation) {
        this.fieldId = fieldId;
        this.analysisDate = analysisDate;
        this.currentNdvi = currentNdvi;
        this.previousNdvi = previousNdvi;
        this.ndviChangePercent = ndviChangePercent;
        this.currentNdre = currentNdre;
        this.currentNdmi = currentNdmi;
        this.healthClass = healthClass;
        this.stressScore = stressScore;
        this.confidence = confidence;
        this.analysisMethod = analysisMethod;
        this.explanation = explanation;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public LocalDate getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(LocalDate analysisDate) {
        this.analysisDate = analysisDate;
    }

    public double getCurrentNdvi() {
        return currentNdvi;
    }

    public void setCurrentNdvi(double currentNdvi) {
        this.currentNdvi = currentNdvi;
    }

    public double getPreviousNdvi() {
        return previousNdvi;
    }

    public void setPreviousNdvi(double previousNdvi) {
        this.previousNdvi = previousNdvi;
    }

    public double getNdviChangePercent() {
        return ndviChangePercent;
    }

    public void setNdviChangePercent(double ndviChangePercent) {
        this.ndviChangePercent = ndviChangePercent;
    }

    public double getCurrentNdre() {
        return currentNdre;
    }

    public void setCurrentNdre(double currentNdre) {
        this.currentNdre = currentNdre;
    }

    public double getCurrentNdmi() {
        return currentNdmi;
    }

    public void setCurrentNdmi(double currentNdmi) {
        this.currentNdmi = currentNdmi;
    }

    public HealthClass getHealthClass() {
        return healthClass;
    }

    public void setHealthClass(HealthClass healthClass) {
        this.healthClass = healthClass;
    }

    public double getStressScore() {
        return stressScore;
    }

    public void setStressScore(double stressScore) {
        this.stressScore = stressScore;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getAnalysisMethod() {
        return analysisMethod;
    }

    public void setAnalysisMethod(String analysisMethod) {
        this.analysisMethod = analysisMethod;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
