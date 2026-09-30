package com.agrisight.region.repository;

import com.agrisight.region.entity.Region;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends MongoRepository<Region, String> {
    Optional<Region> findByCode(String code);
    boolean existsByCode(String code);
    boolean existsByName(String name);
}
