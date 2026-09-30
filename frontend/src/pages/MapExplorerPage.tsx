import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { SatelliteGisMap } from '../components/map/SatelliteGisMap';
import { MALWA_FIELDS } from '../mock/malwaData';
import { useMapState } from '../context/MapStateContext';
import { getHealthColorStyle } from '../utils/healthColors';
import { Search, Filter, ShieldCheck, AlertTriangle } from 'lucide-react';

export const MapExplorerPage: React.FC = () => {
  const navigate = useNavigate();
  const { setSelectedFieldId, selectedFieldId } = useMapState();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCrop, setSelectedCrop] = useState('ALL');
  const [selectedHealth, setSelectedHealth] = useState('ALL');

  const filteredFields = MALWA_FIELDS.filter((f) => {
    const matchesSearch =
      f.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      f.block.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCrop = selectedCrop === 'ALL' || f.crop === selectedCrop;
    const matchesHealth = selectedHealth === 'ALL' || f.healthClass === selectedHealth;
    return matchesSearch && matchesCrop && matchesHealth;
  });

  return (
    <div className="flex flex-col lg:flex-row h-[calc(100vh-8.5rem)] gap-4 select-none">
      {/* Sidebar Filter Panel */}
      <div className="w-full lg:w-80 bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-col shrink-0">
        <div className="flex items-center justify-between pb-3 border-b border-slate-100 mb-3">
          <div className="flex items-center space-x-2">
            <Filter className="w-4 h-4 text-emerald-700" />
            <h2 className="text-sm font-bold text-slate-800">GIS Layer Filters</h2>
          </div>
          <span className="text-[11px] font-semibold text-emerald-800 bg-emerald-50 px-2 py-0.5 rounded-full border border-emerald-200">
            {filteredFields.length} Fields
          </span>
        </div>

        {/* Search */}
        <div className="relative mb-3">
          <Search className="w-3.5 h-3.5 text-slate-400 absolute left-2.5 top-1/2 -translate-y-1/2" />
          <input
            type="text"
            placeholder="Search field or block..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full bg-slate-50 border border-slate-200 rounded-lg pl-8 pr-3 py-1.5 text-xs text-slate-700 focus:outline-none focus:ring-1 focus:ring-emerald-500"
          />
        </div>

        {/* Filter Dropdowns */}
        <div className="space-y-3 mb-4 text-xs">
          <div>
            <label className="text-[11px] font-semibold text-slate-500 block mb-1">Health Class</label>
            <select
              value={selectedHealth}
              onChange={(e) => setSelectedHealth(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-medium text-slate-800 focus:outline-none"
            >
              <option value="ALL">All Health Classes</option>
              <option value="HEALTHY">🟢 Healthy (0.6 - 1.0)</option>
              <option value="MODERATE">🟡 Moderate (0.4 - 0.6)</option>
              <option value="POTENTIAL_STRESS">🟠 Potential Stress (0.2 - 0.4)</option>
              <option value="INSUFFICIENT_DATA">🔴 Insufficient / No Data (&lt; 0.2)</option>
            </select>
          </div>

          <div>
            <label className="text-[11px] font-semibold text-slate-500 block mb-1">Crop Type</label>
            <select
              value={selectedCrop}
              onChange={(e) => setSelectedCrop(e.target.value)}
              className="w-full bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-medium text-slate-800 focus:outline-none"
            >
              <option value="ALL">All Crops</option>
              <option value="Wheat">Wheat</option>
              <option value="Rice">Rice</option>
              <option value="Cotton">Cotton</option>
            </select>
          </div>
        </div>

        {/* Filtered Field List Scrollable */}
        <div className="flex-1 overflow-y-auto space-y-1.5 pr-1">
          {filteredFields.map((f) => {
            const isSelected = selectedFieldId === f.id;
            const healthStyle = getHealthColorStyle(f.healthClass);

            return (
              <div
                key={f.id}
                onClick={() => setSelectedFieldId(f.id)}
                className={`p-2.5 rounded-lg border transition-all cursor-pointer flex items-center justify-between ${
                  isSelected
                    ? 'bg-emerald-50 border-emerald-500 shadow-2xs'
                    : 'bg-white border-slate-200 hover:bg-slate-50'
                }`}
              >
                <div>
                  <p className="text-xs font-bold text-slate-800">{f.name}</p>
                  <p className="text-[10.5px] text-slate-500">{f.block} • {f.areaHa} ha</p>
                </div>

                <div className="text-right">
                  <span className={`inline-flex items-center space-x-1 px-2 py-0.5 rounded-full text-[10px] font-bold ${healthStyle.badgeBg} ${healthStyle.badgeText} border ${healthStyle.badgeBorder}`}>
                    <span>{f.currentNdvi.toFixed(2)}</span>
                  </span>
                  <p className="text-[9.5px] text-slate-400 mt-0.5">{healthStyle.shortLabel}</p>
                </div>
              </div>
            );
          })}
        </div>
      </div>

      {/* Main Map Canvas */}
      <div className="flex-1 rounded-xl overflow-hidden shadow-card border border-slate-200 relative">
        <SatelliteGisMap onNavigateToDetail={(id) => navigate(`/fields/${id}`)} />
      </div>
    </div>
  );
};
