package com.agrisight.gis;

import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.gis.dto.GeoJsonFeature;
import com.agrisight.gis.dto.GeoJsonFeatureCollection;
import com.agrisight.gis.service.GisService;
import com.agrisight.region.entity.Region;
import com.agrisight.region.repository.RegionRepository;
import com.agrisight.vegetation.entity.HealthClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GisServiceTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private HealthAnalysisRepository healthAnalysisRepository;

    @Mock
    private AlertRepository alertRepository;

    private GisService gisService;

    @BeforeEach
    void setUp() {
        gisService = new GisService(fieldRepository, regionRepository, healthAnalysisRepository, alertRepository);
    }

    @Test
    void testGetFieldFeatures_ReturnsValidGeoJsonFeatureCollection() {
        List<List<Double>> coords = List.of(
                List.of(79.110, 10.750),
                List.of(79.118, 10.750),
                List.of(79.118, 10.758),
                List.of(79.110, 10.758),
                List.of(79.110, 10.750)
        );
        GeoJsonPolygon poly = GeoJsonUtils.createPolygon(coords);

        Field field = new Field("reg-1", "Test Rice Field", "FLD-101", "Rice", 5.2, poly, 10.754, 79.114);
        field.setId("f-101");

        when(fieldRepository.findAll()).thenReturn(List.of(field));
        when(regionRepository.findAll()).thenReturn(Collections.emptyList());

        HealthAnalysis ha = new HealthAnalysis(
                "f-101", LocalDate.now(), 0.72, 0.68, 5.88, 0.44, 0.31,
                HealthClass.HEALTHY, 0.12, 0.90, "RULE_V1", "Healthy crop"
        );
        when(healthAnalysisRepository.findFirstByFieldIdOrderByAnalysisDateDesc("f-101"))
                .thenReturn(Optional.of(ha));
        when(alertRepository.findByFieldIdAndStatus("f-101", com.agrisight.alert.entity.AlertStatus.ACTIVE))
                .thenReturn(Collections.emptyList());

        GeoJsonFeatureCollection collection = gisService.getFieldFeatures(null, null);

        assertNotNull(collection);
        assertEquals("FeatureCollection", collection.getType());
        assertEquals(1, collection.getFeatures().size());

        GeoJsonFeature feature = collection.getFeatures().get(0);
        assertEquals("Feature", feature.getType());
        assertEquals("f-101", feature.getId());
        assertEquals("Polygon", feature.getGeometry().getType());
        assertNotNull(feature.getProperties());
        assertEquals("Test Rice Field", feature.getProperties().get("name"));
        assertEquals("HEALTHY", feature.getProperties().get("healthClass"));
        assertEquals("#22c55e", feature.getProperties().get("healthColor"));
        assertEquals(0.72, feature.getProperties().get("currentNdvi"));
    }
}
