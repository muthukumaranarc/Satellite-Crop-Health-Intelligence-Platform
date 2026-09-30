package com.agrisight.dashboard.dto;

import java.time.LocalDate;

public class TrendPointDto {

    private LocalDate date;
    private double meanNdvi;
    private double meanNdre;
    private double meanNdmi;
    private long observationCount;

    public TrendPointDto() {
    }

    public TrendPointDto(LocalDate date, double meanNdvi, double meanNdre, double meanNdmi, long observationCount) {
        this.date = date;
        this.meanNdvi = meanNdvi;
        this.meanNdre = meanNdre;
        this.meanNdmi = meanNdmi;
        this.observationCount = observationCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getMeanNdvi() {
        return meanNdvi;
    }

    public void setMeanNdvi(double meanNdvi) {
        this.meanNdvi = meanNdvi;
    }

    public double getMeanNdre() {
        return meanNdre;
    }

    public void setMeanNdre(double meanNdre) {
        this.meanNdre = meanNdre;
    }

    public double getMeanNdmi() {
        return meanNdmi;
    }

    public void setMeanNdmi(double meanNdmi) {
        this.meanNdmi = meanNdmi;
    }

    public long getObservationCount() {
        return observationCount;
    }

    public void setObservationCount(long observationCount) {
        this.observationCount = observationCount;
    }
}
