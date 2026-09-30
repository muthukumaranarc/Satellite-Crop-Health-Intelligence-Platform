package com.agrisight.dashboard.controller;

import com.agrisight.common.ApiResponse;
import com.agrisight.dashboard.dto.DashboardSummaryDto;
import com.agrisight.dashboard.dto.HealthDistributionDto;
import com.agrisight.dashboard.dto.TrendPointDto;
import com.agrisight.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Endpoints for crop health metrics, KPI summaries, and historical trends")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Get high-level agricultural health summary and KPIs")
    public ResponseEntity<ApiResponse<DashboardSummaryDto>> getSummary() {
        DashboardSummaryDto summary = dashboardService.getSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @GetMapping("/health-distribution")
    @Operation(summary = "Get breakdown of crop health classes by crop type and region")
    public ResponseEntity<ApiResponse<HealthDistributionDto>> getHealthDistribution() {
        HealthDistributionDto distribution = dashboardService.getHealthDistribution();
        return ResponseEntity.ok(ApiResponse.success(distribution));
    }

    @GetMapping("/trends")
    @Operation(summary = "Get time-series trends for vegetation indices (NDVI, NDRE, NDMI)")
    public ResponseEntity<ApiResponse<List<TrendPointDto>>> getTrends(
            @RequestParam(defaultValue = "90") int days) {
        List<TrendPointDto> trends = dashboardService.getTrends(days);
        return ResponseEntity.ok(ApiResponse.success(trends));
    }
}
