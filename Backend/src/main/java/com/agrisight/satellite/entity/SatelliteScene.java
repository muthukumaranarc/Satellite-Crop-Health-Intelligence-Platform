package com.agrisight.satellite.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "satellite_scenes")
public class SatelliteScene {

    @Id
    private String id;

    private String provider = "SENTINEL_2";

    @Indexed(unique = true)
    private String sceneId;

    private Instant acquisitionTime;

    private double cloudCover;

    private String bbox;

    private String sourceUrl;

    private ProcessingStatus processingStatus = ProcessingStatus.COMPLETED;

    @CreatedDate
    private Instant createdAt = Instant.now();

    public SatelliteScene() {
    }

    public SatelliteScene(String provider, String sceneId, Instant acquisitionTime,
                          double cloudCover, String bbox, String sourceUrl,
                          ProcessingStatus processingStatus) {
        this.provider = provider;
        this.sceneId = sceneId;
        this.acquisitionTime = acquisitionTime;
        this.cloudCover = cloudCover;
        this.bbox = bbox;
        this.sourceUrl = sourceUrl;
        this.processingStatus = processingStatus;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public Instant getAcquisitionTime() {
        return acquisitionTime;
    }

    public void setAcquisitionTime(Instant acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public ProcessingStatus getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
