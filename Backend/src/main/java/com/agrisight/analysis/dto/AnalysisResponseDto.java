package com.agrisight.analysis.dto;

import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.vegetation.entity.HealthClass;

import java.time.Instant;
import java.time.LocalDate;

public class AnalysisResponseDto {

    private String id;
    private String fieldId;
    private LocalDate analysisDate;
    private double currentNdvi;
    private double previousNdvi;
    private double ndviChangePercent;
    private double currentNdre;
    private double currentNdmi;
    private HealthClass healthClass;
    private double stressScore;
    private double confidence;
    private String analysisMethod;
    private String explanation;
    private Instant createdAt;

    public AnalysisResponseDto() {
    }

    public static AnalysisResponseDto from(HealthAnalysis ha) {
        AnalysisResponseDto dto = new AnalysisResponseDto();
        dto.setId(ha.getId());
        dto.setFieldId(ha.getFieldId());
        dto.setAnalysisDate(ha.getAnalysisDate());
        dto.setCurrentNdvi(ha.getCurrentNdvi());
        dto.setPreviousNdvi(ha.getPreviousNdvi());
        dto.setNdviChangePercent(ha.getNdviChangePercent());
        dto.setCurrentNdre(ha.getCurrentNdre());
        dto.setCurrentNdmi(ha.getCurrentNdmi());
        dto.setHealthClass(ha.getHealthClass());
        dto.setStressScore(ha.getStressScore());
        dto.setConfidence(ha.getConfidence());
        dto.setAnalysisMethod(ha.getAnalysisMethod());
        dto.setExplanation(ha.getExplanation());
        dto.setCreatedAt(ha.getCreatedAt());
        return dto;
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
