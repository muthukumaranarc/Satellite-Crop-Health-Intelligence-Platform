import React from 'react';
import { Cloud, Satellite } from 'lucide-react';
import { RecentImageryScene } from '../../types';

interface RecentImageryCardProps {
  scene: RecentImageryScene;
  onViewAll?: () => void;
}

export const RecentImageryCard: React.FC<RecentImageryCardProps> = ({
  scene,
  onViewAll,
}) => {
  return (
    <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-col justify-between">
      {/* Header */}
      <div className="flex items-center justify-between mb-3">
        <h3 className="text-sm font-bold text-slate-800 tracking-tight">Recent Imagery</h3>
        <button
          onClick={onViewAll}
          className="text-xs font-medium text-slate-500 hover:text-emerald-700 transition-colors"
        >
          View All
        </button>
      </div>

      {/* Imagery Scene Detail */}
      <div className="flex items-center space-x-3.5">
        {/* Satellite Scene Thumbnail */}
        <div className="w-18 h-18 rounded-lg overflow-hidden border border-slate-200 bg-slate-900 shrink-0 relative group">
          <img
            src={scene.previewUrl}
            alt={scene.satellite}
            className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
          />
          <div className="absolute top-1 left-1 bg-black/60 backdrop-blur-xs text-white p-0.5 rounded">
            <Satellite className="w-2.5 h-2.5" />
          </div>
        </div>

        {/* Scene Metadata */}
        <div className="flex-1 space-y-1">
          <h4 className="text-xs font-bold text-slate-800 tracking-tight">{scene.satellite}</h4>
          <p className="text-[11px] text-slate-500 font-medium">
            {scene.acquisitionDate}
          </p>
          <p className="text-[10.5px] text-slate-400">
            {scene.acquisitionTime}
          </p>
          
          <div className="flex items-center space-x-1.5 text-[11px] text-emerald-700 font-medium pt-0.5">
            <Cloud className="w-3.5 h-3.5 text-emerald-600" />
            <span>Cloud Cover: {scene.cloudCoverPercent}%</span>
          </div>
        </div>
      </div>
    </div>
  );
};
