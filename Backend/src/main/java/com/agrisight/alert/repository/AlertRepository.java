package com.agrisight.alert.repository;

import com.agrisight.alert.entity.Alert;
import com.agrisight.alert.entity.AlertSeverity;
import com.agrisight.alert.entity.AlertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {
    List<Alert> findByFieldId(String fieldId);
    Page<Alert> findByStatus(AlertStatus status, Pageable pageable);
    Page<Alert> findBySeverity(AlertSeverity severity, Pageable pageable);
    Page<Alert> findByStatusAndSeverity(AlertStatus status, AlertSeverity severity, Pageable pageable);
    List<Alert> findByFieldIdAndStatus(String fieldId, AlertStatus status);
    long countByStatus(AlertStatus status);
    void deleteByFieldId(String fieldId);
}
