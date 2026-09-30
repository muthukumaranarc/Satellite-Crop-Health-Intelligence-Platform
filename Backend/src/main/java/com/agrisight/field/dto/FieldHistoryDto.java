package com.agrisight.field.dto;

import com.agrisight.vegetation.entity.DataQuality;
import com.agrisight.vegetation.entity.HealthClass;

import java.time.LocalDate;
import java.util.List;

public class FieldHistoryDto {

    private String fieldId;
    private String fieldName;
    private String cropType;
    private List<ObservationPoint> observations;

    public FieldHistoryDto() {
    }

    public static class ObservationPoint {
        private String id;
        private LocalDate date;
        private double ndvi;
        private double ndre;
        private double ndmi;
        private double validPixelPercentage;
        private DataQuality dataQuality;
        private HealthClass healthClass;

        public ObservationPoint() {
        }

        public ObservationPoint(String id, LocalDate date, double ndvi, double ndre, double ndmi,
                                double validPixelPercentage, DataQuality dataQuality, HealthClass healthClass) {
            this.id = id;
            this.date = date;
            this.ndvi = ndvi;
            this.ndre = ndre;
            this.ndmi = ndmi;
            this.validPixelPercentage = validPixelPercentage;
            this.dataQuality = dataQuality;
            this.healthClass = healthClass;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public DataQuality getDataQuality() {
            return dataQuality;
        }

        public void setDataQuality(DataQuality dataQuality) {
            this.dataQuality = dataQuality;
        }

        public HealthClass getHealthClass() {
            return healthClass;
        }

        public void setHealthClass(HealthClass healthClass) {
            this.healthClass = healthClass;
        }
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

    public List<ObservationPoint> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationPoint> observations) {
        this.observations = observations;
    }
}
