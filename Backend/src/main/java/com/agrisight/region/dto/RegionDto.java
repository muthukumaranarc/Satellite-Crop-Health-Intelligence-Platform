package com.agrisight.region.dto;

import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.region.entity.Region;

import java.time.Instant;
import java.util.List;

public class RegionDto {
    private String id;
    private String name;
    private String code;
    private String description;
    private List<List<Double>> coordinates;
    private double centerLatitude;
    private double centerLongitude;
    private double totalAreaHectares;
    private long fieldCount;
    private Instant createdAt;

    public RegionDto() {
    }

    public static RegionDto from(Region region, long fieldCount) {
        RegionDto dto = new RegionDto();
        dto.setId(region.getId());
        dto.setName(region.getName());
        dto.setCode(region.getCode());
        dto.setDescription(region.getDescription());
        dto.setCoordinates(GeoJsonUtils.extractCoordinates(region.getBoundary()));
        dto.setCenterLatitude(region.getCenterLatitude());
        dto.setCenterLongitude(region.getCenterLongitude());
        dto.setTotalAreaHectares(region.getTotalAreaHectares());
        dto.setFieldCount(fieldCount);
        dto.setCreatedAt(region.getCreatedAt());
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getTotalAreaHectares() {
        return totalAreaHectares;
    }

    public void setTotalAreaHectares(double totalAreaHectares) {
        this.totalAreaHectares = totalAreaHectares;
    }

    public long getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(long fieldCount) {
        this.fieldCount = fieldCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
