package com.agrisight.alert.service;

import com.agrisight.alert.dto.AlertDto;
import com.agrisight.alert.dto.UpdateAlertStatusRequest;
import com.agrisight.alert.entity.Alert;
import com.agrisight.alert.entity.AlertSeverity;
import com.agrisight.alert.entity.AlertStatus;
import com.agrisight.alert.repository.AlertRepository;
import com.agrisight.common.PageResponse;
import com.agrisight.exception.ResourceNotFoundException;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final FieldRepository fieldRepository;

    public AlertService(AlertRepository alertRepository, FieldRepository fieldRepository) {
        this.alertRepository = alertRepository;
        this.fieldRepository = fieldRepository;
    }

    public PageResponse<AlertDto> getAlerts(AlertStatus status, AlertSeverity severity, Pageable pageable) {
        Page<Alert> page;
        if (status != null && severity != null) {
            page = alertRepository.findByStatusAndSeverity(status, severity, pageable);
        } else if (status != null) {
            page = alertRepository.findByStatus(status, pageable);
        } else if (severity != null) {
            page = alertRepository.findBySeverity(severity, pageable);
        } else {
            page = alertRepository.findAll(pageable);
        }

        // Cache fields for efficient enrichment
        Map<String, Field> fieldMap = fieldRepository.findAll().stream()
                .collect(Collectors.toMap(Field::getId, f -> f, (a, b) -> a));

        List<AlertDto> dtos = page.getContent().stream()
                .map(alert -> {
                    Field field = fieldMap.get(alert.getFieldId());
                    String name = field != null ? field.getName() : "Unknown Field";
                    String code = field != null ? field.getCode() : "N/A";
                    return AlertDto.from(alert, name, code);
                })
                .collect(Collectors.toList());

        return PageResponse.from(new PageImpl<>(dtos, pageable, page.getTotalElements()));
    }

    public AlertDto getAlertById(String id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with ID: " + id));
        Field field = fieldRepository.findById(alert.getFieldId()).orElse(null);
        String name = field != null ? field.getName() : "Unknown Field";
        String code = field != null ? field.getCode() : "N/A";
        return AlertDto.from(alert, name, code);
    }

    public AlertDto updateAlertStatus(String id, UpdateAlertStatusRequest request) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with ID: " + id));

        alert.setStatus(request.getStatus());
        if (request.getStatus() == AlertStatus.RESOLVED || request.getStatus() == AlertStatus.FALSE_POSITIVE) {
            alert.setResolvedAt(Instant.now());
        }

        alert = alertRepository.save(alert);
        Field field = fieldRepository.findById(alert.getFieldId()).orElse(null);
        String name = field != null ? field.getName() : "Unknown Field";
        String code = field != null ? field.getCode() : "N/A";
        return AlertDto.from(alert, name, code);
    }
}
