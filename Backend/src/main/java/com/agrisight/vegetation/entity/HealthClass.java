package com.agrisight.vegetation.entity;

public enum HealthClass {
    HEALTHY,
    MODERATE,
    POTENTIAL_STRESS,
    INSUFFICIENT_DATA;

    public String getColorHex() {
        return switch (this) {
            case HEALTHY -> "#22c55e"; // emerald green
            case MODERATE -> "#eab308"; // amber / yellow
            case POTENTIAL_STRESS -> "#ef4444"; // red
            case INSUFFICIENT_DATA -> "#94a3b8"; // slate gray
        };
    }
}
