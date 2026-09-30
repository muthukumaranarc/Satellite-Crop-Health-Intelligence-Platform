package com.agrisight.region.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "regions")
public class Region {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed(unique = true)
    private String code;

    private String description;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPolygon boundary;

    private double centerLatitude;
    private double centerLongitude;
    private double totalAreaHectares;

    @CreatedDate
    private Instant createdAt = Instant.now();

    public Region() {
    }

    public Region(String name, String code, String description, GeoJsonPolygon boundary,
                  double centerLatitude, double centerLongitude, double totalAreaHectares) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.boundary = boundary;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.totalAreaHectares = totalAreaHectares;
        this.createdAt = Instant.now();
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

    public GeoJsonPolygon getBoundary() {
        return boundary;
    }

    public void setBoundary(GeoJsonPolygon boundary) {
        this.boundary = boundary;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
