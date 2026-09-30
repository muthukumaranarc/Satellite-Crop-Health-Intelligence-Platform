package com.agrisight.field.repository;

import com.agrisight.field.entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends MongoRepository<Field, String> {
    List<Field> findByRegionId(String regionId);
    Page<Field> findByRegionId(String regionId, Pageable pageable);
    long countByRegionId(String regionId);
    Optional<Field> findByCode(String code);
    boolean existsByCode(String code);
}
