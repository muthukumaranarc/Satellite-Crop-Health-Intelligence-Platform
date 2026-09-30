import React, { useState } from 'react';
import { Settings as SettingsIcon, Save, Database, Shield, Sliders } from 'lucide-react';

export const SettingsPage: React.FC = () => {
  const [healthyThreshold, setHealthyThreshold] = useState('0.60');
  const [moderateThreshold, setModerateThreshold] = useState('0.40');
  const [stressThreshold, setStressThreshold] = useState('0.20');
  const [apiUrl, setApiUrl] = useState('http://localhost:8080/api/v1');
  const [mockMode, setMockMode] = useState(true);

  const handleSave = () => {
    alert('Settings successfully updated.');
  };

  return (
    <div className="space-y-4 max-w-[1000px] mx-auto pb-8 select-none">
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex items-center justify-between">
        <div>
          <h1 className="text-lg font-bold text-slate-900 tracking-tight">System & Health Threshold Configurations</h1>
          <p className="text-xs text-slate-500 mt-1">
            Configure vegetation index cutoffs per crop type and backend API integration endpoints.
          </p>
        </div>
        <button
          onClick={handleSave}
          className="px-3.5 py-1.5 rounded-lg bg-[#15803D] hover:bg-emerald-700 text-white text-xs font-semibold flex items-center space-x-1.5 transition-colors shadow-2xs"
        >
          <Save className="w-3.5 h-3.5" />
          <span>Save Changes</span>
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-xs">
        {/* Health Classification Thresholds */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card space-y-4">
          <div className="flex items-center space-x-2 pb-2 border-b border-slate-100">
            <Sliders className="w-4 h-4 text-emerald-600" />
            <h3 className="font-bold text-slate-900 text-sm">NDVI Health Thresholds</h3>
          </div>

          <div>
            <label className="text-[11px] font-semibold text-slate-600 block mb-1">
              Healthy Cutoff (NDVI ≥)
            </label>
            <input
              type="number"
              step="0.05"
              value={healthyThreshold}
              onChange={(e) => setHealthyThreshold(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-bold text-slate-800 focus:outline-none"
            />
          </div>

          <div>
            <label className="text-[11px] font-semibold text-slate-600 block mb-1">
              Moderate Cutoff (NDVI ≥)
            </label>
            <input
              type="number"
              step="0.05"
              value={moderateThreshold}
              onChange={(e) => setModerateThreshold(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-bold text-slate-800 focus:outline-none"
            />
          </div>

          <div>
            <label className="text-[11px] font-semibold text-slate-600 block mb-1">
              Potential Stress Cutoff (NDVI ≥)
            </label>
            <input
              type="number"
              step="0.05"
              value={stressThreshold}
              onChange={(e) => setStressThreshold(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-bold text-slate-800 focus:outline-none"
            />
          </div>
        </div>

        {/* Backend REST API Configuration */}
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card space-y-4">
          <div className="flex items-center space-x-2 pb-2 border-b border-slate-100">
            <Database className="w-4 h-4 text-emerald-600" />
            <h3 className="font-bold text-slate-900 text-sm">Spring Boot API Integration</h3>
          </div>

          <div>
            <label className="text-[11px] font-semibold text-slate-600 block mb-1">
              API Base URL
            </label>
            <input
              type="text"
              value={apiUrl}
              onChange={(e) => setApiUrl(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-mono text-slate-800 focus:outline-none"
            />
          </div>

          <div className="p-3 bg-slate-50 rounded-lg border border-slate-100 flex items-center justify-between">
            <div>
              <p className="font-semibold text-slate-800">Standalone Mock Dataset Mode</p>
              <p className="text-[11px] text-slate-500">Uses realistic Malwa Region wheat field data</p>
            </div>
            <input
              type="checkbox"
              checked={mockMode}
              onChange={(e) => setMockMode(e.target.checked)}
              className="w-4 h-4 accent-emerald-600 rounded cursor-pointer"
            />
          </div>
        </div>
      </div>
    </div>
  );
};
