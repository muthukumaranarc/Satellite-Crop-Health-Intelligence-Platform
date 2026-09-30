package com.agrisight.alert.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "alerts")
@CompoundIndexes({
        @CompoundIndex(name = "field_alert_status_idx", def = "{'fieldId': 1, 'status': 1}")
})
public class Alert {

    @Id
    private String id;

    @Indexed
    private String fieldId;

    private String type = "POTENTIAL_VEGETATION_DECLINE";

    private AlertSeverity severity = AlertSeverity.HIGH;

    private String title;

    private String description;

    @Indexed
    private Instant detectedAt = Instant.now();

    @Indexed
    private AlertStatus status = AlertStatus.ACTIVE;

    private Instant resolvedAt;

    @CreatedDate
    private Instant createdAt = Instant.now();

    public Alert() {
    }

    public Alert(String fieldId, String type, AlertSeverity severity,
                 String title, String description, Instant detectedAt,
                 AlertStatus status) {
        this.fieldId = fieldId;
        this.type = type;
        this.severity = severity;
        this.title = title;
        this.description = description;
        this.detectedAt = detectedAt != null ? detectedAt : Instant.now();
        this.status = status != null ? status : AlertStatus.ACTIVE;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(Instant detectedAt) {
        this.detectedAt = detectedAt;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Instant resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
