package com.agrisight.alert.dto;

import com.agrisight.alert.entity.Alert;
import com.agrisight.alert.entity.AlertSeverity;
import com.agrisight.alert.entity.AlertStatus;

import java.time.Instant;

public class AlertDto {

    private String id;
    private String fieldId;
    private String fieldName;
    private String fieldCode;
    private String type;
    private AlertSeverity severity;
    private String title;
    private String description;
    private Instant detectedAt;
    private AlertStatus status;
    private Instant resolvedAt;
    private Instant createdAt;

    public AlertDto() {
    }

    public static AlertDto from(Alert alert, String fieldName, String fieldCode) {
        AlertDto dto = new AlertDto();
        dto.setId(alert.getId());
        dto.setFieldId(alert.getFieldId());
        dto.setFieldName(fieldName);
        dto.setFieldCode(fieldCode);
        dto.setType(alert.getType());
        dto.setSeverity(alert.getSeverity());
        dto.setTitle(alert.getTitle());
        dto.setDescription(alert.getDescription());
        dto.setDetectedAt(alert.getDetectedAt());
        dto.setStatus(alert.getStatus());
        dto.setResolvedAt(alert.getResolvedAt());
        dto.setCreatedAt(alert.getCreatedAt());
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
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
