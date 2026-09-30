package com.agrisight.dashboard.dto;

import java.util.Map;

public class HealthDistributionDto {

    private Map<String, Long> overallDistribution;
    private Map<String, Map<String, Long>> distributionByCrop;
    private Map<String, Map<String, Long>> distributionByRegion;

    public HealthDistributionDto() {
    }

    public HealthDistributionDto(Map<String, Long> overallDistribution,
                                 Map<String, Map<String, Long>> distributionByCrop,
                                 Map<String, Map<String, Long>> distributionByRegion) {
        this.overallDistribution = overallDistribution;
        this.distributionByCrop = distributionByCrop;
        this.distributionByRegion = distributionByRegion;
    }

    public Map<String, Long> getOverallDistribution() {
        return overallDistribution;
    }

    public void setOverallDistribution(Map<String, Long> overallDistribution) {
        this.overallDistribution = overallDistribution;
    }

    public Map<String, Map<String, Long>> getDistributionByCrop() {
        return distributionByCrop;
    }

    public void setDistributionByCrop(Map<String, Map<String, Long>> distributionByCrop) {
        this.distributionByCrop = distributionByCrop;
    }

    public Map<String, Map<String, Long>> getDistributionByRegion() {
        return distributionByRegion;
    }

    public void setDistributionByRegion(Map<String, Map<String, Long>> distributionByRegion) {
        this.distributionByRegion = distributionByRegion;
    }
}
