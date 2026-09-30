package com.agrisight.seed;

import com.agrisight.analysis.service.HealthAnalysisService;
import com.agrisight.common.util.GeoJsonUtils;
import com.agrisight.field.entity.Field;
import com.agrisight.field.repository.FieldRepository;
import com.agrisight.region.entity.Region;
import com.agrisight.region.repository.RegionRepository;
import com.agrisight.satellite.entity.ProcessingStatus;
import com.agrisight.satellite.entity.SatelliteScene;
import com.agrisight.satellite.repository.SatelliteSceneRepository;
import com.agrisight.user.entity.Role;
import com.agrisight.user.entity.User;
import com.agrisight.user.repository.UserRepository;
import com.agrisight.vegetation.entity.DataQuality;
import com.agrisight.vegetation.entity.HealthClass;
import com.agrisight.vegetation.entity.VegetationObservation;
import com.agrisight.vegetation.repository.VegetationObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final FieldRepository fieldRepository;
    private final SatelliteSceneRepository sceneRepository;
    private final VegetationObservationRepository observationRepository;
    private final HealthAnalysisService healthAnalysisService;
    private final PasswordEncoder passwordEncoder;

    @Value("${agrisight.seeding.enabled:true}")
    private boolean seedingEnabled;

    public DataSeeder(UserRepository userRepository,
                      RegionRepository regionRepository,
                      FieldRepository fieldRepository,
                      SatelliteSceneRepository sceneRepository,
                      VegetationObservationRepository observationRepository,
                      HealthAnalysisService healthAnalysisService,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
        this.fieldRepository = fieldRepository;
        this.sceneRepository = sceneRepository;
        this.observationRepository = observationRepository;
        this.healthAnalysisService = healthAnalysisService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!seedingEnabled) {
            log.info("Data seeding is disabled.");
            return;
        }

        seedAdminUser();

        if (regionRepository.count() == 0) {
            log.info("Database is empty. Seeding initial regions, fields, Sentinel-2 scenes, and multi-temporal observations...");
            seedAgriculturalData();
            log.info("AgriSight initial data seeding completed successfully.");
        } else {
            log.info("Database already contains data ({} regions, {} fields). Skipping seeding.",
                    regionRepository.count(), fieldRepository.count());
        }
    }

    private void seedAdminUser() {
        if (!userRepository.existsByEmail("admin@agrisight.io")) {
            User admin = new User(
                    "AgriSight Admin",
                    "admin@agrisight.io",
                    passwordEncoder.encode("Password123!"),
                    Set.of(Role.ROLE_ADMIN, Role.ROLE_OFFICER, Role.ROLE_RESEARCHER)
            );
            userRepository.save(admin);
            log.info("Seeded default administrator: admin@agrisight.io / Password123!");
        }
    }

    private void seedAgriculturalData() {
        // 1. Region: Cauvery Delta Agricultural Basin (Tamil Nadu, India)
        List<List<Double>> cauveryCoords = List.of(
                List.of(79.05, 10.70),
                List.of(79.25, 10.70),
                List.of(79.25, 10.90),
                List.of(79.05, 10.90),
                List.of(79.05, 10.70)
        );
        GeoJsonPolygon cauveryPoly = GeoJsonUtils.createPolygon(cauveryCoords);
        Point cauveryCenter = GeoJsonUtils.calculateCentroid(cauveryPoly);
        Region cauveryRegion = new Region(
                "Cauvery Delta Agricultural Basin",
                "REG-CDR-01",
                "Major rice and sugarcane agricultural zone in southern India with dense canal irrigation.",
                cauveryPoly,
                cauveryCenter.getY(),
                cauveryCenter.getX(),
                GeoJsonUtils.calculateAreaHectares(cauveryPoly)
        );
        cauveryRegion = regionRepository.save(cauveryRegion);

        // 2. Region: Punjab Indo-Gangetic Wheat Belt (Punjab, India)
        List<List<Double>> punjabCoords = List.of(
                List.of(75.75, 30.80),
                List.of(75.95, 30.80),
                List.of(75.95, 31.00),
                List.of(75.75, 31.00),
                List.of(75.75, 30.80)
        );
        GeoJsonPolygon punjabPoly = GeoJsonUtils.createPolygon(punjabCoords);
        Point punjabCenter = GeoJsonUtils.calculateCentroid(punjabPoly);
        Region punjabRegion = new Region(
                "Punjab Indo-Gangetic Plain",
                "REG-PBW-02",
                "High-intensity intensive cereal farming zone with wheat-rice rotation system.",
                punjabPoly,
                punjabCenter.getY(),
                punjabCenter.getX(),
                GeoJsonUtils.calculateAreaHectares(punjabPoly)
        );
        punjabRegion = regionRepository.save(punjabRegion);

        // 3. Sentinel-2 Satellite Scenes
        SatelliteScene scene1 = new SatelliteScene("SENTINEL_2", "S2A_MSIL2A_20260715T050641", Instant.now().minusSeconds(86400 * 75), 1.8, "BBOX", "https://copernicus.eu/s2/scene1", ProcessingStatus.COMPLETED);
        SatelliteScene scene2 = new SatelliteScene("SENTINEL_2", "S2B_MSIL2A_20260801T050649", Instant.now().minusSeconds(86400 * 60), 3.2, "BBOX", "https://copernicus.eu/s2/scene2", ProcessingStatus.COMPLETED);
        SatelliteScene scene3 = new SatelliteScene("SENTINEL_2", "S2A_MSIL2A_20260816T050651", Instant.now().minusSeconds(86400 * 45), 2.1, "BBOX", "https://copernicus.eu/s2/scene3", ProcessingStatus.COMPLETED);
        SatelliteScene scene4 = new SatelliteScene("SENTINEL_2", "S2B_MSIL2A_20260901T050709", Instant.now().minusSeconds(86400 * 30), 4.5, "BBOX", "https://copernicus.eu/s2/scene4", ProcessingStatus.COMPLETED);
        SatelliteScene scene5 = new SatelliteScene("SENTINEL_2", "S2A_MSIL2A_20260916T050711", Instant.now().minusSeconds(86400 * 15), 1.4, "BBOX", "https://copernicus.eu/s2/scene5", ProcessingStatus.COMPLETED);
        SatelliteScene scene6 = new SatelliteScene("SENTINEL_2", "S2B_MSIL2A_20260928T050729", Instant.now().minusSeconds(86400 * 2), 0.9, "BBOX", "https://copernicus.eu/s2/scene6", ProcessingStatus.COMPLETED);
        sceneRepository.saveAll(List.of(scene1, scene2, scene3, scene4, scene5, scene6));

        // 4. Fields in Cauvery Delta
        // Field 1: Thanjavur Rice Field #101 (Healthy)
        Field f101 = createAndSaveField(cauveryRegion.getId(), "Thanjavur Rice Field #101", "FLD-101", "Rice",
                List.of(List.of(79.110, 10.750), List.of(79.118, 10.750), List.of(79.118, 10.758), List.of(79.110, 10.758), List.of(79.110, 10.750)));

        // Field 2: Kallanai Paddy Field #102 (Potential Stress - decline case from Section 14 demo story!)
        Field f102 = createAndSaveField(cauveryRegion.getId(), "Kallanai Paddy Field #102", "FLD-102", "Rice",
                List.of(List.of(79.130, 10.760), List.of(79.139, 10.760), List.of(79.139, 10.769), List.of(79.130, 10.769), List.of(79.130, 10.760)));

        // Field 3: Cauvery Sugarcane Block #103 (Moderate)
        Field f103 = createAndSaveField(cauveryRegion.getId(), "Cauvery Sugarcane Block #103", "FLD-103", "Sugarcane",
                List.of(List.of(79.145, 10.775), List.of(79.155, 10.775), List.of(79.155, 10.785), List.of(79.145, 10.785), List.of(79.145, 10.775)));

        // Field 4: Kumbakonam Agro-Estate #104 (Healthy)
        Field f104 = createAndSaveField(cauveryRegion.getId(), "Kumbakonam Agro-Estate #104", "FLD-104", "Cotton",
                List.of(List.of(79.170, 10.810), List.of(79.182, 10.810), List.of(79.182, 10.822), List.of(79.170, 10.822), List.of(79.170, 10.810)));

        // 5. Fields in Punjab Plain
        // Field 5: Ludhiana Wheat Plot #201 (Healthy)
        Field f201 = createAndSaveField(punjabRegion.getId(), "Ludhiana Wheat Plot #201", "FLD-201", "Wheat",
                List.of(List.of(75.820, 30.860), List.of(75.832, 30.860), List.of(75.832, 30.872), List.of(75.820, 30.872), List.of(75.820, 30.860)));

        // Field 6: Amritsar Basmati Field #202 (Moderate)
        Field f202 = createAndSaveField(punjabRegion.getId(), "Amritsar Basmati Field #202", "FLD-202", "Rice",
                List.of(List.of(75.845, 30.880), List.of(75.856, 30.880), List.of(75.856, 30.892), List.of(75.845, 30.892), List.of(75.845, 30.880)));

        // Field 7: Jalandhar Maize Block #203 (Potential Stress)
        Field f203 = createAndSaveField(punjabRegion.getId(), "Jalandhar Maize Block #203", "FLD-203", "Maize",
                List.of(List.of(75.870, 30.900), List.of(75.882, 30.900), List.of(75.882, 30.912), List.of(75.870, 30.912), List.of(75.870, 30.900)));

        // Field 8: Bathinda Agro Field #204 (Healthy)
        Field f204 = createAndSaveField(punjabRegion.getId(), "Bathinda Agro Field #204", "FLD-204", "Cotton",
                List.of(List.of(75.895, 30.925), List.of(75.908, 30.925), List.of(75.908, 30.938), List.of(75.895, 30.938), List.of(75.895, 30.925)));

        // 6. Multi-temporal observations
        LocalDate today = LocalDate.now();

        // Seed Field 101 (Healthy trajectory)
        seedObservationSeries(f101.getId(), scene1.getId(), today,
                new double[]{0.62, 0.68, 0.73, 0.76, 0.77, 0.78},
                new double[]{0.38, 0.42, 0.45, 0.48, 0.49, 0.50},
                HealthClass.HEALTHY);

        // Seed Field 102 (Significant Decline Story: 0.71 -> 0.43: drop of ~39.4%!)
        seedObservationSeries(f102.getId(), scene2.getId(), today,
                new double[]{0.63, 0.69, 0.71, 0.68, 0.61, 0.43},
                new double[]{0.39, 0.43, 0.45, 0.42, 0.36, 0.27},
                HealthClass.POTENTIAL_STRESS);

        // Seed Field 103 (Moderate vigor)
        seedObservationSeries(f103.getId(), scene3.getId(), today,
                new double[]{0.48, 0.51, 0.53, 0.52, 0.50, 0.51},
                new double[]{0.29, 0.31, 0.33, 0.32, 0.31, 0.31},
                HealthClass.MODERATE);

        // Seed Field 104 (Healthy)
        seedObservationSeries(f104.getId(), scene4.getId(), today,
                new double[]{0.58, 0.64, 0.69, 0.72, 0.74, 0.75},
                new double[]{0.35, 0.40, 0.43, 0.45, 0.47, 0.48},
                HealthClass.HEALTHY);

        // Seed Field 201 (Healthy Wheat)
        seedObservationSeries(f201.getId(), scene5.getId(), today,
                new double[]{0.60, 0.66, 0.70, 0.74, 0.76, 0.77},
                new double[]{0.37, 0.41, 0.44, 0.47, 0.48, 0.49},
                HealthClass.HEALTHY);

        // Seed Field 202 (Moderate)
        seedObservationSeries(f202.getId(), scene6.getId(), today,
                new double[]{0.45, 0.49, 0.52, 0.51, 0.48, 0.49},
                new double[]{0.28, 0.30, 0.32, 0.31, 0.29, 0.30},
                HealthClass.MODERATE);

        // Seed Field 203 (Decline / Potential Stress: 0.66 -> 0.39)
        seedObservationSeries(f203.getId(), scene1.getId(), today,
                new double[]{0.59, 0.64, 0.66, 0.62, 0.52, 0.39},
                new double[]{0.36, 0.40, 0.42, 0.38, 0.31, 0.23},
                HealthClass.POTENTIAL_STRESS);

        // Seed Field 204 (Healthy)
        seedObservationSeries(f204.getId(), scene2.getId(), today,
                new double[]{0.55, 0.61, 0.67, 0.71, 0.73, 0.74},
                new double[]{0.34, 0.38, 0.42, 0.45, 0.46, 0.47},
                HealthClass.HEALTHY);

        // 7. Run health analysis engine on each field to create official HealthAnalysis records and Alerts
        List<Field> allFields = fieldRepository.findAll();
        for (Field f : allFields) {
            healthAnalysisService.analyzeFieldHealth(f);
        }
    }

    private Field createAndSaveField(String regionId, String name, String code, String cropType, List<List<Double>> coords) {
        GeoJsonPolygon poly = GeoJsonUtils.createPolygon(coords);
        Point centroid = GeoJsonUtils.calculateCentroid(poly);
        double area = GeoJsonUtils.calculateAreaHectares(poly);
        Field field = new Field(regionId, name, code, cropType, area, poly, centroid.getY(), centroid.getX());
        return fieldRepository.save(field);
    }

    private void seedObservationSeries(String fieldId, String sceneId, LocalDate today,
                                       double[] ndviSeries, double[] ndreSeries, HealthClass latestClass) {
        int[] dayOffsets = new int[]{75, 60, 45, 30, 15, 2};
        for (int i = 0; i < dayOffsets.length; i++) {
            LocalDate date = today.minusDays(dayOffsets[i]);
            double ndvi = ndviSeries[i];
            double ndre = ndreSeries[i];
            double ndmi = Math.round((ndvi * 0.45) * 100.0) / 100.0;

            HealthClass hc;
            if (i == dayOffsets.length - 1) {
                hc = latestClass;
            } else if (ndvi >= 0.60) {
                hc = HealthClass.HEALTHY;
            } else if (ndvi >= 0.40) {
                hc = HealthClass.MODERATE;
            } else {
                hc = HealthClass.POTENTIAL_STRESS;
            }

            VegetationObservation obs = new VegetationObservation(
                    fieldId,
                    sceneId,
                    date,
                    ndvi,
                    ndre,
                    ndmi,
                    94.5,
                    DataQuality.HIGH,
                    hc
            );
            observationRepository.save(obs);
        }
    }
}
