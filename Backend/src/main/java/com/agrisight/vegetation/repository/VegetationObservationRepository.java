package com.agrisight.vegetation.repository;

import com.agrisight.vegetation.entity.VegetationObservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VegetationObservationRepository extends MongoRepository<VegetationObservation, String> {
    List<VegetationObservation> findByFieldIdOrderByObservationDateAsc(String fieldId);
    List<VegetationObservation> findByFieldIdAndObservationDateBetweenOrderByObservationDateAsc(String fieldId, LocalDate start, LocalDate end);
    Optional<VegetationObservation> findFirstByFieldIdOrderByObservationDateDesc(String fieldId);
    List<VegetationObservation> findFirst2ByFieldIdOrderByObservationDateDesc(String fieldId);
    List<VegetationObservation> findByObservationDateBetween(LocalDate start, LocalDate end);
    void deleteByFieldId(String fieldId);
}
