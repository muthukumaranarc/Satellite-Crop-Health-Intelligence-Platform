package com.agrisight.dashboard.dto;

public class DashboardSummaryDto {

    private long totalFields;
    private double totalMonitoredHectares;
    private long healthyFields;
    private long moderateFields;
    private long potentialStressFields;
    private long insufficientDataFields;
    private long activeAlerts;
    private double averageNdvi;
    private double averageNdre;

    public DashboardSummaryDto() {
    }

    public long getTotalFields() {
        return totalFields;
    }

    public void setTotalFields(long totalFields) {
        this.totalFields = totalFields;
    }

    public double getTotalMonitoredHectares() {
        return totalMonitoredHectares;
    }

    public void setTotalMonitoredHectares(double totalMonitoredHectares) {
        this.totalMonitoredHectares = totalMonitoredHectares;
    }

    public long getHealthyFields() {
        return healthyFields;
    }

    public void setHealthyFields(long healthyFields) {
        this.healthyFields = healthyFields;
    }

    public long getModerateFields() {
        return moderateFields;
    }

    public void setModerateFields(long moderateFields) {
        this.moderateFields = moderateFields;
    }

    public long getPotentialStressFields() {
        return potentialStressFields;
    }

    public void setPotentialStressFields(long potentialStressFields) {
        this.potentialStressFields = potentialStressFields;
    }

    public long getInsufficientDataFields() {
        return insufficientDataFields;
    }

    public void setInsufficientDataFields(long insufficientDataFields) {
        this.insufficientDataFields = insufficientDataFields;
    }

    public long getActiveAlerts() {
        return activeAlerts;
    }

    public void setActiveAlerts(long activeAlerts) {
        this.activeAlerts = activeAlerts;
    }

    public double getAverageNdvi() {
        return averageNdvi;
    }

    public void setAverageNdvi(double averageNdvi) {
        this.averageNdvi = averageNdvi;
    }

    public double getAverageNdre() {
        return averageNdre;
    }

    public void setAverageNdre(double averageNdre) {
        this.averageNdre = averageNdre;
    }
}
