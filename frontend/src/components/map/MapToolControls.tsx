import React from 'react';
import { Plus, Minus, Layers, Navigation } from 'lucide-react';
import { useMapState } from '../../context/MapStateContext';

interface MapToolControlsProps {
  onZoomIn: () => void;
  onZoomOut: () => void;
  onRecenter: () => void;
}

export const MapToolControls: React.FC<MapToolControlsProps> = ({
  onZoomIn,
  onZoomOut,
  onRecenter,
}) => {
  const { mapState, setBasemap } = useMapState();

  const toggleBasemap = () => {
    setBasemap(mapState.basemap === 'satellite' ? 'street' : 'satellite');
  };

  return (
    <div className="flex flex-col space-y-1.5 select-none">
      {/* Zoom Controls */}
      <div className="bg-slate-900/85 backdrop-blur-md border border-slate-700/60 rounded-xl overflow-hidden shadow-xl flex flex-col">
        <button
          onClick={onZoomIn}
          className="p-2 text-white hover:bg-slate-800 transition-colors border-b border-slate-700/60"
          title="Zoom In"
        >
          <Plus className="w-4 h-4" />
        </button>
        <button
          onClick={onZoomOut}
          className="p-2 text-white hover:bg-slate-800 transition-colors"
          title="Zoom Out"
        >
          <Minus className="w-4 h-4" />
        </button>
      </div>

      {/* Layer Switcher */}
      <div className="bg-slate-900/85 backdrop-blur-md border border-slate-700/60 rounded-xl overflow-hidden shadow-xl">
        <button
          onClick={toggleBasemap}
          className={`p-2 transition-colors ${
            mapState.basemap === 'satellite'
              ? 'text-emerald-400 bg-slate-800'
              : 'text-white hover:bg-slate-800'
          }`}
          title={`Switch to ${mapState.basemap === 'satellite' ? 'Street' : 'Satellite'} view`}
        >
          <Layers className="w-4 h-4" />
        </button>
      </div>

      {/* Recenter Button */}
      <div className="bg-slate-900/85 backdrop-blur-md border border-slate-700/60 rounded-xl overflow-hidden shadow-xl">
        <button
          onClick={onRecenter}
          className="p-2 text-white hover:bg-slate-800 transition-colors"
          title="Recenter to Fields"
        >
          <Navigation className="w-4 h-4" />
        </button>
      </div>
    </div>
  );
};
