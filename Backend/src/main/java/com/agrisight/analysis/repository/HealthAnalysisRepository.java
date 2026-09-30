package com.agrisight.analysis.repository;

import com.agrisight.analysis.entity.HealthAnalysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthAnalysisRepository extends MongoRepository<HealthAnalysis, String> {
    Optional<HealthAnalysis> findFirstByFieldIdOrderByAnalysisDateDesc(String fieldId);
    List<HealthAnalysis> findByFieldIdOrderByAnalysisDateDesc(String fieldId);
    Page<HealthAnalysis> findByFieldIdOrderByAnalysisDateDesc(String fieldId, Pageable pageable);
    void deleteByFieldId(String fieldId);
}
