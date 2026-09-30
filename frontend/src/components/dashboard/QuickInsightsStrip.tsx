import React from 'react';
import { Sprout, TrendingUp, Bell, Cloud, ArrowUpRight, ArrowDownRight } from 'lucide-react';
import { QuickInsightItem } from '../../types';

interface QuickInsightsStripProps {
  insights: QuickInsightItem[];
}

export const QuickInsightsStrip: React.FC<QuickInsightsStripProps> = ({ insights }) => {
  return (
    <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card">
      <div className="text-sm font-bold text-slate-800 mb-3 tracking-tight">Quick Insights</div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {insights.map((item) => {
          let IconComponent = Sprout;
          let iconBg = 'bg-emerald-50 text-emerald-600 border border-emerald-200';
          let trendColor = 'text-emerald-600';

          if (item.type === 'healthy') {
            IconComponent = Sprout;
            iconBg = 'bg-emerald-50 text-emerald-600 border border-emerald-200';
            trendColor = item.isPositive ? 'text-emerald-600' : 'text-red-600';
          } else if (item.type === 'stress') {
            IconComponent = TrendingUp;
            iconBg = 'bg-orange-50 text-orange-600 border border-orange-200';
            // Increase in stress is warning (red/orange)
            trendColor = 'text-red-500';
          } else if (item.type === 'alerts') {
            IconComponent = Bell;
            iconBg = 'bg-red-50 text-red-500 border border-red-200';
            trendColor = 'text-red-500';
          } else if (item.type === 'cloud') {
            IconComponent = Cloud;
            iconBg = 'bg-sky-50 text-sky-600 border border-sky-200';
            trendColor = 'text-emerald-600';
          }

          const isUp = item.changeValue >= 0;

          return (
            <div
              key={item.id}
              className="flex items-center space-x-3.5 p-2.5 rounded-lg hover:bg-slate-50/80 transition-colors"
            >
              {/* Icon badge */}
              <div className={`w-10 h-10 rounded-full flex items-center justify-center shrink-0 ${iconBg}`}>
                <IconComponent className="w-5 h-5" />
              </div>

              {/* Metric Details */}
              <div className="flex-1">
                <p className="text-[11px] font-medium text-slate-500">{item.label}</p>
                <div className="flex items-baseline space-x-2 mt-0.5">
                  <span className="text-base font-bold text-slate-900 tracking-tight">
                    {item.value} {item.unit && <span className="text-xs font-normal text-slate-500">{item.unit}</span>}
                  </span>
                  
                  {/* Delta indicator */}
                  <span className={`inline-flex items-center text-[10.5px] font-semibold ${trendColor}`}>
                    {isUp ? (
                      <ArrowUpRight className="w-3 h-3 stroke-[2.5]" />
                    ) : (
                      <ArrowDownRight className="w-3 h-3 stroke-[2.5]" />
                    )}
                    <span>{item.changeText}</span>
                  </span>
                </div>
                <p className="text-[10px] text-slate-400 mt-0.5">vs last period</p>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
