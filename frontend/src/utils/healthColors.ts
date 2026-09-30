import { HealthClass } from '../types';

export const HEALTH_COLORS = {
  HEALTHY: {
    fill: '#22c55e',
    stroke: '#16a34a',
    text: 'text-green-600',
    bg: 'bg-green-500',
    badgeBg: 'bg-green-50',
    badgeBorder: 'border-green-200',
    badgeText: 'text-green-700',
    label: 'Healthy (0.6 - 1.0)',
    shortLabel: 'Healthy',
  },
  MODERATE: {
    fill: '#eab308',
    stroke: '#ca8a04',
    text: 'text-yellow-600',
    bg: 'bg-yellow-500',
    badgeBg: 'bg-yellow-50',
    badgeBorder: 'border-yellow-200',
    badgeText: 'text-yellow-700',
    label: 'Moderate (0.4 - 0.6)',
    shortLabel: 'Moderate',
  },
  POTENTIAL_STRESS: {
    fill: '#f97316',
    stroke: '#ea580c',
    text: 'text-orange-600',
    bg: 'bg-orange-500',
    badgeBg: 'bg-orange-50',
    badgeBorder: 'border-orange-200',
    badgeText: 'text-orange-700',
    label: 'Potential Stress (0.2 - 0.4)',
    shortLabel: 'Potential Stress',
  },
  INSUFFICIENT_DATA: {
    fill: '#ef4444',
    stroke: '#dc2626',
    text: 'text-red-600',
    bg: 'bg-red-500',
    badgeBg: 'bg-red-50',
    badgeBorder: 'border-red-200',
    badgeText: 'text-red-700',
    label: 'Insufficient / No Data (< 0.2)',
    shortLabel: 'Insufficient / No Data',
  },
  NO_DATA: {
    fill: '#64748b',
    stroke: '#475569',
    text: 'text-slate-500',
    bg: 'bg-slate-500',
    badgeBg: 'bg-slate-50',
    badgeBorder: 'border-slate-200',
    badgeText: 'text-slate-700',
    label: 'No Data',
    shortLabel: 'No Data',
  },
};

export function getHealthClassFromNdvi(ndvi: number | undefined | null): HealthClass {
  if (ndvi === undefined || ndvi === null || isNaN(ndvi)) return 'NO_DATA';
  if (ndvi >= 0.6) return 'HEALTHY';
  if (ndvi >= 0.4) return 'MODERATE';
  if (ndvi >= 0.2) return 'POTENTIAL_STRESS';
  return 'INSUFFICIENT_DATA';
}

export function getHealthColorStyle(healthClass: HealthClass) {
  return HEALTH_COLORS[healthClass] || HEALTH_COLORS.NO_DATA;
}
