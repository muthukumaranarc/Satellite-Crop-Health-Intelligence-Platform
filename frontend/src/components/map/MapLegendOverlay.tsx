import React from 'react';
import { useMapState } from '../../context/MapStateContext';

export const MapLegendOverlay: React.FC = () => {
  const { mapState } = useMapState();

  const legendItems = [
    { label: 'Healthy (0.6 – 1.0)', color: '#22c55e' },
    { label: 'Moderate (0.4 – 0.6)', color: '#eab308' },
    { label: 'Potential Stress (0.2 – 0.4)', color: '#f97316' },
    { label: 'Insufficient / No Data (< 0.2)', color: '#ef4444' },
    { label: 'No Data', color: '#64748b' },
  ];

  return (
    <div className="bg-slate-900/85 backdrop-blur-md text-white border border-slate-700/60 p-3 rounded-xl shadow-xl text-xs select-none space-y-1.5 w-52">
      <div className="text-[11px] font-bold text-slate-200 tracking-tight pb-1 border-b border-slate-700/60">
        Crop Health ({mapState.index})
      </div>

      <div className="space-y-1 pt-0.5">
        {legendItems.map((item) => (
          <div key={item.label} className="flex items-center space-x-2 text-[11px] text-slate-300">
            <span
              className="w-2.5 h-2.5 rounded-full shrink-0 shadow-2xs"
              style={{ backgroundColor: item.color }}
            />
            <span className="truncate">{item.label}</span>
          </div>
        ))}
      </div>
    </div>
  );
};
