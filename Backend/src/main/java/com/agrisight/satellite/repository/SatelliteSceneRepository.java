package com.agrisight.satellite.repository;

import com.agrisight.satellite.entity.SatelliteScene;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SatelliteSceneRepository extends MongoRepository<SatelliteScene, String> {
    Optional<SatelliteScene> findBySceneId(String sceneId);
    boolean existsBySceneId(String sceneId);
}
