# AgriSight — Satellite Crop Health Intelligence Platform

## 1. Product Identity

**Application Name:** AgriSight  
**Full Product Name:** AgriSight — Satellite Crop Health Intelligence Platform

**One-line description:**  
A satellite-powered agricultural intelligence platform that monitors crop health, detects vegetation decline, analyzes field-level trends, and visualizes potential crop stress on an interactive GIS dashboard.

**Core problem statement:**  
Develop a satellite-based crop health monitoring system that:
- Calculates NDVI, NDRE and other useful vegetation indices.
- Generates crop-health classes.
- Compares crop condition over time.
- Identifies agricultural fields showing potential stress.
- Presents the results through an interactive geographic dashboard.

---

# 2. Product Vision

AgriSight should not be a simple NDVI calculator.

It should behave like a practical **crop-health intelligence platform**:

```text
Satellite imagery
      ↓
Image preprocessing
      ↓
Field / agricultural-area detection
      ↓
Vegetation indices
      ↓
Temporal analysis
      ↓
Health classification
      ↓
Potential stress / anomaly detection
      ↓
GIS visualization
      ↓
Historical trends + alerts
```

The system must clearly distinguish between:

- **Observed satellite-derived indicators**
- **Detected vegetation decline**
- **Potential crop stress**
- **Possible causes**

Do NOT claim that satellite indices alone prove disease, nutrient deficiency, pest attack, or irrigation failure.

---

# 3. Target Users

## Primary users

1. Agricultural officers
2. Researchers
3. Agricultural organizations
4. Farmers / farm managers
5. Students and educational users

## MVP priority

Build the product primarily for **agricultural officers/researchers**, because they benefit from field-level maps, historical trends, filtering, and analytics.

---

# 4. MVP Scope

The first production-quality version must support:

### Core

- Region selection
- Agricultural field/area selection
- Satellite imagery ingestion
- NDVI calculation
- NDRE calculation where appropriate data is available
- Optional NDMI calculation
- Cloud/data-quality filtering
- Crop-health classification
- Historical time-series analysis
- Potential stress detection
- Interactive GIS map
- Field details
- Health statistics
- Alerts / flagged fields
- REST API
- ML-ready architecture

### MVP health classes

Use configurable classes rather than hard-coding universal scientific thresholds:

- Healthy
- Moderate
- Potential Stress
- Insufficient / No Data

Thresholds must be configurable by crop, region, season, or analysis profile.

---

# 5. Recommended Technology Stack

## Frontend

- React
- Vite
- TypeScript
- Tailwind CSS
- React Router
- TanStack Query
- Recharts
- Leaflet or MapLibre GL JS
- Axios
- Zod where useful

## Backend

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Validation
- Spring Data JPA
- PostgreSQL + PostGIS
- Spring Security + JWT if authentication is implemented
- OpenAPI / Swagger
- Maven

## Data / ML Service

- Python 3.11+
- FastAPI
- NumPy
- Pandas
- Rasterio
- GeoPandas
- Shapely
- scikit-learn
- PyTorch only if a deep-learning model is later required

## Satellite / Remote Sensing

Preferred data source:

- Sentinel-2 for optical vegetation monitoring

Potential future sources:

- Sentinel-1 SAR
- Landsat
- ERA5 / weather data
- Rainfall datasets
- Soil-moisture datasets

---

# 6. HIGH-LEVEL ARCHITECTURE

```text
                         ┌──────────────────────┐
                         │   Satellite Sources  │
                         │     Sentinel-2       │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │ Python Processing /  │
                         │ Remote Sensing API   │
                         └──────────┬───────────┘
                                    │
                    ┌───────────────┼────────────────┐
                    ▼               ▼                ▼
                  NDVI             NDRE              NDMI
                    │               │                │
                    └───────────────┼────────────────┘
                                    ▼
                         ┌──────────────────────┐
                         │ Feature Engineering │
                         └──────────┬───────────┘
                                    ▼
                         ┌──────────────────────┐
                         │ Health / Anomaly     │
                         │ Analysis Engine      │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │    Spring Boot API   │
                         └──────────┬───────────┘
                                    │
                          ┌─────────┴─────────┐
                          ▼                   ▼
                   PostgreSQL/PostGIS    Object Storage
                          │
                          └─────────┬─────────┘
                                    ▼
                         ┌──────────────────────┐
                         │    React GIS UI      │
                         └──────────────────────┘
```

---

# 7. REPOSITORY STRUCTURE

Create a monorepo:

```text
agrisight/
│
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── README.md
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── ml-service/
│   ├── app/
│   ├── models/
│   ├── tests/
│   ├── requirements.txt
│   └── README.md
│
├── docs/
│   ├── architecture.md
│   ├── api.md
│   └── data-pipeline.md
│
├── docker-compose.yml
├── .env.example
└── README.md
```

---

# 8. BACKEND EXECUTION PLAN

## 8.1 Backend Responsibilities

Spring Boot is responsible for:

- REST APIs
- Authentication / authorization
- Region and field management
- Satellite-analysis job management
- Storing analysis metadata
- Storing field health summaries
- Storing time-series observations
- Storing alerts
- Communicating with the Python ML/remote-sensing service
- Providing dashboard statistics
- Providing GIS-ready GeoJSON responses
- Validation and error handling

Do NOT perform heavy raster/image processing inside Spring Boot.

Heavy geospatial processing belongs in the Python service.

---

# 8.2 Backend Modules

Create these modules/packages:

```text
backend/src/main/java/com/agrisight/

├── config/
├── security/
├── auth/
├── user/
├── region/
├── field/
├── satellite/
├── analysis/
├── vegetation/
├── health/
├── alert/
├── dashboard/
├── gis/
├── ml/
├── common/
└── exception/
```

Use clean layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Use DTOs instead of exposing JPA entities directly.

---

# 8.3 Backend Database Design

Use PostgreSQL with PostGIS.

### users

```text
id
name
email
password_hash
role
created_at
updated_at
```

### regions

```text
id
name
description
boundary_geometry
created_at
```

### fields

```text
id
region_id
name
area_hectares
crop_type
geometry
created_at
updated_at
```

### satellite_scenes

```text
id
provider
scene_id
acquisition_time
cloud_cover
bbox
source_url
processing_status
created_at
```

### vegetation_observations

```text
id
field_id
scene_id
observation_date
ndvi
ndre
ndmi
data_quality
health_class
created_at
```

### health_analyses

```text
id
field_id
analysis_date
current_ndvi
previous_ndvi
ndvi_change_percent
current_ndre
current_ndmi
health_class
stress_score
confidence
analysis_method
created_at
```

### alerts

```text
id
field_id
type
severity
title
description
detected_at
status
created_at
```

---

# 8.4 Backend REST APIs

Implement APIs similar to:

## Dashboard

```http
GET /api/v1/dashboard/summary
GET /api/v1/dashboard/health-distribution
GET /api/v1/dashboard/trends
```

## Regions

```http
GET /api/v1/regions
POST /api/v1/regions
GET /api/v1/regions/{id}
```

## Fields

```http
GET /api/v1/fields
POST /api/v1/fields
GET /api/v1/fields/{id}
GET /api/v1/fields/{id}/health
GET /api/v1/fields/{id}/history
GET /api/v1/fields/{id}/geometry
```

## Analysis

```http
POST /api/v1/analysis
GET /api/v1/analysis/{id}
GET /api/v1/analysis
```

## Alerts

```http
GET /api/v1/alerts
GET /api/v1/alerts/{id}
PATCH /api/v1/alerts/{id}/status
```

## GIS

```http
GET /api/v1/gis/fields
GET /api/v1/gis/health-map
```

Return GeoJSON where appropriate.

---

# 8.5 Python Service Integration

Spring Boot sends a processing request:

```json
{
  "fieldId": "field-102",
  "startDate": "2026-06-01",
  "endDate": "2026-09-30",
  "indices": ["NDVI", "NDRE", "NDMI"]
}
```

Python service returns:

```json
{
  "fieldId": "field-102",
  "observations": [
    {
      "date": "2026-09-01",
      "ndvi": 0.71,
      "ndre": 0.42
    },
    {
      "date": "2026-09-15",
      "ndvi": 0.58,
      "ndre": 0.34
    }
  ],
  "healthClass": "POTENTIAL_STRESS",
  "stressScore": 0.78,
  "confidence": 0.86
}
```

---

# 8.6 Backend Error Handling

Create a global exception handler.

Return a consistent structure:

```json
{
  "timestamp": "...",
  "status": 400,
  "code": "INVALID_FIELD_ID",
  "message": "Field does not exist",
  "path": "/api/v1/fields/123"
}
```

---

# 8.7 Backend Testing

Implement:

- Unit tests
- Service tests
- Controller tests
- Repository tests
- Integration tests
- API validation tests

Important cases:

- Invalid field
- Empty satellite data
- Missing observations
- Invalid dates
- Invalid geometry
- No-data pixels
- Failed Python service
- Duplicate analysis
- Unauthorized access

---

# 9. FRONTEND EXECUTION PLAN

## 9.1 Frontend Design Direction

The UI must be:

- Modern
- Minimal
- Professional
- White/light theme
- GIS-focused
- Data-rich but not cluttered
- Responsive
- Accessible

Visual inspiration:

```text
Agricultural Intelligence
+
Modern GIS
+
Clean SaaS Dashboard
```

Avoid a generic admin-dashboard appearance.

---

# 9.2 Frontend Pages

Create:

```text
/login

/dashboard

/map

/fields

/fields/:id

/analysis

/alerts

/settings
```

---

# 9.3 Main Dashboard

Show:

### KPI cards

```text
Total Fields
1,248

Healthy
874

Moderate
231

Potential Stress
143
```

### Main map

Display field health:

```text
🟢 Healthy
🟡 Moderate
🔴 Potential Stress
⚪ No Data
```

### Trend chart

Show overall NDVI trend.

### Recent alerts

Example:

```text
Potential vegetation decline
Field #102
2 hours ago
```

---

# 9.4 GIS Map

The map is the heart of the application.

Features:

- Zoom
- Pan
- Field boundaries
- Health-based field coloring
- Field selection
- Search
- Region filtering
- Crop-type filtering
- Date filtering
- Health-class filtering
- Layer controls

When a field is clicked:

```text
Field #102

Crop: Rice
Area: 2.8 ha

NDVI       0.43
NDRE       0.27
Stress     78%

Status:
🔴 Potential Stress

[View Details]
```

---

# 9.5 Field Details Page

Show:

## Overview

```text
Field #102
Rice
2.8 hectares
```

## Current health

```text
Potential Stress

NDVI: 0.43
NDRE: 0.27
NDMI: 0.31
```

## Historical trend

Interactive line charts.

## Change analysis

```text
Previous NDVI: 0.61
Current NDVI: 0.43

Change: -29.5%
```

## Analysis explanation

Use careful wording:

> "A significant decline in vegetation activity has been detected compared with recent observations."

Do not claim a specific cause unless additional evidence exists.

---

# 9.6 Alerts Page

Show:

```text
HIGH
Field #102
Significant vegetation decline

MEDIUM
Field #217
Moderate NDVI decrease

LOW
Field #431
Insufficient recent observations
```

Filters:

- Severity
- Date
- Region
- Status
- Crop type

---

# 9.7 Frontend Components

Suggested structure:

```text
components/
├── layout/
├── dashboard/
├── map/
├── fields/
├── charts/
├── alerts/
├── common/
└── ui/
```

Important reusable components:

```text
HealthBadge
MetricCard
FieldMap
FieldPopup
NDVIChart
HealthDistributionChart
TrendChart
AlertCard
DateRangePicker
FilterPanel
LoadingState
EmptyState
ErrorState
```

---

# 10. DATA PROCESSING PIPELINE

## Step 1 — Acquire satellite data

Use Sentinel-2 imagery.

Prefer scenes with acceptable cloud coverage.

## Step 2 — Preprocess

Perform:

- Data-quality filtering
- Cloud masking
- Band alignment/resampling when required
- Geometry clipping
- Field boundary clipping

## Step 3 — Calculate indices

NDVI:

```text
(NIR - Red) / (NIR + Red)
```

NDRE:

```text
(NIR - RedEdge) / (NIR + RedEdge)
```

NDMI if used:

```text
(NIR - SWIR) / (NIR + SWIR)
```

## Step 4 — Aggregate to fields

For each field calculate statistics such as:

```text
mean NDVI
median NDVI
minimum NDVI
maximum NDVI
standard deviation
valid pixel percentage
```

## Step 5 — Time-series analysis

Store observations:

```text
Field
 ↓
Date
 ↓
NDVI
NDRE
NDMI
```

## Step 6 — Detect change

Calculate:

```text
absolute change
percentage change
rolling trend
rate of decline
anomaly from historical baseline
```

## Step 7 — Health classification

Use configurable rules initially.

Later support ML.

## Step 8 — Generate alert

If a significant decline is detected:

```text
Potential stress alert
```

---

# 11. ML ROADMAP

Do NOT start with a complicated deep-learning model.

## Phase 1

Rule-based classification.

## Phase 2

Anomaly detection.

Possible approaches:

- Isolation Forest
- Local Outlier Factor
- statistical z-score / baseline deviation

## Phase 3

Supervised ML.

Potential features:

```text
NDVI
NDRE
NDMI
NDVI change
NDRE change
NDMI change
temperature
rainfall
crop type
growth stage
```

Possible models:

- Random Forest
- XGBoost
- LightGBM

Only train a model when reliable labeled/reference data is available.

---

# 12. IMPORTANT DATA QUALITY RULES

The system must handle:

- Clouds
- Cloud shadows
- Missing observations
- Bad pixels
- Different crop growth stages
- Seasonal variation
- Harvest periods
- Bare soil
- Field boundary errors

Never interpret a sudden NDVI drop as stress without checking data quality.

For every analysis store:

```text
data_quality
valid_pixel_percentage
observation_date
analysis_method
confidence
```

---

# 13. DEVELOPMENT PHASES

## Phase 1 — Foundation

- Create monorepo
- Create React frontend
- Create Spring Boot backend
- Create Python service
- Configure PostgreSQL/PostGIS
- Configure Docker Compose
- Create environment configuration

## Phase 2 — Backend

- Entities
- DTOs
- Repositories
- Services
- Controllers
- Validation
- Exception handling
- GeoJSON endpoints
- Swagger

## Phase 3 — Satellite Processing

- Sentinel-2 ingestion
- Band processing
- Cloud filtering
- NDVI
- NDRE
- Optional NDMI
- Field aggregation

## Phase 4 — Analysis

- Time-series storage
- Trend calculation
- Change detection
- Health classification
- Stress scoring
- Alert generation

## Phase 5 — Frontend

- Layout
- Dashboard
- GIS map
- Field details
- Charts
- Alerts
- Filters
- Loading/error/empty states

## Phase 6 — Integration

```text
React
 ↓
Spring Boot
 ↓
Python
 ↓
Satellite processing
 ↓
PostGIS
 ↓
Spring Boot
 ↓
React
```

## Phase 7 — Quality

- Testing
- Performance
- Security
- Error handling
- Data validation
- UI polish

## Phase 8 — Deployment

Suggested:

```text
Frontend → Vercel / Firebase
Backend → Render / Railway / VPS
Python → Docker service
Database → PostgreSQL/PostGIS
Object storage → S3-compatible storage
```

---

# 14. DEMO FLOW

The final demonstration should follow this story:

### Step 1

Open AgriSight.

### Step 2

Select a region.

### Step 3

Show the agricultural field map.

### Step 4

Filter:

```text
Health = Potential Stress
```

### Step 5

Click a field.

### Step 6

Show:

```text
Current NDVI
Current NDRE
Historical trend
Change percentage
Health class
Stress score
```

### Step 7

Open historical analysis.

Show how the field changed over several observations.

### Step 8

Show the alert.

### Step 9

Explain:

> "The system has detected a significant decline in vegetation activity. This is classified as potential crop stress and should be investigated using field observations and additional environmental data."

This is a much more scientifically responsible and convincing demo.

---

# 15. SECURITY

Implement:

- JWT authentication if multi-user access is needed
- Role-based authorization
- Input validation
- CORS configuration
- Rate limiting where appropriate
- Secure environment variables
- No API keys in frontend
- No secrets committed to Git
- Audit logging for important operations

---

# 16. PERFORMANCE

The frontend must not download huge raster files unnecessarily.

Prefer:

```text
Satellite raster
      ↓
Python processing
      ↓
Aggregated field statistics
      ↓
API
      ↓
React
```

For map visualization, use:

- GeoJSON for manageable field boundaries
- Vector tiles or optimized layers if scale becomes large
- Server-side filtering
- Pagination
- Cached analysis results

---

# 17. OBSERVABILITY

Backend should log:

- API requests
- Analysis jobs
- Python service failures
- Processing duration
- Database failures

Analysis records should include:

```text
processing_time
data_source
scene_id
analysis_method
model_version
confidence
```

---

# 18. FUTURE FEATURES

After MVP:

### Agricultural drought

Integrate:

- Rainfall
- LST
- Soil moisture
- NDMI

### Crop identification

Detect crop type.

### Yield estimation

Estimate crop yield using satellite + weather + historical data.

### Irrigation detection

Extend toward Problem 3.4.

### Disease detection

Use higher-resolution imagery and field/ground-truth data.

### Mobile app

Farmer-facing Android application.

### Notifications

Email / SMS / WhatsApp alerts.

### AI assistant

Allow users to ask:

> "Which fields showed the largest decline this month?"

> "Show stressed rice fields."

> "Which fields have continuously declining NDVI?"

---

# 19. SUCCESS CRITERIA

The MVP is successful when a user can:

1. Open the dashboard.
2. Select a region.
3. View agricultural fields on a map.
4. See health classification.
5. Select a field.
6. View NDVI/NDRE values.
7. View historical trends.
8. Detect significant vegetation decline.
9. See a potential-stress alert.
10. Understand why the field was flagged.
11. Filter and compare fields.

---

# 20. MASTER BUILD PROMPT FOR ANTIGRAVITY

Use the following prompt after giving Antigravity this execution-plan file.

---

## MASTER PROMPT

You are the lead software architect and senior full-stack engineer responsible for implementing **AgriSight — Satellite Crop Health Intelligence Platform**.

Read the entire `AGRI_SIGHT_EXECUTION_PLAN.md` before writing code.

Your objective is to build a production-quality MVP, not a mockup.

### Core instruction

Implement the project according to the architecture and execution plan in the document.

Do not randomly change the architecture.

The system must contain:

1. React frontend
2. Spring Boot backend
3. Python remote-sensing/ML service
4. PostgreSQL + PostGIS
5. Satellite-data processing pipeline
6. GIS visualization
7. Crop-health analysis
8. Historical time-series analysis
9. Potential stress detection
10. Alerts

### Engineering principles

- Write clean, maintainable code.
- Use clear package/module separation.
- Use DTOs in the backend.
- Validate all API inputs.
- Handle errors consistently.
- Never expose secrets.
- Use environment variables.
- Write meaningful comments only where necessary.
- Avoid unnecessary complexity.
- Do not create fake AI functionality disguised as real analysis.
- Do not hard-code fake satellite results as if they were real.
- Build a clean abstraction so real satellite data can be connected.
- If external satellite APIs require credentials, create a provider interface and a clearly documented development/mock provider without pretending the mock data is real satellite data.
- Keep the application runnable locally.
- Use Docker where useful.
- Add README documentation.
- Add tests for important backend and processing logic.

### Development order

Work in this order:

#### Phase A
Set up repository and project structure.

#### Phase B
Implement database schema and backend foundations.

#### Phase C
Implement Python remote-sensing service.

#### Phase D
Implement NDVI/NDRE processing and field aggregation.

#### Phase E
Implement historical analysis and stress detection.

#### Phase F
Implement backend APIs.

#### Phase G
Implement React dashboard.

#### Phase H
Implement GIS map.

#### Phase I
Integrate all services.

#### Phase J
Test and polish.

### Important scientific requirement

Do not state that NDVI alone proves disease, pests, nutrient deficiency, or a specific cause of crop stress.

Use language such as:

- Potential Stress
- Vegetation Decline
- Anomaly Detected
- Requires Field Verification

### UI requirement

The UI must look like a modern professional agricultural intelligence product.

Use:

- White/light theme
- Clean typography
- Strong visual hierarchy
- Spacious layout
- Professional GIS interface
- Clear charts
- Minimal visual clutter
- Responsive design
- Excellent loading and empty states

Do not build a generic dashboard template.

### GIS requirement

The map is a core product feature, not an optional page.

Users must be able to:

- Search fields
- Filter fields
- Click fields
- View health status
- Open field details
- View historical trends

### Do not stop at scaffolding

Do not only create empty files or placeholder components.

Implement the actual functionality progressively.

If a real external satellite data source cannot be connected without credentials, build the complete processing abstraction and local test dataset path so the system remains functional and easy to connect to real data.

### Final deliverables

Produce:

```text
frontend/
backend/
ml-service/
docs/
docker-compose.yml
.env.example
README.md
```

Also document:

- How to run the system
- How to configure environment variables
- How to connect real Sentinel-2 data
- API documentation
- Database setup
- Python service setup
- Frontend setup
- Testing commands
- Deployment instructions

Before finishing, verify that:

- Frontend builds
- Backend builds
- Python service starts
- Database starts
- APIs respond
- Frontend can communicate with backend
- Backend can communicate with Python service
- GIS map renders
- Field details work
- Historical charts work
- Health classification works
- Stress alerts work

Do not claim a feature is complete unless it is actually implemented and tested.

---

# 21. FRONTEND-ONLY PROMPT

Use this if Antigravity is working specifically on the frontend.

> Build only the AgriSight React frontend according to the frontend section of `AGRI_SIGHT_EXECUTION_PLAN.md`.
>
> Use React + Vite + TypeScript + Tailwind CSS.
>
> Build a polished professional agricultural intelligence dashboard with:
>
> - Dashboard
> - GIS map
> - Field details
> - Historical NDVI/NDRE charts
> - Health classification
> - Alerts
> - Filters
> - Search
> - Responsive layout
> - Loading states
> - Empty states
> - Error states
>
> Use realistic development data only where backend APIs are not yet available, and clearly isolate mock data from API integration.
>
> Do not create a generic admin dashboard.
>
> The main user journey must be:
>
> `Dashboard → Map → Select Field → Field Details → Historical Trend → Potential Stress Alert`
>
> Keep the UI clean, white, minimal, modern, GIS-oriented, and professional.
>
> Prepare API service abstractions so the frontend can connect directly to the Spring Boot backend once the endpoints are available.
>
> Do not implement backend logic inside the frontend.

---

# 22. BACKEND-ONLY PROMPT

Use this if Antigravity is working specifically on the backend.

> Build only the AgriSight backend according to the backend section of `AGRI_SIGHT_EXECUTION_PLAN.md`.
>
> Use:
>
> - Java 21
> - Spring Boot 3.x
> - Maven
> - PostgreSQL
> - PostGIS
> - Spring Data JPA
> - Spring Validation
> - Spring Security/JWT where authentication is enabled
> - OpenAPI/Swagger
>
> Implement:
>
> - Region management
> - Field management
> - Satellite scene metadata
> - Vegetation observations
> - Health analysis
> - Historical trends
> - Stress detection
> - Alerts
> - Dashboard statistics
> - GeoJSON GIS APIs
> - Python-service integration
>
> Use:
>
> `Controller → Service → Repository`
>
> Use DTOs and validation.
>
> Do not expose entities directly.
>
> Implement global exception handling.
>
> Return consistent API responses.
>
> Use PostGIS for field geometry.
>
> Do not perform heavy raster processing inside Spring Boot.
>
> The Python service must own satellite image processing and vegetation-index calculations.
>
> Build a clean interface for communication with the Python service.
>
> Add unit and integration tests.
>
> Add Swagger/OpenAPI documentation.
>
> Keep all secrets in environment variables.
>
> The backend must be production-oriented and ready for the React frontend to consume.

---

# 23. FIRST IMPLEMENTATION PRIORITY

If the entire project is too large to implement in one pass, prioritize this exact vertical slice:

```text
Sentinel-2/local development dataset
          ↓
       NDVI
          ↓
     Field aggregation
          ↓
 Health classification
          ↓
 Spring Boot API
          ↓
 React GIS map
          ↓
 Field details
          ↓
 Historical NDVI chart
          ↓
 Potential stress alert
```

Get this end-to-end flow working first.

Then add:

```text
NDRE
 ↓
NDMI
 ↓
Better temporal analysis
 ↓
ML anomaly detection
 ↓
Additional data sources
```

The final result should feel like a real **satellite agricultural intelligence product**, not a college project consisting of a few charts and an NDVI formula.
