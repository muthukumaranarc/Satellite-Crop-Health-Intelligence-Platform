# AgriSight Backend — Satellite Crop Health Intelligence Platform

Production-quality backend for **AgriSight** built using **Java 25.0.2 / 21 LTS**, **Spring Boot 3.4.3**, and **Spring Data MongoDB** with native `2dsphere` spatial indexing for GeoJSON polygons.

---

## Features

- **Regional & Field Management**: OGC-compliant GeoJSON polygons for agricultural field boundaries, automated centroid, and area (hectare) computation.
- **Vegetation Indices**: Time-series observation tracking for Sentinel-2 optical data (NDVI, NDRE, NDMI) with data-quality and cloud masking support.
- **Scientific Crop Health Engine**: Responsible classification (`HEALTHY`, `MODERATE`, `POTENTIAL_STRESS`, `INSUFFICIENT_DATA`), percentage decline calculation ($\Delta\%$), stress scoring (0.0 to 1.0), and ground-truth recommendation diagnostics.
- **Automated Stress Alerts**: Flags vegetation declines greater than 20–25% with configurable alert lifecycle (`ACTIVE`, `ACKNOWLEDGED`, `RESOLVED`, `FALSE_POSITIVE`).
- **Interactive GIS GeoJSON Endpoints**: Standard RFC 7946 GeoJSON FeatureCollections pre-styled with hex color tags (`#22c55e`, `#eab308`, `#ef4444`, `#94a3b8`) for immediate rendering in Leaflet, MapLibre, Mapbox, or OpenLayers.
- **Dashboard Analytics**: High-level KPIs, crop & regional health distributions, and 90-day multi-temporal trend curves.
- **Resilient ML Integration**: Non-blocking `WebClient` integration to external Python raster services with automatic internal rule-based analysis fallback.
- **Security & JWT**: Stateless Spring Security authentication with role-based access control (`ROLE_ADMIN`, `ROLE_OFFICER`, `ROLE_RESEARCHER`, `ROLE_FARMER`).
- **OpenAPI 3.1 / Swagger UI**: Built-in interactive documentation at `/swagger-ui.html`.
- **Initial Data Seeder**: Built-in CommandLineRunner seeding realistic agricultural zones (Cauvery Delta Basin, Punjab Wheat Belt), 8 monitored fields, Sentinel-2 scenes, and multi-temporal observations.

---

## Configuration & Environment Variables

The backend dynamically binds to system environment variables or values from `.env`:

| Variable | Default Value | Description |
| :--- | :--- | :--- |
| `MONGODB_URL` | `${MONGODB_URL}` | MongoDB Atlas connection string |
| `MONGODB_DATABASE` | `agrisight` | Target MongoDB database name |
| `PORT` | `8080` | HTTP server port |
| `JWT_SECRET` | `${JWT_SECRET}` | Secret key used for signing JWT tokens |
| `ML_SERVICE_URL` | `http://localhost:8000` | URL for the external Python raster/ML service |
| `SEED_DATA` | `true` | Seeds initial demonstration data if database is empty |

---

## Getting Started

### Prerequisites
- **Java 21+** (JDK 25.0.2 installed)
- **Maven 3.9+**

### Build & Run Tests
```powershell
# Navigate to the Backend directory
cd Backend

# Run automated tests
mvn clean test
```

### Run the Application
```powershell
mvn spring-boot:run
```

Or run the packaged JAR:
```powershell
mvn package -DskipTests
java -jar target/agrisight-backend-1.0.0-SNAPSHOT.jar
```

---

## API Documentation

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Schema (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Default Administrator Credentials
- **Email**: `admin@agrisight.io`
- **Password**: `Password123!`

---

## Core REST Endpoints

### 1. Dashboard
- `GET /api/v1/dashboard/summary` — High-level KPI cards (field count, monitored hectares, health distribution, active alerts, mean NDVI/NDRE).
- `GET /api/v1/dashboard/health-distribution` — Crop-wise and region-wise breakdown of health categories.
- `GET /api/v1/dashboard/trends?days=90` — Time-series mean NDVI/NDRE/NDMI progression points.

### 2. GIS Map Layers
- `GET /api/v1/gis/fields` — GeoJSON `FeatureCollection` containing all fields with polygon geometry and properties.
- `GET /api/v1/gis/health-map` — GeoJSON `FeatureCollection` with hex color codes ready for GIS map layer styling.

### 3. Regions & Fields
- `GET /api/v1/regions` — List monitoring regions.
- `POST /api/v1/regions` — Register region with polygon boundary.
- `GET /api/v1/fields` — Paginated field list (filters: `regionId`, `cropType`, `healthClass`).
- `POST /api/v1/fields` — Register agricultural field polygon boundary.
- `GET /api/v1/fields/{id}/health` — Field current health status, change %, stress score, and explanation.
- `GET /api/v1/fields/{id}/history` — Multi-temporal vegetation index observations.
- `GET /api/v1/fields/{id}/geometry` — GeoJSON polygon coordinate list.

### 4. Crop Health Analysis & Stress Alerts
- `POST /api/v1/analysis` — Trigger on-demand satellite health analysis on field.
- `GET /api/v1/analysis/{id}` — Retrieve analysis details.
- `GET /api/v1/alerts` — Paginated alerts (`status`, `severity`).
- `PATCH /api/v1/alerts/{id}/status` — Update alert status (`ACKNOWLEDGED`, `RESOLVED`, `FALSE_POSITIVE`).

### 5. Authentication
- `POST /api/v1/auth/login` — User authentication returning JWT token.
- `POST /api/v1/auth/register` — User registration.
- `GET /api/v1/auth/me` — Current authenticated principal details.
