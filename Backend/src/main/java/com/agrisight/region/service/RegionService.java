package com.agrisight.region.service;

import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.exception.BadRequestException;
import com.agrisight.exception.ResourceNotFoundException;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.region.dto.CreateRegionRequest;
import com.agrisight.region.dto.RegionDto;
import com.agrisight.region.entity.Region;
import com.agrisight.region.repository.RegionRepository;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final FieldRepository fieldRepository;

    public RegionService(RegionRepository regionRepository, FieldRepository fieldRepository) {
        this.regionRepository = regionRepository;
        this.fieldRepository = fieldRepository;
    }

    public List<RegionDto> getAllRegions() {
        return regionRepository.findAll().stream()
                .map(region -> {
                    long fieldCount = fieldRepository.countByRegionId(region.getId());
                    return RegionDto.from(region, fieldCount);
                })
                .collect(Collectors.toList());
    }

    public RegionDto getRegionById(String id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with ID: " + id));
        long fieldCount = fieldRepository.countByRegionId(region.getId());
        return RegionDto.from(region, fieldCount);
    }

    public RegionDto createRegion(CreateRegionRequest request) {
        if (regionRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Region with code already exists: " + request.getCode());
        }
        if (regionRepository.existsByName(request.getName())) {
            throw new BadRequestException("Region with name already exists: " + request.getName());
        }

        GeoJsonPolygon polygon = GeoJsonUtils.createPolygon(request.getCoordinates());
        Point centroid = GeoJsonUtils.calculateCentroid(polygon);
        double areaHectares = GeoJsonUtils.calculateAreaHectares(polygon);

        Region region = new Region(
                request.getName(),
                request.getCode(),
                request.getDescription(),
                polygon,
                centroid.getY(), // lat
                centroid.getX(), // lng
                areaHectares
        );

        region = regionRepository.save(region);
        return RegionDto.from(region, 0);
    }
}
