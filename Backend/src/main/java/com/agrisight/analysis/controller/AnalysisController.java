package com.agrisight.analysis.controller;

import com.agrisight.analysis.dto.AnalysisResponseDto;
import com.agrisight.analysis.dto.TriggerAnalysisRequest;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.analysis.service.HealthAnalysisService;
import com.agrisight.common.ApiResponse;
import com.agrisight.exception.ResourceNotFoundException;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.ml.client.MlServiceClient;
import com.agrisight.ml.dto.MlProcessingRequest;
import com.agrisight.ml.dto.MlProcessingResponse;
import com.agrisight.common.util.GeoJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/analysis")
@Tag(name = "Analysis", description = "Endpoints for triggering satellite crop health analyses and stress detection")
public class AnalysisController {

    private final HealthAnalysisService healthAnalysisService;
    private final HealthAnalysisRepository healthAnalysisRepository;
    private final FieldRepository fieldRepository;
    private final MlServiceClient mlServiceClient;

    public AnalysisController(HealthAnalysisService healthAnalysisService,
                              HealthAnalysisRepository healthAnalysisRepository,
                              FieldRepository fieldRepository,
                              MlServiceClient mlServiceClient) {
        this.healthAnalysisService = healthAnalysisService;
        this.healthAnalysisRepository = healthAnalysisRepository;
        this.fieldRepository = fieldRepository;
        this.mlServiceClient = mlServiceClient;
    }

    @PostMapping
    @Operation(summary = "Trigger on-demand crop health analysis for an agricultural field")
    public ResponseEntity<ApiResponse<AnalysisResponseDto>> triggerAnalysis(@Valid @RequestBody TriggerAnalysisRequest request) {
        Field field = fieldRepository.findById(request.getFieldId())
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + request.getFieldId()));

        // Check if external ML service can provide augmented raster analysis
        MlProcessingRequest mlReq = new MlProcessingRequest(
                field.getId(),
                GeoJsonUtils.extractCoordinates(field.getBoundary()),
                field.getCropType(),
                request.getStartDate(),
                request.getEndDate(),
                request.getIndices() != null ? request.getIndices() : List.of("NDVI", "NDRE", "NDMI")
        );

        Optional<MlProcessingResponse> mlResponse = mlServiceClient.processField(mlReq);
        HealthAnalysis analysis = healthAnalysisService.analyzeFieldHealth(field);

        if (mlResponse.isPresent()) {
            MlProcessingResponse res = mlResponse.get();
            analysis.setAnalysisMethod("HYBRID_FASTAPI_SENTINEL2_" + (res.getModelVersion() != null ? res.getModelVersion() : "V1"));
            analysis.setConfidence(Math.max(analysis.getConfidence(), res.getConfidence()));
            analysis = healthAnalysisRepository.save(analysis);
        }

        return ResponseEntity.ok(ApiResponse.success("Health analysis completed successfully", AnalysisResponseDto.from(analysis)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get health analysis record by ID")
    public ResponseEntity<ApiResponse<AnalysisResponseDto>> getAnalysisById(@PathVariable String id) {
        HealthAnalysis analysis = healthAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Health analysis record not found with ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(AnalysisResponseDto.from(analysis)));
    }

    @GetMapping
    @Operation(summary = "Get historical health analysis runs for a field")
    public ResponseEntity<ApiResponse<List<AnalysisResponseDto>>> getAnalysesByField(
            @RequestParam String fieldId) {
        List<AnalysisResponseDto> dtos = healthAnalysisRepository.findByFieldIdOrderByAnalysisDateDesc(fieldId).stream()
                .map(AnalysisResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
}
