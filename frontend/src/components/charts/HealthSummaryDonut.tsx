import React from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer } from 'recharts';
import { HealthSummaryMetrics } from '../../types';

interface HealthSummaryDonutProps {
  metrics: HealthSummaryMetrics;
  onViewAll?: () => void;
}

export const HealthSummaryDonut: React.FC<HealthSummaryDonutProps> = ({
  metrics,
  onViewAll,
}) => {
  const data = [
    { name: 'Healthy', value: metrics.healthyHa, percent: metrics.healthyPercent, color: '#22c55e' },
    { name: 'Moderate', value: metrics.moderateHa, percent: metrics.moderatePercent, color: '#eab308' },
    { name: 'Potential Stress', value: metrics.potentialStressHa, percent: metrics.potentialStressPercent, color: '#f97316' },
    { name: 'Insufficient / No Data', value: metrics.insufficientDataHa, percent: metrics.insufficientDataPercent, color: '#ef4444' },
  ];

  return (
    <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-col justify-between">
      {/* Header */}
      <div className="flex items-center justify-between mb-3">
        <h3 className="text-sm font-bold text-slate-800 tracking-tight">Health Summary</h3>
        <button
          onClick={onViewAll}
          className="text-xs font-medium text-slate-500 hover:text-emerald-700 transition-colors"
        >
          View All
        </button>
      </div>

      {/* Content: Chart + Right Legend */}
      <div className="flex items-center justify-between">
        {/* Donut Chart with Center Text */}
        <div className="relative w-36 h-36 shrink-0">
          <ResponsiveContainer width="100%" height="100%">
            <PieChart>
              <Pie
                data={data}
                innerRadius={46}
                outerRadius={64}
                paddingAngle={3}
                dataKey="value"
                stroke="none"
              >
                {data.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={entry.color} />
                ))}
              </Pie>
            </PieChart>
          </ResponsiveContainer>

          {/* Centered Area Label */}
          <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none">
            <span className="text-base font-extrabold text-slate-800 leading-tight">
              {metrics.totalAreaHa.toLocaleString()} ha
            </span>
            <span className="text-[10.5px] font-medium text-slate-400">Total Area</span>
          </div>
        </div>

        {/* Legend on the Right */}
        <div className="flex-1 pl-4 space-y-2 text-xs">
          {data.map((item) => (
            <div key={item.name} className="flex items-start justify-between">
              <div className="flex items-center space-x-2">
                <span
                  className="w-2.5 h-2.5 rounded-full shrink-0"
                  style={{ backgroundColor: item.color }}
                />
                <span className="text-slate-700 font-medium">{item.name}</span>
              </div>
              <div className="text-right text-slate-500 font-normal">
                {item.value.toLocaleString()} ha ({item.percent}%)
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
