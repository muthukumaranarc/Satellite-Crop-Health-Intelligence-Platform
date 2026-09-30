import { 
  NDVITrendDataPoint, 
  HealthPeriodDataPoint, 
  RecentImageryScene, 
  QuickInsightItem 
} from '../types';

export const MOCK_NDVI_TREND: NDVITrendDataPoint[] = [
  { date: 'Apr 20', fullDate: '2024-04-20', ndvi: 0.48, ndre: 0.28 },
  { date: 'Apr 23', fullDate: '2024-04-23', ndvi: 0.53, ndre: 0.31 },
  { date: 'Apr 26', fullDate: '2024-04-26', ndvi: 0.61, ndre: 0.36 },
  { date: 'Apr 30', fullDate: '2024-04-30', ndvi: 0.63, ndre: 0.38 },
  { date: 'May 03', fullDate: '2024-05-03', ndvi: 0.66, ndre: 0.40 },
  { date: 'May 06', fullDate: '2024-05-06', ndvi: 0.68, ndre: 0.42 },
  { date: 'May 08', fullDate: '2024-05-08', ndvi: 0.65, ndre: 0.39 },
  { date: 'May 10', fullDate: '2024-05-10', ndvi: 0.71, ndre: 0.44 },
  { date: 'May 13', fullDate: '2024-05-13', ndvi: 0.69, ndre: 0.41 },
  { date: 'May 16', fullDate: '2024-05-16', ndvi: 0.64, ndre: 0.37 },
  { date: 'May 18', fullDate: '2024-05-18', ndvi: 0.58, ndre: 0.33 },
  { date: 'May 20', fullDate: '2024-05-20', ndvi: 0.52, ndre: 0.29 },
];

export const MOCK_HEALTH_PERIODS: HealthPeriodDataPoint[] = [
  {
    period: 'Mar 15',
    healthy: 32,
    moderate: 40,
    potentialStress: 18,
    insufficientData: 10,
  },
  {
    period: 'Mar 30',
    healthy: 38,
    moderate: 37,
    potentialStress: 16,
    insufficientData: 9,
  },
  {
    period: 'Apr 15',
    healthy: 46,
    moderate: 33,
    potentialStress: 14,
    insufficientData: 7,
  },
  {
    period: 'Apr 30',
    healthy: 52,
    moderate: 29,
    potentialStress: 13,
    insufficientData: 6,
  },
  {
    period: 'May 10',
    healthy: 49,
    moderate: 30,
    potentialStress: 15,
    insufficientData: 6,
  },
  {
    period: 'May 20',
    healthy: 45.3,
    moderate: 29.3,
    potentialStress: 19.2,
    insufficientData: 6.2,
  },
];

export const MOCK_RECENT_IMAGERY: RecentImageryScene = {
  id: 'S2A_MSIL2A_20240520T053641_N0510_R005_T43RER',
  satellite: 'Sentinel-2 L2A',
  acquisitionDate: 'May 20, 2024',
  acquisitionTime: '10:15 AM',
  cloudCoverPercent: 12,
  // High quality Sentinel-2 satellite optical tile preview
  previewUrl: 'https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=400&q=80',
  resolution: '10m',
  sunElevation: 68.4,
};

export const MOCK_QUICK_INSIGHTS: QuickInsightItem[] = [
  {
    id: 'qi-1',
    label: 'Healthy Area',
    value: '1,152',
    unit: 'ha',
    changeText: '8.4%',
    changeValue: 8.4,
    isIncreasePositive: true,
    isPositive: true,
    type: 'healthy',
  },
  {
    id: 'qi-2',
    label: 'Potential Stress Area',
    value: '487',
    unit: 'ha',
    changeText: '12.7%',
    changeValue: 12.7,
    isIncreasePositive: false,
    isPositive: false,
    type: 'stress',
  },
  {
    id: 'qi-3',
    label: 'Total Alerts',
    value: '12',
    unit: '',
    changeText: '2',
    changeValue: 2,
    isIncreasePositive: false,
    isPositive: false,
    type: 'alerts',
  },
  {
    id: 'qi-4',
    label: 'Avg. Cloud Cover',
    value: '18%',
    unit: '',
    changeText: '6%',
    changeValue: -6,
    isIncreasePositive: false,
    isPositive: true,
    type: 'cloud',
  },
];
