package com.agrisight.field.controller;

import com.agrisight.common.ApiResponse;
import com.agrisight.common.PageResponse;
import com.agrisight.field.dto.CreateFieldRequest;
import com.agrisight.field.dto.FieldDto;
import com.agrisight.field.dto.FieldHealthSummaryDto;
import com.agrisight.field.dto.FieldHistoryDto;
import com.agrisight.field.service.FieldService;
import com.agrisight.vegetation.entity.HealthClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fields")
@Tag(name = "Fields", description = "Endpoints for agricultural field boundaries, health indicators, and history")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    @Operation(summary = "Query fields with filtering and pagination")
    public ResponseEntity<ApiResponse<PageResponse<FieldDto>>> getFields(
            @RequestParam(required = false) String regionId,
            @RequestParam(required = false) String cropType,
            @RequestParam(required = false) HealthClass healthClass,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<FieldDto> response = fieldService.getFields(regionId, cropType, healthClass, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get field details and latest health metrics by ID")
    public ResponseEntity<ApiResponse<FieldDto>> getFieldById(@PathVariable String id) {
        FieldDto field = fieldService.getFieldById(id);
        return ResponseEntity.ok(ApiResponse.success(field));
    }

    @PostMapping
    @Operation(summary = "Register new agricultural field boundary")
    public ResponseEntity<ApiResponse<FieldDto>> createField(@Valid @RequestBody CreateFieldRequest request) {
        FieldDto field = fieldService.createField(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Field registered successfully", field));
    }

    @GetMapping("/{id}/health")
    @Operation(summary = "Get current crop health, stress score, and explanation for field")
    public ResponseEntity<ApiResponse<FieldHealthSummaryDto>> getFieldHealth(@PathVariable String id) {
        FieldHealthSummaryDto health = fieldService.getFieldHealth(id);
        return ResponseEntity.ok(ApiResponse.success(health));
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Get multi-temporal vegetation index time-series (NDVI, NDRE, NDMI) for field")
    public ResponseEntity<ApiResponse<FieldHistoryDto>> getFieldHistory(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        FieldHistoryDto history = fieldService.getFieldHistory(id, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/{id}/geometry")
    @Operation(summary = "Get GeoJSON polygon coordinates for field")
    public ResponseEntity<ApiResponse<List<List<Double>>>> getFieldGeometry(@PathVariable String id) {
        List<List<Double>> geometry = fieldService.getFieldGeometry(id);
        return ResponseEntity.ok(ApiResponse.success(geometry));
    }
}
