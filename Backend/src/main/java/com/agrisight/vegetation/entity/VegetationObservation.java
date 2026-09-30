package com.agrisight.vegetation.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Document(collection = "vegetation_observations")
@CompoundIndexes({
        @CompoundIndex(name = "field_obs_date_idx", def = "{'fieldId': 1, 'observationDate': -1}")
})
public class VegetationObservation {

    @Id
    private String id;

    @Indexed
    private String fieldId;

    private String sceneId;

    @Indexed
    private LocalDate observationDate;

    private double ndvi;
    private double ndre;
    private double ndmi;

    private double validPixelPercentage;

    private DataQuality dataQuality = DataQuality.HIGH;

    private HealthClass healthClass = HealthClass.HEALTHY;

    @CreatedDate
    private Instant createdAt = Instant.now();

    public VegetationObservation() {
    }

    public VegetationObservation(String fieldId, String sceneId, LocalDate observationDate,
                                 double ndvi, double ndre, double ndmi,
                                 double validPixelPercentage, DataQuality dataQuality,
                                 HealthClass healthClass) {
        this.fieldId = fieldId;
        this.sceneId = sceneId;
        this.observationDate = observationDate;
        this.ndvi = ndvi;
        this.ndre = ndre;
        this.ndmi = ndmi;
        this.validPixelPercentage = validPixelPercentage;
        this.dataQuality = dataQuality;
        this.healthClass = healthClass;
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

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public LocalDate getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDate observationDate) {
        this.observationDate = observationDate;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
