package com.agrisight.dashboard.service;

import com.agrisight.alert.entity.AlertStatus;
import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.dashboard.dto.DashboardSummaryDto;
import com.agrisight.dashboard.dto.HealthDistributionDto;
import com.agrisight.dashboard.dto.TrendPointDto;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.region.entity.Region;
import com.agrisight.region.repository.RegionRepository;
import com.agrisight.vegetation.entity.HealthClass;
import com.agrisight.vegetation.entity.VegetationObservation;
import com.agrisight.vegetation.repository.VegetationObservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final FieldRepository fieldRepository;
    private final RegionRepository regionRepository;
    private final HealthAnalysisRepository healthAnalysisRepository;
    private final VegetationObservationRepository observationRepository;
    private final AlertRepository alertRepository;

    public DashboardService(FieldRepository fieldRepository,
                            RegionRepository regionRepository,
                            HealthAnalysisRepository healthAnalysisRepository,
                            VegetationObservationRepository observationRepository,
                            AlertRepository alertRepository) {
        this.fieldRepository = fieldRepository;
        this.regionRepository = regionRepository;
        this.healthAnalysisRepository = healthAnalysisRepository;
        this.observationRepository = observationRepository;
        this.alertRepository = alertRepository;
    }

    public DashboardSummaryDto getSummary() {
        List<Field> fields = fieldRepository.findAll();
        long totalFields = fields.size();
        double totalHectares = fields.stream().mapToDouble(Field::getAreaHectares).sum();

        long healthy = 0;
        long moderate = 0;
        long potentialStress = 0;
        long insufficient = 0;
        double sumNdvi = 0;
        double sumNdre = 0;
        int evaluatedCount = 0;

        for (Field f : fields) {
            Optional<HealthAnalysis> latest = healthAnalysisRepository.findFirstByFieldIdOrderByAnalysisDateDesc(f.getId());
            if (latest.isPresent()) {
                HealthAnalysis ha = latest.get();
                switch (ha.getHealthClass()) {
                    case HEALTHY -> healthy++;
                    case MODERATE -> moderate++;
                    case POTENTIAL_STRESS -> potentialStress++;
                    case INSUFFICIENT_DATA -> insufficient++;
                }
                sumNdvi += ha.getCurrentNdvi();
                sumNdre += ha.getCurrentNdre();
                evaluatedCount++;
            } else {
                insufficient++;
            }
        }

        long activeAlerts = alertRepository.countByStatus(AlertStatus.ACTIVE);

        DashboardSummaryDto dto = new DashboardSummaryDto();
        dto.setTotalFields(totalFields);
        dto.setTotalMonitoredHectares(Math.round(totalHectares * 10.0) / 10.0);
        dto.setHealthyFields(healthy);
        dto.setModerateFields(moderate);
        dto.setPotentialStressFields(potentialStress);
        dto.setInsufficientDataFields(insufficient);
        dto.setActiveAlerts(activeAlerts);
        dto.setAverageNdvi(evaluatedCount > 0 ? Math.round((sumNdvi / evaluatedCount) * 1000.0) / 1000.0 : 0.0);
        dto.setAverageNdre(evaluatedCount > 0 ? Math.round((sumNdre / evaluatedCount) * 1000.0) / 1000.0 : 0.0);

        return dto;
    }

    public HealthDistributionDto getHealthDistribution() {
        List<Field> fields = fieldRepository.findAll();
        Map<String, String> regionNameMap = regionRepository.findAll().stream()
                .collect(Collectors.toMap(Region::getId, Region::getName, (a, b) -> a));

        Map<String, Long> overall = new HashMap<>();
        Map<String, Map<String, Long>> byCrop = new HashMap<>();
        Map<String, Map<String, Long>> byRegion = new HashMap<>();

        for (HealthClass hc : HealthClass.values()) {
            overall.put(hc.name(), 0L);
        }

        for (Field f : fields) {
            String crop = f.getCropType() != null ? f.getCropType() : "Unknown";
            String regionName = regionNameMap.getOrDefault(f.getRegionId(), "Unknown");

            byCrop.putIfAbsent(crop, new HashMap<>());
            byRegion.putIfAbsent(regionName, new HashMap<>());

            HealthClass hc = healthAnalysisRepository.findFirstByFieldIdOrderByAnalysisDateDesc(f.getId())
                    .map(HealthAnalysis::getHealthClass)
                    .orElse(HealthClass.INSUFFICIENT_DATA);

            overall.put(hc.name(), overall.get(hc.name()) + 1);

            Map<String, Long> cropMap = byCrop.get(crop);
            cropMap.put(hc.name(), cropMap.getOrDefault(hc.name(), 0L) + 1);

            Map<String, Long> regMap = byRegion.get(regionName);
            regMap.put(hc.name(), regMap.getOrDefault(hc.name(), 0L) + 1);
        }

        return new HealthDistributionDto(overall, byCrop, byRegion);
    }

    public List<TrendPointDto> getTrends(int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days > 0 ? days : 90);

        List<VegetationObservation> observations = observationRepository.findByObservationDateBetween(startDate, endDate);

        // Group by observation date
        Map<LocalDate, List<VegetationObservation>> grouped = observations.stream()
                .collect(Collectors.groupingBy(VegetationObservation::getObservationDate));

        return grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<VegetationObservation> list = entry.getValue();
                    double meanNdvi = list.stream().mapToDouble(VegetationObservation::getNdvi).average().orElse(0.0);
                    double meanNdre = list.stream().mapToDouble(VegetationObservation::getNdre).average().orElse(0.0);
                    double meanNdmi = list.stream().mapToDouble(VegetationObservation::getNdmi).average().orElse(0.0);

                    return new TrendPointDto(
                            date,
                            Math.round(meanNdvi * 1000.0) / 1000.0,
                            Math.round(meanNdre * 1000.0) / 1000.0,
                            Math.round(meanNdmi * 1000.0) / 1000.0,
                            list.size()
                    );
                })
                .collect(Collectors.toList());
    }
}
