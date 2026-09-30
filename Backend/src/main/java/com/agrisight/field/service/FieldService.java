package com.agrisight.field.service;

import com.agrisight.alert.entity.AlertStatus;
import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.analysis.service.HealthAnalysisService;
import com.agrisight.common.PageResponse;
import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.exception.BadRequestException;
import com.agrisight.exception.ResourceNotFoundException;
import com.agrisight.field.dto.CreateFieldRequest;
import com.agrisight.field.dto.FieldDto;
import com.agrisight.field.dto.FieldHealthSummaryDto;
import com.agrisight.field.dto.FieldHistoryDto;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.region.repository.RegionRepository;
import com.agrisight.vegetation.entity.HealthClass;
import com.agrisight.vegetation.entity.VegetationObservation;
import com.agrisight.vegetation.repository.VegetationObservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final RegionRepository regionRepository;
    private final VegetationObservationRepository observationRepository;
    private final HealthAnalysisRepository healthAnalysisRepository;
    private final HealthAnalysisService healthAnalysisService;
    private final AlertRepository alertRepository;

    public FieldService(FieldRepository fieldRepository,
                        RegionRepository regionRepository,
                        VegetationObservationRepository observationRepository,
                        HealthAnalysisRepository healthAnalysisRepository,
                        HealthAnalysisService healthAnalysisService,
                        AlertRepository alertRepository) {
        this.fieldRepository = fieldRepository;
        this.regionRepository = regionRepository;
        this.observationRepository = observationRepository;
        this.healthAnalysisRepository = healthAnalysisRepository;
        this.healthAnalysisService = healthAnalysisService;
        this.alertRepository = alertRepository;
    }

    public PageResponse<FieldDto> getFields(String regionId, String cropType, HealthClass healthClass, Pageable pageable) {
        Page<Field> page;
        if (regionId != null && !regionId.isBlank()) {
            page = fieldRepository.findByRegionId(regionId, pageable);
        } else {
            page = fieldRepository.findAll(pageable);
        }

        List<FieldDto> dtos = page.getContent().stream()
                .map(this::enrichFieldDto)
                .filter(dto -> cropType == null || cropType.isBlank() || cropType.equalsIgnoreCase(dto.getCropType()))
                .filter(dto -> healthClass == null || dto.getLatestHealthClass() == healthClass)
                .collect(Collectors.toList());

        return PageResponse.from(new PageImpl<>(dtos, pageable, page.getTotalElements()));
    }

    public FieldDto getFieldById(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + id));
        return enrichFieldDto(field);
    }

    public FieldDto createField(CreateFieldRequest request) {
        if (!regionRepository.existsById(request.getRegionId())) {
            throw new ResourceNotFoundException("Region not found with ID: " + request.getRegionId());
        }
        if (fieldRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Field code already exists: " + request.getCode());
        }

        GeoJsonPolygon polygon = GeoJsonUtils.createPolygon(request.getCoordinates());
        Point centroid = GeoJsonUtils.calculateCentroid(polygon);
        double areaHectares = GeoJsonUtils.calculateAreaHectares(polygon);

        Field field = new Field(
                request.getRegionId(),
                request.getName(),
                request.getCode(),
                request.getCropType(),
                areaHectares,
                polygon,
                centroid.getY(),
                centroid.getX()
        );

        field = fieldRepository.save(field);
        return FieldDto.from(field);
    }

    public FieldHealthSummaryDto getFieldHealth(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + id));

        Optional<HealthAnalysis> latestAnalysis = healthAnalysisRepository.findFirstByFieldIdOrderByAnalysisDateDesc(id);
        HealthAnalysis analysis = latestAnalysis.orElseGet(() -> healthAnalysisService.analyzeFieldHealth(field));

        long activeAlerts = alertRepository.findByFieldIdAndStatus(id, AlertStatus.ACTIVE).size();

        FieldHealthSummaryDto dto = new FieldHealthSummaryDto();
        dto.setFieldId(field.getId());
        dto.setFieldName(field.getName());
        dto.setCropType(field.getCropType());
        dto.setAreaHectares(field.getAreaHectares());
        dto.setHealthClass(analysis.getHealthClass());
        dto.setCurrentNdvi(analysis.getCurrentNdvi());
        dto.setPreviousNdvi(analysis.getPreviousNdvi());
        dto.setNdviChangePercent(analysis.getNdviChangePercent());
        dto.setCurrentNdre(analysis.getCurrentNdre());
        dto.setCurrentNdmi(analysis.getCurrentNdmi());
        dto.setStressScore(analysis.getStressScore());
        dto.setConfidence(analysis.getConfidence());
        dto.setObservationDate(analysis.getAnalysisDate());
        dto.setAnalysisMethod(analysis.getAnalysisMethod());
        dto.setExplanation(analysis.getExplanation());
        dto.setActiveAlertCount(activeAlerts);

        return dto;
    }

    public FieldHistoryDto getFieldHistory(String id, LocalDate startDate, LocalDate endDate) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + id));

        List<VegetationObservation> observations;
        if (startDate != null && endDate != null) {
            observations = observationRepository.findByFieldIdAndObservationDateBetweenOrderByObservationDateAsc(id, startDate, endDate);
        } else {
            observations = observationRepository.findByFieldIdOrderByObservationDateAsc(id);
        }

        List<FieldHistoryDto.ObservationPoint> points = observations.stream()
                .map(obs -> new FieldHistoryDto.ObservationPoint(
                        obs.getId(),
                        obs.getObservationDate(),
                        obs.getNdvi(),
                        obs.getNdre(),
                        obs.getNdmi(),
                        obs.getValidPixelPercentage(),
                        obs.getDataQuality(),
                        obs.getHealthClass()
                ))
                .collect(Collectors.toList());

        FieldHistoryDto dto = new FieldHistoryDto();
        dto.setFieldId(field.getId());
        dto.setFieldName(field.getName());
        dto.setCropType(field.getCropType());
        dto.setObservations(points);
        return dto;
    }

    public List<List<Double>> getFieldGeometry(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + id));
        return GeoJsonUtils.extractCoordinates(field.getBoundary());
    }

    private FieldDto enrichFieldDto(Field field) {
        FieldDto dto = FieldDto.from(field);

        Optional<HealthAnalysis> latest = healthAnalysisRepository.findFirstByFieldIdOrderByAnalysisDateDesc(field.getId());
        if (latest.isPresent()) {
            HealthAnalysis ha = latest.get();
            dto.setLatestHealthClass(ha.getHealthClass());
            dto.setLatestNdvi(ha.getCurrentNdvi());
            dto.setLatestNdre(ha.getCurrentNdre());
            dto.setLatestNdmi(ha.getCurrentNdmi());
            dto.setStressScore(ha.getStressScore());
            dto.setLatestObservationDate(ha.getAnalysisDate());
        } else {
            Optional<VegetationObservation> obs = observationRepository.findFirstByFieldIdOrderByObservationDateDesc(field.getId());
            if (obs.isPresent()) {
                VegetationObservation vo = obs.get();
                dto.setLatestHealthClass(vo.getHealthClass());
                dto.setLatestNdvi(vo.getNdvi());
                dto.setLatestNdre(vo.getNdre());
                dto.setLatestNdmi(vo.getNdmi());
                dto.setLatestObservationDate(vo.getObservationDate());
            } else {
                dto.setLatestHealthClass(HealthClass.INSUFFICIENT_DATA);
            }
        }

        long activeAlerts = alertRepository.findByFieldIdAndStatus(field.getId(), AlertStatus.ACTIVE).size();
        dto.setActiveAlertCount(activeAlerts);
        return dto;
    }
}
