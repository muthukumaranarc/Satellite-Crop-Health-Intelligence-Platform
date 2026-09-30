package com.agrisight.field.dto;

import com.agrisight.vegetation.entity.HealthClass;

import java.time.LocalDate;

public class FieldHealthSummaryDto {

    private String fieldId;
    private String fieldName;
    private String cropType;
    private double areaHectares;

    private HealthClass healthClass;
    private String healthColor;
    private double currentNdvi;
    private double previousNdvi;
    private double ndviChangePercent;
    private double currentNdre;
    private double currentNdmi;

    private double stressScore;
    private double confidence;
    private LocalDate observationDate;
    private String analysisMethod;
    private String explanation;
    private long activeAlertCount;

    public FieldHealthSummaryDto() {
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public double getAreaHectares() {
        return areaHectares;
    }

    public void setAreaHectares(double areaHectares) {
        this.areaHectares = areaHectares;
    }

    public HealthClass getHealthClass() {
        return healthClass;
    }

    public void setHealthClass(HealthClass healthClass) {
        this.healthClass = healthClass;
        this.healthColor = (healthClass != null) ? healthClass.getColorHex() : "#94a3b8";
    }

    public String getHealthColor() {
        return healthColor;
    }

    public void setHealthColor(String healthColor) {
        this.healthColor = healthColor;
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

    public LocalDate getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDate observationDate) {
        this.observationDate = observationDate;
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

    public long getActiveAlertCount() {
        return activeAlertCount;
    }

    public void setActiveAlertCount(long activeAlertCount) {
        this.activeAlertCount = activeAlertCount;
    }
}
