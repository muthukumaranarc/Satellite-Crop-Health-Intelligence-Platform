package com.agrisight.field.dto;

import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.field.entity.Field;
import com.agrisight.vegetation.entity.HealthClass;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class FieldDto {
    private String id;
    private String regionId;
    private String name;
    private String code;
    private String cropType;
    private double areaHectares;
    private List<List<Double>> coordinates;
    private double centerLatitude;
    private double centerLongitude;

    // Latest health indicator fields
    private HealthClass latestHealthClass;
    private Double latestNdvi;
    private Double latestNdre;
    private Double latestNdmi;
    private Double stressScore;
    private LocalDate latestObservationDate;
    private long activeAlertCount;

    private Instant createdAt;

    public FieldDto() {
    }

    public static FieldDto from(Field field) {
        FieldDto dto = new FieldDto();
        dto.setId(field.getId());
        dto.setRegionId(field.getRegionId());
        dto.setName(field.getName());
        dto.setCode(field.getCode());
        dto.setCropType(field.getCropType());
        dto.setAreaHectares(field.getAreaHectares());
        dto.setCoordinates(GeoJsonUtils.extractCoordinates(field.getBoundary()));
        dto.setCenterLatitude(field.getCenterLatitude());
        dto.setCenterLongitude(field.getCenterLongitude());
        dto.setCreatedAt(field.getCreatedAt());
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public HealthClass getLatestHealthClass() {
        return latestHealthClass;
    }

    public void setLatestHealthClass(HealthClass latestHealthClass) {
        this.latestHealthClass = latestHealthClass;
    }

    public Double getLatestNdvi() {
        return latestNdvi;
    }

    public void setLatestNdvi(Double latestNdvi) {
        this.latestNdvi = latestNdvi;
    }

    public Double getLatestNdre() {
        return latestNdre;
    }

    public void setLatestNdre(Double latestNdre) {
        this.latestNdre = latestNdre;
    }

    public Double getLatestNdmi() {
        return latestNdmi;
    }

    public void setLatestNdmi(Double latestNdmi) {
        this.latestNdmi = latestNdmi;
    }

    public Double getStressScore() {
        return stressScore;
    }

    public void setStressScore(Double stressScore) {
        this.stressScore = stressScore;
    }

    public LocalDate getLatestObservationDate() {
        return latestObservationDate;
    }

    public void setLatestObservationDate(LocalDate latestObservationDate) {
        this.latestObservationDate = latestObservationDate;
    }

    public long getActiveAlertCount() {
        return activeAlertCount;
    }

    public void setActiveAlertCount(long activeAlertCount) {
        this.activeAlertCount = activeAlertCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
