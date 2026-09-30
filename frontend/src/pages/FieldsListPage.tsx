import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MALWA_FIELDS } from '../mock/malwaData';
import { getHealthColorStyle } from '../utils/healthColors';
import { Search, Grid, List, ArrowUpDown, ChevronRight, Sprout, AlertTriangle } from 'lucide-react';

export const FieldsListPage: React.FC = () => {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCrop, setSelectedCrop] = useState('ALL');
  const [selectedHealth, setSelectedHealth] = useState('ALL');
  const [viewMode, setViewMode] = useState<'table' | 'grid'>('table');

  const filteredFields = MALWA_FIELDS.filter((f) => {
    const matchesSearch =
      f.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      f.block.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCrop = selectedCrop === 'ALL' || f.crop === selectedCrop;
    const matchesHealth = selectedHealth === 'ALL' || f.healthClass === selectedHealth;
    return matchesSearch && matchesCrop && matchesHealth;
  });

  return (
    <div className="space-y-4 max-w-[1600px] mx-auto pb-6 select-none">
      {/* Header */}
      <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-wrap items-center justify-between gap-3">
        <div>
          <h1 className="text-base font-bold text-slate-900 tracking-tight">Agricultural Fields Directory</h1>
          <p className="text-xs text-slate-500 mt-0.5">
            Monitored field boundaries, latest Sentinel-2 vegetation indices, and health classifications.
          </p>
        </div>

        {/* View Toggle */}
        <div className="flex items-center space-x-1 bg-slate-100 p-1 rounded-lg border border-slate-200">
          <button
            onClick={() => setViewMode('table')}
            className={`p-1.5 rounded-md text-xs font-medium transition-colors ${
              viewMode === 'table' ? 'bg-white text-slate-900 shadow-2xs' : 'text-slate-500 hover:text-slate-700'
            }`}
          >
            <List className="w-4 h-4" />
          </button>
          <button
            onClick={() => setViewMode('grid')}
            className={`p-1.5 rounded-md text-xs font-medium transition-colors ${
              viewMode === 'grid' ? 'bg-white text-slate-900 shadow-2xs' : 'text-slate-500 hover:text-slate-700'
            }`}
          >
            <Grid className="w-4 h-4" />
          </button>
        </div>
      </div>

      {/* Filter and Search Bar */}
      <div className="bg-white rounded-xl border border-slate-200 p-3 shadow-card flex flex-wrap items-center justify-between gap-3 text-xs">
        <div className="flex items-center space-x-2.5 flex-1 max-w-md">
          <div className="relative w-full">
            <Search className="w-3.5 h-3.5 text-slate-400 absolute left-3 top-1/2 -translate-y-1/2" />
            <input
              type="text"
              placeholder="Search by field name or block..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg pl-8 pr-3 py-1.5 text-xs text-slate-800 focus:outline-none focus:ring-1 focus:ring-emerald-500"
            />
          </div>
        </div>

        <div className="flex items-center space-x-2">
          <select
            value={selectedHealth}
            onChange={(e) => setSelectedHealth(e.target.value)}
            className="bg-slate-50 border border-slate-200 rounded-lg px-2.5 py-1.5 text-xs font-medium text-slate-700 focus:outline-none"
          >
            <option value="ALL">All Health Classes</option>
            <option value="HEALTHY">🟢 Healthy</option>
            <option value="MODERATE">🟡 Moderate</option>
            <option value="POTENTIAL_STRESS">🟠 Potential Stress</option>
            <option value="INSUFFICIENT_DATA">🔴 Insufficient / No Data</option>
          </select>

          <select
            value={selectedCrop}
            onChange={(e) => setSelectedCrop(e.target.value)}
            className="bg-slate-50 border border-slate-200 rounded-lg px-2.5 py-1.5 text-xs font-medium text-slate-700 focus:outline-none"
          >
            <option value="ALL">All Crops</option>
            <option value="Wheat">Wheat</option>
            <option value="Rice">Rice</option>
            <option value="Cotton">Cotton</option>
          </select>
        </div>
      </div>

      {/* Content: Table or Grid */}
      {viewMode === 'table' ? (
        <div className="bg-white rounded-xl border border-slate-200 shadow-card overflow-hidden">
          <table className="w-full text-left border-collapse text-xs">
            <thead>
              <tr className="bg-slate-50/80 border-b border-slate-200 text-slate-500 font-semibold text-[11px] uppercase tracking-wider">
                <th className="py-3 px-4">Field</th>
                <th className="py-3 px-4">Crop</th>
                <th className="py-3 px-4">Area</th>
                <th className="py-3 px-4">NDVI</th>
                <th className="py-3 px-4">NDRE</th>
                <th className="py-3 px-4">Stress Score</th>
                <th className="py-3 px-4">Status</th>
                <th className="py-3 px-4 text-right">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 font-medium">
              {filteredFields.map((f) => {
                const healthStyle = getHealthColorStyle(f.healthClass);
                return (
                  <tr
                    key={f.id}
                    onClick={() => navigate(`/fields/${f.id}`)}
                    className="hover:bg-slate-50/80 transition-colors cursor-pointer group"
                  >
                    <td className="py-3 px-4">
                      <div className="font-bold text-slate-900 group-hover:text-emerald-700 transition-colors">
                        {f.name}
                      </div>
                      <div className="text-[10.5px] text-slate-400">{f.block}</div>
                    </td>
                    <td className="py-3 px-4 text-slate-600">{f.crop}</td>
                    <td className="py-3 px-4 text-slate-600">{f.areaHa} ha</td>
                    <td className="py-3 px-4 font-bold text-slate-900">{f.currentNdvi.toFixed(2)}</td>
                    <td className="py-3 px-4 text-slate-600">{f.currentNdre.toFixed(2)}</td>
                    <td className="py-3 px-4">
                      <span className={`font-bold ${f.stressScore > 50 ? 'text-orange-600' : 'text-slate-700'}`}>
                        {f.stressScore}%
                      </span>
                    </td>
                    <td className="py-3 px-4">
                      <span className={`inline-flex items-center space-x-1.5 px-2 py-0.5 rounded-full text-[10.5px] font-semibold ${healthStyle.badgeBg} ${healthStyle.badgeBorder} ${healthStyle.badgeText} border`}>
                        <span className={`w-1.5 h-1.5 rounded-full ${healthStyle.bg}`} />
                        <span>{healthStyle.shortLabel}</span>
                      </span>
                    </td>
                    <td className="py-3 px-4 text-right">
                      <button className="text-emerald-700 hover:text-emerald-800 font-semibold inline-flex items-center space-x-1 text-xs">
                        <span>Details</span>
                        <ChevronRight className="w-3.5 h-3.5" />
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {filteredFields.map((f) => {
            const healthStyle = getHealthColorStyle(f.healthClass);
            return (
              <div
                key={f.id}
                onClick={() => navigate(`/fields/${f.id}`)}
                className="bg-white rounded-xl border border-slate-200 p-4 shadow-card hover:shadow-md transition-all cursor-pointer flex flex-col justify-between group"
              >
                <div>
                  <div className="flex items-start justify-between">
                    <div>
                      <h3 className="font-bold text-slate-900 text-sm group-hover:text-emerald-700 transition-colors">
                        {f.name}
                      </h3>
                      <p className="text-[11px] text-slate-500 mt-0.5">{f.block} • {f.crop}</p>
                    </div>
                    <span className={`inline-flex items-center space-x-1 px-2 py-0.5 rounded-full text-[10px] font-semibold ${healthStyle.badgeBg} ${healthStyle.badgeBorder} ${healthStyle.badgeText} border`}>
                      <span>{healthStyle.shortLabel}</span>
                    </span>
                  </div>

                  <div className="grid grid-cols-3 gap-2 mt-4 text-center">
                    <div className="bg-slate-50 rounded-lg p-2 border border-slate-100">
                      <p className="text-[10px] text-slate-400">NDVI</p>
                      <p className="font-bold text-slate-900 text-sm mt-0.5">{f.currentNdvi.toFixed(2)}</p>
                    </div>
                    <div className="bg-slate-50 rounded-lg p-2 border border-slate-100">
                      <p className="text-[10px] text-slate-400">Area</p>
                      <p className="font-bold text-slate-900 text-sm mt-0.5">{f.areaHa} ha</p>
                    </div>
                    <div className="bg-slate-50 rounded-lg p-2 border border-slate-100">
                      <p className="text-[10px] text-slate-400">Stress</p>
                      <p className={`font-bold text-sm mt-0.5 ${f.stressScore > 50 ? 'text-orange-600' : 'text-slate-900'}`}>
                        {f.stressScore}%
                      </p>
                    </div>
                  </div>
                </div>

                <div className="pt-3 border-t border-slate-100 mt-4 flex items-center justify-between text-xs text-slate-400">
                  <span>Updated: {f.lastObservationDate}</span>
                  <ChevronRight className="w-4 h-4 text-emerald-600 group-hover:translate-x-1 transition-transform" />
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};
