package com.agrisight.gis.dto;

import java.util.List;
import java.util.Map;

public class GeoJsonFeature {

    private String type = "Feature";
    private String id;
    private Geometry geometry;
    private Map<String, Object> properties;

    public GeoJsonFeature() {
    }

    public GeoJsonFeature(String id, List<List<Double>> polygonCoordinates, Map<String, Object> properties) {
        this.type = "Feature";
        this.id = id;
        this.geometry = new Geometry("Polygon", List.of(polygonCoordinates));
        this.properties = properties;
    }

    public static class Geometry {
        private String type = "Polygon";
        private List<List<List<Double>>> coordinates;

        public Geometry() {
        }

        public Geometry(String type, List<List<List<Double>>> coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<List<List<Double>>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<List<Double>>> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
