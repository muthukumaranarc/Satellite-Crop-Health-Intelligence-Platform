import React from 'react';
import { AlertTriangle, ArrowRight } from 'lucide-react';
import { StressAlert } from '../../types';

interface PotentialStressAlertsProps {
  alerts: StressAlert[];
  onSelectAlert?: (alert: StressAlert) => void;
  onViewAll?: () => void;
}

export const PotentialStressAlerts: React.FC<PotentialStressAlertsProps> = ({
  alerts,
  onSelectAlert,
  onViewAll,
}) => {
  return (
    <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-col justify-between">
      {/* Header */}
      <div className="flex items-center justify-between mb-2.5">
        <h3 className="text-sm font-bold text-slate-800 tracking-tight">Potential Stress Alerts</h3>
        <button
          onClick={onViewAll}
          className="text-xs font-medium text-slate-500 hover:text-emerald-700 transition-colors"
        >
          View All
        </button>
      </div>

      {/* Alerts List */}
      <div className="space-y-2.5 flex-1">
        {alerts.slice(0, 4).map((alert) => (
          <div
            key={alert.id}
            onClick={() => onSelectAlert?.(alert)}
            className="group flex items-start justify-between p-2 rounded-lg hover:bg-slate-50 transition-colors cursor-pointer"
          >
            <div className="flex items-start space-x-2.5">
              <div className="w-6 h-6 rounded-md bg-red-50 border border-red-200 text-red-500 flex items-center justify-center shrink-0 mt-0.5">
                <AlertTriangle className="w-3.5 h-3.5" />
              </div>
              <div>
                <p className="text-xs font-semibold text-slate-800 group-hover:text-emerald-700 transition-colors">
                  {alert.fieldName} — {alert.block}
                </p>
                <p className="text-[11px] text-slate-500 mt-0.5">{alert.title}</p>
              </div>
            </div>

            <span className="text-[10.5px] font-normal text-slate-400 shrink-0 ml-2 mt-0.5">
              {alert.timeAgo}
            </span>
          </div>
        ))}
      </div>

      {/* Footer Link */}
      <div className="pt-2 border-t border-slate-100 mt-2 text-center">
        <button
          onClick={onViewAll}
          className="inline-flex items-center space-x-1.5 text-xs font-semibold text-emerald-700 hover:text-emerald-800 transition-colors"
        >
          <span>View All Alerts</span>
          <ArrowRight className="w-3.5 h-3.5" />
        </button>
      </div>
    </div>
  );
};
