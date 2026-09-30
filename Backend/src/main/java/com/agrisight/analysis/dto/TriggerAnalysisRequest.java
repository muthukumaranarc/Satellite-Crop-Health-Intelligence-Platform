package com.agrisight.analysis.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public class TriggerAnalysisRequest {

    @NotBlank(message = "Field ID is required")
    private String fieldId;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> indices; // e.g. ["NDVI", "NDRE", "NDMI"]

    public TriggerAnalysisRequest() {
    }

    public TriggerAnalysisRequest(String fieldId, LocalDate startDate, LocalDate endDate, List<String> indices) {
        this.fieldId = fieldId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.indices = indices;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getIndices() {
        return indices;
    }

    public void setIndices(List<String> indices) {
        this.indices = indices;
    }
}
