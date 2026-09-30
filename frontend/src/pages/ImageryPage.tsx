import React from 'react';
import { Satellite, Cloud, Calendar, Download, Eye } from 'lucide-react';
import { MOCK_RECENT_IMAGERY } from '../mock/trendsData';

export const ImageryPage: React.FC = () => {
  const scenes = [
    MOCK_RECENT_IMAGERY,
    {
      id: 'S2B_MSIL2A_20240510T053649_N0510_R005_T43RER',
      satellite: 'Sentinel-2 L2A',
      acquisitionDate: 'May 10, 2024',
      acquisitionTime: '10:18 AM',
      cloudCoverPercent: 6,
      previewUrl: 'https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=400&q=80',
      resolution: '10m',
      sunElevation: 66.8,
    },
    {
      id: 'S2A_MSIL2A_20240430T053641_N0510_R005_T43RER',
      satellite: 'Sentinel-2 L2A',
      acquisitionDate: 'Apr 30, 2024',
      acquisitionTime: '10:14 AM',
      cloudCoverPercent: 18,
      previewUrl: 'https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=400&q=80',
      resolution: '10m',
      sunElevation: 63.2,
    },
  ];

  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex items-center justify-between">
        <div>
          <h1 className="text-lg font-bold text-slate-900 tracking-tight">Sentinel-2 Satellite Scene Catalog</h1>
          <p className="text-xs text-slate-500 mt-1">
            Multispectral Instrument (MSI) acquisitions covering the Malwa Region agricultural AOI.
          </p>
        </div>
        <span className="text-xs font-semibold bg-emerald-50 text-emerald-800 border border-emerald-200 px-3 py-1 rounded-lg">
          ESA Copernicus Sentinel-2
        </span>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {scenes.map((scene) => (
          <div key={scene.id} className="bg-white rounded-xl border border-slate-200 overflow-hidden shadow-card hover:shadow-md transition-all">
            <div className="h-44 bg-slate-900 relative">
              <img src={scene.previewUrl} alt={scene.satellite} className="w-full h-full object-cover" />
              <div className="absolute top-2 left-2 bg-black/60 backdrop-blur-xs text-white text-[11px] px-2 py-0.5 rounded font-mono">
                {scene.satellite}
              </div>
              <div className="absolute bottom-2 right-2 bg-emerald-900/80 backdrop-blur-xs text-emerald-200 text-[10px] px-2 py-0.5 rounded">
                Resolution: {scene.resolution}
              </div>
            </div>

            <div className="p-4 space-y-3">
              <div>
                <p className="text-xs font-mono text-slate-400 truncate">{scene.id}</p>
                <div className="flex items-center space-x-2 text-sm font-bold text-slate-800 mt-1">
                  <Calendar className="w-4 h-4 text-slate-500" />
                  <span>{scene.acquisitionDate} • {scene.acquisitionTime}</span>
                </div>
              </div>

              <div className="flex items-center justify-between text-xs text-slate-600 bg-slate-50 p-2.5 rounded-lg border border-slate-100">
                <span className="flex items-center space-x-1.5">
                  <Cloud className="w-4 h-4 text-emerald-600" />
                  <span>Cloud Cover:</span>
                </span>
                <strong className="text-slate-800">{scene.cloudCoverPercent}%</strong>
              </div>

              <div className="pt-2 flex items-center space-x-2">
                <button
                  onClick={() => alert(`Opening scene ${scene.id} in GIS viewer...`)}
                  className="flex-1 py-1.5 rounded-lg bg-[#15803D] hover:bg-emerald-700 text-white text-xs font-semibold flex items-center justify-center space-x-1 transition-colors"
                >
                  <Eye className="w-3.5 h-3.5" />
                  <span>View Rasters</span>
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
