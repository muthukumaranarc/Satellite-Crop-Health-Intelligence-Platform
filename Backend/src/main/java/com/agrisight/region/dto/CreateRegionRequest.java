package com.agrisight.region.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateRegionRequest {

    @NotBlank(message = "Region name is required")
    private String name;

    @NotBlank(message = "Region code is required")
    private String code;

    private String description;

    @NotEmpty(message = "Boundary polygon coordinates are required")
    private List<List<Double>> coordinates;

    public CreateRegionRequest() {
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
}
