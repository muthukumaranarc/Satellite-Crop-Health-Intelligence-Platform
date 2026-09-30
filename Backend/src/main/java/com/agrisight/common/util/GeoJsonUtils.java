package com.agrisight.common.util;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.ArrayList;
import java.util.List;

public final class GeoJsonUtils {

    private static final double EARTH_RADIUS_METERS = 6378137.0;

    private GeoJsonUtils() {
    }

    /**
     * Converts a list of [lng, lat] coordinate pairs into a valid GeoJsonPolygon.
     * Ensures the ring is closed (first point equals last point).
     */
    public static GeoJsonPolygon createPolygon(List<List<Double>> coordinates) {
        if (coordinates == null || coordinates.size() < 3) {
            throw new IllegalArgumentException("A polygon must have at least 3 distinct coordinates.");
        }

        List<Point> points = new ArrayList<>();
        for (List<Double> coord : coordinates) {
            if (coord.size() < 2) {
                throw new IllegalArgumentException("Each coordinate must be [longitude, latitude].");
            }
            double lng = coord.get(0);
            double lat = coord.get(1);
            points.add(new Point(lng, lat));
        }

        // Close polygon ring if not already closed
        Point first = points.get(0);
        Point last = points.get(points.size() - 1);
        if (Double.compare(first.getX(), last.getX()) != 0 || Double.compare(first.getY(), last.getY()) != 0) {
            points.add(new Point(first.getX(), first.getY()));
        }

        if (points.size() < 4) {
            throw new IllegalArgumentException("A closed polygon ring must contain at least 4 points.");
        }

        return new GeoJsonPolygon(points);
    }

    /**
     * Extracts coordinate pairs [lng, lat] from a GeoJsonPolygon.
     */
    public static List<List<Double>> extractCoordinates(GeoJsonPolygon polygon) {
        List<List<Double>> result = new ArrayList<>();
        if (polygon == null || polygon.getPoints() == null) {
            return result;
        }
        for (Point p : polygon.getPoints()) {
            result.add(List.of(p.getX(), p.getY()));
        }
        return result;
    }

    /**
     * Calculates the centroid (mean lng, lat) of the polygon.
     */
    public static Point calculateCentroid(GeoJsonPolygon polygon) {
        if (polygon == null || polygon.getPoints() == null || polygon.getPoints().isEmpty()) {
            return new Point(0, 0);
        }
        double sumLng = 0;
        double sumLat = 0;
        int count = 0;
        int size = polygon.getPoints().size();
        // Ignore the closing duplicate point for centroid averaging
        int limit = (size > 1) ? size - 1 : size;
        for (int i = 0; i < limit; i++) {
            Point p = polygon.getPoints().get(i);
            sumLng += p.getX();
            sumLat += p.getY();
            count++;
        }
        return new Point(sumLng / count, sumLat / count);
    }

    /**
     * Calculates approximate area in hectares using spherical earth projection.
     */
    public static double calculateAreaHectares(GeoJsonPolygon polygon) {
        if (polygon == null || polygon.getPoints() == null || polygon.getPoints().size() < 4) {
            return 0.0;
        }

        List<Point> pts = polygon.getPoints();
        double total = 0.0;
        for (int i = 0; i < pts.size() - 1; i++) {
            Point p1 = pts.get(i);
            Point p2 = pts.get(i + 1);

            double lon1Rad = Math.toRadians(p1.getX());
            double lat1Rad = Math.toRadians(p1.getY());
            double lon2Rad = Math.toRadians(p2.getX());
            double lat2Rad = Math.toRadians(p2.getY());

            total += (lon2Rad - lon1Rad) * (2.0 + Math.sin(lat1Rad) + Math.sin(lat2Rad));
        }

        double areaSqMeters = Math.abs(total * EARTH_RADIUS_METERS * EARTH_RADIUS_METERS / 2.0);
        double hectares = areaSqMeters / 10000.0;
        return Math.round(hectares * 100.0) / 100.0; // round to 2 decimals
    }
}
