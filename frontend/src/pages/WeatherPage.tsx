import React from 'react';
import { CloudSun, Droplets, Wind, Thermometer, Sun } from 'lucide-react';

export const WeatherPage: React.FC = () => {
  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex items-center justify-between">
        <div>
          <h1 className="text-lg font-bold text-slate-900 tracking-tight">Agro-Meteorology & Environmental Indices</h1>
          <p className="text-xs text-slate-500 mt-1">
            Weather observation context for the Malwa agricultural belt to correlate with vegetative changes.
          </p>
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 text-xs">
        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <div className="flex items-center space-x-2 text-amber-600 mb-1">
            <Thermometer className="w-4 h-4" />
            <span className="font-semibold text-slate-500">Surface Temperature</span>
          </div>
          <p className="text-2xl font-bold text-slate-900">34.2 °C</p>
          <p className="text-[11px] text-slate-400 mt-1">+1.8 °C above seasonal norm</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <div className="flex items-center space-x-2 text-sky-600 mb-1">
            <Droplets className="w-4 h-4" />
            <span className="font-semibold text-slate-500">Relative Humidity</span>
          </div>
          <p className="text-2xl font-bold text-slate-900">42%</p>
          <p className="text-[11px] text-slate-400 mt-1">Moderate atmospheric moisture</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <div className="flex items-center space-x-2 text-indigo-600 mb-1">
            <Wind className="w-4 h-4" />
            <span className="font-semibold text-slate-500">Wind Velocity</span>
          </div>
          <p className="text-2xl font-bold text-slate-900">14 km/h</p>
          <p className="text-[11px] text-slate-400 mt-1">North-West direction</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <div className="flex items-center space-x-2 text-amber-500 mb-1">
            <Sun className="w-4 h-4" />
            <span className="font-semibold text-slate-500">Solar Radiation</span>
          </div>
          <p className="text-2xl font-bold text-slate-900">22.4 MJ/m²</p>
          <p className="text-[11px] text-slate-400 mt-1">High photosynthetic potential</p>
        </div>
      </div>
    </div>
  );
};
