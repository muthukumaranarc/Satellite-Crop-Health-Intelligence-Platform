import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MOCK_STRESS_ALERTS } from '../mock/alertsData';
import { StressAlert, AlertSeverity, AlertStatus } from '../types';
import { AlertTriangle, CheckCircle2, Clock, Filter, ArrowRight, ShieldAlert } from 'lucide-react';

export const AlertsPage: React.FC = () => {
  const navigate = useNavigate();
  const [alerts, setAlerts] = useState<StressAlert[]>(MOCK_STRESS_ALERTS);
  const [selectedSeverity, setSelectedSeverity] = useState<string>('ALL');
  const [selectedStatus, setSelectedStatus] = useState<string>('ALL');

  const filteredAlerts = alerts.filter((a) => {
    const matchesSev = selectedSeverity === 'ALL' || a.severity === selectedSeverity;
    const matchesStatus = selectedStatus === 'ALL' || a.status === selectedStatus;
    return matchesSev && matchesStatus;
  });

  const handleUpdateStatus = (id: string, newStatus: AlertStatus) => {
    setAlerts((prev) =>
      prev.map((a) => (a.id === id ? { ...a, status: newStatus } : a))
    );
  };

  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      {/* Header Banner */}
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex flex-wrap items-center justify-between gap-4">
        <div>
          <div className="flex items-center space-x-2.5">
            <div className="w-8 h-8 rounded-lg bg-red-50 border border-red-200 text-red-600 flex items-center justify-center">
              <ShieldAlert className="w-5 h-5" />
            </div>
            <h1 className="text-lg font-bold text-slate-900 tracking-tight">Potential Crop Stress Triage</h1>
          </div>
          <p className="text-xs text-slate-500 mt-1">
            Automated remote-sensing alerts triggered by sudden NDVI declines or prolonged sub-baseline vegetation indices.
          </p>
        </div>

        <div className="flex items-center space-x-2">
          <select
            value={selectedSeverity}
            onChange={(e) => setSelectedSeverity(e.target.value)}
            className="bg-slate-50 border border-slate-200 rounded-lg px-3 py-1.5 text-xs font-semibold text-slate-700 focus:outline-none"
          >
            <option value="ALL">All Severities</option>
            <option value="HIGH">High Severity</option>
            <option value="MEDIUM">Medium Severity</option>
            <option value="LOW">Low Severity</option>
          </select>

          <select
            value={selectedStatus}
            onChange={(e) => setSelectedStatus(e.target.value)}
            className="bg-slate-50 border border-slate-200 rounded-lg px-3 py-1.5 text-xs font-semibold text-slate-700 focus:outline-none"
          >
            <option value="ALL">All Statuses</option>
            <option value="ACTIVE">Active</option>
            <option value="INVESTIGATING">Investigating</option>
            <option value="RESOLVED">Resolved</option>
          </select>
        </div>
      </div>

      {/* Alert Feed */}
      <div className="space-y-3">
        {filteredAlerts.map((alert) => (
          <div
            key={alert.id}
            className="bg-white rounded-xl border border-slate-200 p-4 shadow-card hover:shadow-md transition-all flex flex-col sm:flex-row sm:items-center justify-between gap-4"
          >
            <div className="flex items-start space-x-3.5 flex-1">
              <div
                className={`w-9 h-9 rounded-xl flex items-center justify-center shrink-0 mt-0.5 ${
                  alert.severity === 'HIGH'
                    ? 'bg-red-50 border border-red-200 text-red-600'
                    : 'bg-amber-50 border border-amber-200 text-amber-600'
                }`}
              >
                <AlertTriangle className="w-4 h-4" />
              </div>

              <div>
                <div className="flex flex-wrap items-center gap-2">
                  <h3 className="font-bold text-slate-900 text-sm">{alert.fieldName}</h3>
                  <span className="text-xs text-slate-400">• {alert.block}</span>
                  <span
                    className={`text-[10px] font-bold px-2 py-0.5 rounded-full uppercase tracking-wider ${
                      alert.severity === 'HIGH'
                        ? 'bg-red-100 text-red-800'
                        : 'bg-amber-100 text-amber-800'
                    }`}
                  >
                    {alert.severity} Severity
                  </span>
                  <span className="text-[10.5px] text-slate-400 flex items-center space-x-1">
                    <Clock className="w-3 h-3" />
                    <span>{alert.timeAgo}</span>
                  </span>
                </div>

                <p className="text-xs font-semibold text-slate-800 mt-1">{alert.title}</p>
                <p className="text-xs text-slate-500 mt-0.5 leading-relaxed">{alert.description}</p>

                {alert.ndviDropPercent && (
                  <div className="mt-2 text-[11px] font-medium text-slate-600 flex items-center space-x-3">
                    <span>
                      Previous NDVI: <strong className="text-slate-800">{alert.previousNdvi.toFixed(2)}</strong>
                    </span>
                    <span>
                      Current NDVI: <strong className="text-orange-600">{alert.currentNdvi.toFixed(2)}</strong>
                    </span>
                    <span className="text-red-600 font-bold">
                      -{alert.ndviDropPercent}% decline
                    </span>
                  </div>
                )}
              </div>
            </div>

            {/* Right Status Toggle & Field Navigation */}
            <div className="flex items-center space-x-2 shrink-0 border-t sm:border-t-0 pt-2 sm:pt-0 border-slate-100">
              <select
                value={alert.status}
                onChange={(e) => handleUpdateStatus(alert.id, e.target.value as AlertStatus)}
                className={`text-xs font-semibold px-2.5 py-1.5 rounded-lg border focus:outline-none cursor-pointer ${
                  alert.status === 'ACTIVE'
                    ? 'bg-red-50 text-red-700 border-red-200'
                    : alert.status === 'INVESTIGATING'
                    ? 'bg-amber-50 text-amber-700 border-amber-200'
                    : 'bg-emerald-50 text-emerald-700 border-emerald-200'
                }`}
              >
                <option value="ACTIVE">Active</option>
                <option value="INVESTIGATING">Investigating</option>
                <option value="RESOLVED">Resolved</option>
              </select>

              <button
                onClick={() => navigate(`/fields/${alert.fieldId}`)}
                className="flex items-center space-x-1 px-3 py-1.5 text-xs font-semibold text-white bg-[#15803D] hover:bg-emerald-700 rounded-lg transition-colors shadow-2xs"
              >
                <span>Inspect Field</span>
                <ArrowRight className="w-3.5 h-3.5" />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
