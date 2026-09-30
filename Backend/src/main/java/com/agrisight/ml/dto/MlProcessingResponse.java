package com.agrisight.ml.dto;

import java.time.LocalDate;
import java.util.List;

public class MlProcessingResponse {

    private String fieldId;
    private List<ObservationItem> observations;
    private String healthClass;
    private double stressScore;
    private double confidence;
    private String modelVersion;

    public MlProcessingResponse() {
    }

    public static class ObservationItem {
        private LocalDate date;
        private double ndvi;
        private double ndre;
        private double ndmi;
        private double validPixelPercentage;

        public ObservationItem() {
        }

        public ObservationItem(LocalDate date, double ndvi, double ndre, double ndmi, double validPixelPercentage) {
            this.date = date;
            this.ndvi = ndvi;
            this.ndre = ndre;
            this.ndmi = ndmi;
            this.validPixelPercentage = validPixelPercentage;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public double getNdvi() {
            return ndvi;
        }

        public void setNdvi(double ndvi) {
            this.ndvi = ndvi;
        }

        public double getNdre() {
            return ndre;
        }

        public void setNdre(double ndre) {
            this.ndre = ndre;
        }

        public double getNdmi() {
            return ndmi;
        }

        public void setNdmi(double ndmi) {
            this.ndmi = ndmi;
        }

        public double getValidPixelPercentage() {
            return validPixelPercentage;
        }

        public void setValidPixelPercentage(double validPixelPercentage) {
            this.validPixelPercentage = validPixelPercentage;
        }
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public List<ObservationItem> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationItem> observations) {
        this.observations = observations;
    }

    public String getHealthClass() {
        return healthClass;
    }

    public void setHealthClass(String healthClass) {
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

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
}
