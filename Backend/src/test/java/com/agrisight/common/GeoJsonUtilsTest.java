package com.agrisight.common;

import com.agrisight.common.util.GeoJsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GeoJsonUtilsTest {

    @Test
    void testCreatePolygonAndExtractCoordinates() {
        List<List<Double>> coords = List.of(
                List.of(79.10, 10.70),
                List.of(79.20, 10.70),
                List.of(79.20, 10.80),
                List.of(79.10, 10.80)
        );

        GeoJsonPolygon polygon = GeoJsonUtils.createPolygon(coords);
        assertNotNull(polygon);
        // Should automatically close the ring (5 points total)
        assertEquals(5, polygon.getPoints().size());

        List<List<Double>> extracted = GeoJsonUtils.extractCoordinates(polygon);
        assertEquals(5, extracted.size());
        assertEquals(79.10, extracted.get(0).get(0));
        assertEquals(10.70, extracted.get(0).get(1));
    }

    @Test
    void testCalculateCentroid() {
        List<List<Double>> coords = List.of(
                List.of(10.0, 20.0),
                List.of(20.0, 20.0),
                List.of(20.0, 30.0),
                List.of(10.0, 30.0)
        );

        GeoJsonPolygon polygon = GeoJsonUtils.createPolygon(coords);
        Point centroid = GeoJsonUtils.calculateCentroid(polygon);

        assertNotNull(centroid);
        assertEquals(15.0, centroid.getX(), 0.001);
        assertEquals(25.0, centroid.getY(), 0.001);
    }

    @Test
    void testCalculateAreaHectares() {
        List<List<Double>> coords = List.of(
                List.of(79.110, 10.750),
                List.of(79.118, 10.750),
                List.of(79.118, 10.758),
                List.of(79.110, 10.758),
                List.of(79.110, 10.750)
        );

        GeoJsonPolygon polygon = GeoJsonUtils.createPolygon(coords);
        double areaHectares = GeoJsonUtils.calculateAreaHectares(polygon);

        assertTrue(areaHectares > 0.0);
        assertTrue(areaHectares < 1000.0);
    }
}
