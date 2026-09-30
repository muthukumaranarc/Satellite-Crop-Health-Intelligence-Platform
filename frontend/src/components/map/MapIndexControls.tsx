import React from 'react';
import { Calendar, ChevronDown } from 'lucide-react';
import { useMapState } from '../../context/MapStateContext';

export const MapIndexControls: React.FC = () => {
  const { mapState, setIndex, setDate, setOpacity } = useMapState();

  return (
    <div className="bg-slate-900/85 backdrop-blur-md text-white border border-slate-700/60 p-3 rounded-xl shadow-xl w-52 space-y-2.5 text-xs select-none">
      {/* Index Selector */}
      <div>
        <label className="text-[10.5px] uppercase font-semibold text-slate-400 tracking-wider block mb-1">
          Index
        </label>
        <div className="relative">
          <select
            value={mapState.index}
            onChange={(e) => setIndex(e.target.value as any)}
            className="w-full bg-slate-800/90 border border-slate-700 rounded-lg px-2.5 py-1.5 text-xs font-medium text-white appearance-none cursor-pointer focus:outline-none focus:border-emerald-500"
          >
            <option value="NDVI">NDVI</option>
            <option value="NDRE">NDRE</option>
            <option value="NDMI">NDMI</option>
          </select>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 absolute right-2.5 top-1/2 -translate-y-1/2 pointer-events-none" />
        </div>
      </div>

      {/* Date Selector */}
      <div>
        <label className="text-[10.5px] uppercase font-semibold text-slate-400 tracking-wider block mb-1">
          Date
        </label>
        <div className="flex items-center justify-between bg-slate-800/90 border border-slate-700 rounded-lg px-2.5 py-1.5 text-xs text-white">
          <span className="font-medium">{mapState.date}</span>
          <Calendar className="w-3.5 h-3.5 text-slate-400" />
        </div>
      </div>

      {/* Opacity Slider */}
      <div>
        <div className="flex items-center justify-between text-[10.5px] text-slate-400 font-semibold mb-1">
          <span>Opacity</span>
          <span className="text-emerald-400">{Math.round(mapState.opacity * 100)}%</span>
        </div>
        <input
          type="range"
          min="0.1"
          max="1"
          step="0.05"
          value={mapState.opacity}
          onChange={(e) => setOpacity(parseFloat(e.target.value))}
          className="w-full cursor-pointer accent-emerald-500"
        />
      </div>
    </div>
  );
};
