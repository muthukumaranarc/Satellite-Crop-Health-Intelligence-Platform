package com.agrisight.gis.controller;

import com.agrisight.gis.dto.GeoJsonFeatureCollection;
import com.agrisight.gis.service.GisService;
import com.agrisight.vegetation.entity.HealthClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gis")
@Tag(name = "GIS", description = "Endpoints for GIS-ready GeoJSON feature collections for interactive map visualization")
public class GisController {

    private final GisService gisService;

    public GisController(GisService gisService) {
        this.gisService = gisService;
    }

    @GetMapping(value = "/fields", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get fields as RFC 7946 GeoJSON FeatureCollection with crop and health properties")
    public ResponseEntity<GeoJsonFeatureCollection> getFieldGeoJson(
            @RequestParam(required = false) String regionId,
            @RequestParam(required = false) HealthClass healthClass) {
        GeoJsonFeatureCollection collection = gisService.getFieldFeatures(regionId, healthClass);
        return ResponseEntity.ok(collection);
    }

    @GetMapping(value = "/health-map", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get styled GIS health map layers with hex color indicators (Green, Yellow, Red, Gray)")
    public ResponseEntity<GeoJsonFeatureCollection> getHealthMapGeoJson(
            @RequestParam(required = false) String regionId,
            @RequestParam(required = false) HealthClass healthClass) {
        GeoJsonFeatureCollection collection = gisService.getFieldFeatures(regionId, healthClass);
        return ResponseEntity.ok(collection);
    }
}
