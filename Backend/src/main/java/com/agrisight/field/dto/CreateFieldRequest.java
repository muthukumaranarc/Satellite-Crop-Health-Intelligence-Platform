package com.agrisight.field.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateFieldRequest {

    @NotBlank(message = "Region ID is required")
    private String regionId;

    @NotBlank(message = "Field name is required")
    private String name;

    @NotBlank(message = "Field code is required")
    private String code;

    @NotBlank(message = "Crop type is required")
    private String cropType;

    @NotEmpty(message = "Polygon coordinates are required")
    private List<List<Double>> coordinates;

    public CreateFieldRequest() {
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

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }
}
