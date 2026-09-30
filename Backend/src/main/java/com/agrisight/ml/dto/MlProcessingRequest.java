package com.agrisight.ml.dto;

import java.time.LocalDate;
import java.util.List;

public class MlProcessingRequest {

    private String fieldId;
    private List<List<Double>> coordinates;
    private String cropType;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> indices;

    public MlProcessingRequest() {
    }

    public MlProcessingRequest(String fieldId, List<List<Double>> coordinates, String cropType,
                                LocalDate startDate, LocalDate endDate, List<String> indices) {
        this.fieldId = fieldId;
        this.coordinates = coordinates;
        this.cropType = cropType;
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

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
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
