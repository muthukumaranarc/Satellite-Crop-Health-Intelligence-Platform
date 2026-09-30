export type HealthClass = 
  | 'HEALTHY' 
  | 'MODERATE' 
  | 'POTENTIAL_STRESS' 
  | 'INSUFFICIENT_DATA' 
  | 'NO_DATA';

export type AlertSeverity = 'HIGH' | 'MEDIUM' | 'LOW';
export type AlertStatus = 'ACTIVE' | 'INVESTIGATING' | 'RESOLVED';

export interface FieldPolygon {
  type: 'Feature';
  properties: {
    id: string;
    name: string;
    block: string;
    crop: string;
    areaHa: number;
    ndvi: number;
    ndre: number;
    healthClass: HealthClass;
    stressScore: number;
    lastObservation: string;
  };
  geometry: {
    type: 'Polygon';
    coordinates: number[][][]; // [ [ [lng, lat], ... ] ]
  };
}

export interface Field {
  id: string;
  name: string;
  block: string;
  zone?: string;
  region: string;
  crop: string;
  areaHa: number;
  currentNdvi: number;
  currentNdre: number;
  currentNdmi?: number;
  stressScore: number; // 0 - 100
  healthClass: HealthClass;
  ndviChangePercent: number; // e.g. -29.5
  validPixelPercent: number;
  lastObservationDate: string;
  polygon: FieldPolygon;
}

export interface StressAlert {
  id: string;
  fieldId: string;
  fieldName: string;
  block: string;
  zone?: string;
  title: string;
  description: string;
  timeAgo: string;
  severity: AlertSeverity;
  status: AlertStatus;
  detectedAt: string;
  ndviDropPercent?: number;
  currentNdvi: number;
  previousNdvi: number;
  stressScore: number;
}

export interface RecentImageryScene {
  id: string;
  satellite: string; // e.g. "Sentinel-2 L2A"
  acquisitionDate: string; // "May 20, 2024"
  acquisitionTime: string; // "10:15 AM"
  cloudCoverPercent: number; // e.g. 12
  previewUrl: string;
  resolution: string;
  sunElevation: number;
}

export interface NDVITrendDataPoint {
  date: string; // "Apr 20", "Apr 30", etc.
  fullDate: string;
  ndvi: number;
  ndre?: number;
  baseline?: number;
}

export interface HealthPeriodDataPoint {
  period: string; // "Mar 15", "Mar 30", etc.
  healthy: number; // percentage (0 - 100)
  moderate: number;
  potentialStress: number;
  insufficientData: number;
}

export interface QuickInsightItem {
  id: string;
  label: string;
  value: string;
  unit?: string;
  changeText: string;
  changeValue: number;
  isIncreasePositive: boolean;
  isPositive: boolean;
  type: 'healthy' | 'stress' | 'alerts' | 'cloud';
}

export interface HealthSummaryMetrics {
  totalAreaHa: number;
  healthyHa: number;
  healthyPercent: number;
  moderateHa: number;
  moderatePercent: number;
  potentialStressHa: number;
  potentialStressPercent: number;
  insufficientDataHa: number;
  insufficientDataPercent: number;
}

export interface GlobalFilterState {
  region: string;
  field: string;
  crop: string;
  dateRange: string;
}

export interface MapLayerState {
  index: 'NDVI' | 'NDRE' | 'NDMI';
  date: string;
  opacity: number; // 0 to 1
  basemap: 'satellite' | 'street';
}
