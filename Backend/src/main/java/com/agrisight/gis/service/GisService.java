package com.agrisight.gis.service;

import com.agrisight.alert.entity.AlertStatus;
import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.gis.dto.GeoJsonFeature;
import com.agrisight.gis.dto.GeoJsonFeatureCollection;
import com.agrisight.region.entity.Region;
import com.agrisight.region.repository.RegionRepository;
import com.agrisight.vegetation.entity.HealthClass;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GisService {

    private final FieldRepository fieldRepository;
    private final RegionRepository regionRepository;
    private final HealthAnalysisRepository healthAnalysisRepository;
    private final AlertRepository alertRepository;

    public GisService(FieldRepository fieldRepository,
                      RegionRepository regionRepository,
                      HealthAnalysisRepository healthAnalysisRepository,
                      AlertRepository alertRepository) {
        this.fieldRepository = fieldRepository;
        this.regionRepository = regionRepository;
        this.healthAnalysisRepository = healthAnalysisRepository;
        this.alertRepository = alertRepository;
    }

    public GeoJsonFeatureCollection getFieldFeatures(String regionId, HealthClass filterHealthClass) {
        List<Field> fields = (regionId != null && !regionId.isBlank())
                ? fieldRepository.findByRegionId(regionId)
                : fieldRepository.findAll();

        Map<String, String> regionNameMap = regionRepository.findAll().stream()
                .collect(Collectors.toMap(Region::getId, Region::getName, (a, b) -> a));

        List<GeoJsonFeature> features = new ArrayList<>();

        for (Field f : fields) {
            Optional<HealthAnalysis> latestAnalysis = healthAnalysisRepository.findFirstByFieldIdOrderByAnalysisDateDesc(f.getId());

            HealthClass hc = latestAnalysis.map(HealthAnalysis::getHealthClass).orElse(HealthClass.INSUFFICIENT_DATA);
            if (filterHealthClass != null && hc != filterHealthClass) {
                continue;
            }

            Map<String, Object> props = new HashMap<>();
            props.put("id", f.getId());
            props.put("name", f.getName());
            props.put("code", f.getCode());
            props.put("cropType", f.getCropType());
            props.put("areaHectares", f.getAreaHectares());
            props.put("regionId", f.getRegionId());
            props.put("regionName", regionNameMap.getOrDefault(f.getRegionId(), "N/A"));
            props.put("centerLatitude", f.getCenterLatitude());
            props.put("centerLongitude", f.getCenterLongitude());

            props.put("healthClass", hc.name());
            props.put("healthColor", hc.getColorHex());

            if (latestAnalysis.isPresent()) {
                HealthAnalysis ha = latestAnalysis.get();
                props.put("currentNdvi", ha.getCurrentNdvi());
                props.put("previousNdvi", ha.getPreviousNdvi());
                props.put("ndviChangePercent", ha.getNdviChangePercent());
                props.put("currentNdre", ha.getCurrentNdre());
                props.put("currentNdmi", ha.getCurrentNdmi());
                props.put("stressScore", ha.getStressScore());
                props.put("confidence", ha.getConfidence());
                props.put("latestObservationDate", ha.getAnalysisDate());
                props.put("explanation", ha.getExplanation());
            } else {
                props.put("currentNdvi", null);
                props.put("previousNdvi", null);
                props.put("ndviChangePercent", 0.0);
                props.put("stressScore", 0.0);
            }

            long activeAlerts = alertRepository.findByFieldIdAndStatus(f.getId(), AlertStatus.ACTIVE).size();
            props.put("activeAlertCount", activeAlerts);

            List<List<Double>> coords = GeoJsonUtils.extractCoordinates(f.getBoundary());
            features.add(new GeoJsonFeature(f.getId(), coords, props));
        }

        return new GeoJsonFeatureCollection(features);
    }
}
