package com.agrisight.analysis;

import com.agrisight.alert.entity.Alert;
import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.analysis.entity.HealthAnalysis;
import com.agrisight.analysis.repository.HealthAnalysisRepository;
import com.agrisight.analysis.service.HealthAnalysisService;
import com.agrisight.field.entity.Field;
import com.agrisight.vegetation.entity.DataQuality;
import com.agrisight.vegetation.entity.HealthClass;
import com.agrisight.vegetation.entity.VegetationObservation;
import com.agrisight.vegetation.repository.VegetationObservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HealthAnalysisServiceTest {

    @Mock
    private HealthAnalysisRepository healthAnalysisRepository;

    @Mock
    private VegetationObservationRepository observationRepository;

    @Mock
    private AlertRepository alertRepository;

    private HealthAnalysisService healthAnalysisService;

    @BeforeEach
    void setUp() {
        healthAnalysisService = new HealthAnalysisService(
                healthAnalysisRepository,
                observationRepository,
                alertRepository
        );

        when(healthAnalysisRepository.save(any(HealthAnalysis.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testAnalyzeFieldHealth_HealthyNoDecline() {
        Field field = new Field();
        field.setId("field-1");
        field.setName("Healthy Field");
        field.setCode("FLD-001");

        VegetationObservation obs1 = new VegetationObservation("field-1", "scene-1", LocalDate.now(), 0.75, 0.45, 0.32, 95.0, DataQuality.HIGH, HealthClass.HEALTHY);
        VegetationObservation obs2 = new VegetationObservation("field-1", "scene-2", LocalDate.now().minusDays(15), 0.72, 0.43, 0.30, 95.0, DataQuality.HIGH, HealthClass.HEALTHY);

        when(observationRepository.findFirst2ByFieldIdOrderByObservationDateDesc("field-1"))
                .thenReturn(List.of(obs1, obs2));

        HealthAnalysis analysis = healthAnalysisService.analyzeFieldHealth(field);

        assertNotNull(analysis);
        assertEquals(HealthClass.HEALTHY, analysis.getHealthClass());
        assertEquals(0.75, analysis.getCurrentNdvi());
        assertEquals(0.72, analysis.getPreviousNdvi());
        assertTrue(analysis.getNdviChangePercent() > 0);
        assertTrue(analysis.getStressScore() < 0.25);
        verify(alertRepository, never()).save(any(Alert.class));
    }

    @Test
    void testAnalyzeFieldHealth_SignificantDeclineTriggersAlert() {
        Field field = new Field();
        field.setId("field-stress");
        field.setName("Stress Test Field");
        field.setCode("FLD-102");

        // Sudden drop from 0.70 to 0.42 (decline: -40%)
        VegetationObservation current = new VegetationObservation("field-stress", "scene-current", LocalDate.now(), 0.42, 0.25, 0.18, 92.0, DataQuality.HIGH, HealthClass.POTENTIAL_STRESS);
        VegetationObservation previous = new VegetationObservation("field-stress", "scene-prev", LocalDate.now().minusDays(14), 0.70, 0.44, 0.32, 95.0, DataQuality.HIGH, HealthClass.HEALTHY);

        when(observationRepository.findFirst2ByFieldIdOrderByObservationDateDesc("field-stress"))
                .thenReturn(List.of(current, previous));
        when(alertRepository.findByFieldIdAndStatus(eq("field-stress"), any()))
                .thenReturn(Collections.emptyList());

        HealthAnalysis analysis = healthAnalysisService.analyzeFieldHealth(field);

        assertNotNull(analysis);
        assertEquals(HealthClass.POTENTIAL_STRESS, analysis.getHealthClass());
        assertEquals(0.42, analysis.getCurrentNdvi());
        assertEquals(0.70, analysis.getPreviousNdvi());
        assertEquals(-40.0, analysis.getNdviChangePercent(), 0.1);
        assertTrue(analysis.getStressScore() >= 0.70);

        // Verify alert was saved
        ArgumentCaptor<Alert> alertCaptor = ArgumentCaptor.forClass(Alert.class);
        verify(alertRepository).save(alertCaptor.capture());
        Alert savedAlert = alertCaptor.getValue();
        assertEquals("field-stress", savedAlert.getFieldId());
        assertEquals("POTENTIAL_VEGETATION_DECLINE", savedAlert.getType());
    }

    @Test
    void testAnalyzeFieldHealth_EmptyObservations_ReturnsInsufficientData() {
        Field field = new Field();
        field.setId("field-empty");

        when(observationRepository.findFirst2ByFieldIdOrderByObservationDateDesc("field-empty"))
                .thenReturn(Collections.emptyList());

        HealthAnalysis analysis = healthAnalysisService.analyzeFieldHealth(field);

        assertNotNull(analysis);
        assertEquals(HealthClass.INSUFFICIENT_DATA, analysis.getHealthClass());
    }
}
