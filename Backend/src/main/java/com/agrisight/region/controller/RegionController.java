package com.agrisight.region.controller;

import com.agrisight.common.ApiResponse;
import com.agrisight.region.dto.CreateRegionRequest;
import com.agrisight.region.dto.RegionDto;
import com.agrisight.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@Tag(name = "Regions", description = "Endpoints for managing agricultural monitoring regions")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    @Operation(summary = "List all registered agricultural regions")
    public ResponseEntity<ApiResponse<List<RegionDto>>> getAllRegions() {
        List<RegionDto> regions = regionService.getAllRegions();
        return ResponseEntity.ok(ApiResponse.success(regions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get region details by ID")
    public ResponseEntity<ApiResponse<RegionDto>> getRegionById(@PathVariable String id) {
        RegionDto region = regionService.getRegionById(id);
        return ResponseEntity.ok(ApiResponse.success(region));
    }

    @PostMapping
    @Operation(summary = "Create and register a new agricultural region")
    public ResponseEntity<ApiResponse<RegionDto>> createRegion(@Valid @RequestBody CreateRegionRequest request) {
        RegionDto region = regionService.createRegion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Region created successfully", region));
    }
}
