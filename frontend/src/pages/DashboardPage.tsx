import React from 'react';
import { useNavigate } from 'react-router-dom';
import { SatelliteGisMap } from '../components/map/SatelliteGisMap';
import { HealthSummaryDonut } from '../components/charts/HealthSummaryDonut';
import { PotentialStressAlerts } from '../components/dashboard/PotentialStressAlerts';
import { RecentImageryCard } from '../components/dashboard/RecentImageryCard';
import { NDVITrendLineChart } from '../components/charts/NDVITrendLineChart';
import { HealthOverTimeBars } from '../components/charts/HealthOverTimeBars';
import { QuickInsightsStrip } from '../components/dashboard/QuickInsightsStrip';

import { MALWA_HEALTH_SUMMARY } from '../mock/malwaData';
import { MOCK_STRESS_ALERTS } from '../mock/alertsData';
import { MOCK_RECENT_IMAGERY, MOCK_NDVI_TREND, MOCK_HEALTH_PERIODS, MOCK_QUICK_INSIGHTS } from '../mock/trendsData';
import { useMapState } from '../context/MapStateContext';

export const DashboardPage: React.FC = () => {
  const navigate = useNavigate();
  const { setSelectedFieldId } = useMapState();

  const handleSelectAlert = (alert: any) => {
    setSelectedFieldId(alert.fieldId);
  };

  const handleNavigateToDetail = (fieldId: string) => {
    navigate(`/fields/${fieldId}`);
  };

  return (
    <div className="space-y-4 max-w-[1600px] mx-auto pb-4">
      {/* Top Section: GIS Map (Left) + Right Column Cards */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-4 items-stretch">
        {/* Main Central Interactive Satellite GIS Map */}
        <div className="lg:col-span-8 min-h-[480px] flex flex-col">
          <SatelliteGisMap onNavigateToDetail={handleNavigateToDetail} className="h-full flex-1" />
        </div>

        {/* Right Summary Column: Donut + Alerts + Recent Imagery */}
        <div className="lg:col-span-4 flex flex-col space-y-4 justify-between">
          <HealthSummaryDonut
            metrics={MALWA_HEALTH_SUMMARY}
            onViewAll={() => navigate('/fields')}
          />
          <PotentialStressAlerts
            alerts={MOCK_STRESS_ALERTS}
            onSelectAlert={handleSelectAlert}
            onViewAll={() => navigate('/alerts')}
          />
          <RecentImageryCard
            scene={MOCK_RECENT_IMAGERY}
            onViewAll={() => navigate('/imagery')}
          />
        </div>
      </div>

      {/* Middle Section: NDVI Trend (30 Days) + Health Over Time Stacked Bars */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-4">
        <div className="lg:col-span-6">
          <NDVITrendLineChart data={MOCK_NDVI_TREND} />
        </div>
        <div className="lg:col-span-6">
          <HealthOverTimeBars data={MOCK_HEALTH_PERIODS} />
        </div>
      </div>

      {/* Bottom Section: Quick Insights 4-Metric Strip */}
      <div>
        <QuickInsightsStrip insights={MOCK_QUICK_INSIGHTS} />
      </div>
    </div>
  );
};
