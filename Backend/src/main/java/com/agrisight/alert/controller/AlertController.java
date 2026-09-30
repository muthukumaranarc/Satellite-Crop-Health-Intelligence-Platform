package com.agrisight.alert.controller;

import com.agrisight.alert.dto.AlertDto;
import com.agrisight.alert.dto.UpdateAlertStatusRequest;
import com.agrisight.alert.entity.AlertSeverity;
import com.agrisight.alert.entity.AlertStatus;
import com.agrisight.alert.service.AlertService;
import com.agrisight.common.ApiResponse;
import com.agrisight.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts", description = "Endpoints for managing vegetation decline and crop stress alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    @Operation(summary = "Get list of alerts with filtering and pagination")
    public ResponseEntity<ApiResponse<PageResponse<AlertDto>>> getAlerts(
            @RequestParam(required = false) AlertStatus status,
            @RequestParam(required = false) AlertSeverity severity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "detectedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<AlertDto> response = alertService.getAlerts(status, severity, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert details by ID")
    public ResponseEntity<ApiResponse<AlertDto>> getAlertById(@PathVariable String id) {
        AlertDto alert = alertService.getAlertById(id);
        return ResponseEntity.ok(ApiResponse.success(alert));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update alert lifecycle status (ACKNOWLEDGED, RESOLVED, FALSE_POSITIVE)")
    public ResponseEntity<ApiResponse<AlertDto>> updateAlertStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateAlertStatusRequest request) {
        AlertDto alert = alertService.updateAlertStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Alert status updated successfully", alert));
    }
}
