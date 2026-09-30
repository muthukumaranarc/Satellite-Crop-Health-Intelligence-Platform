import React from 'react';
import { Activity, Info, BookOpen } from 'lucide-react';

export const IndicesPage: React.FC = () => {
  const indices = [
    {
      name: 'NDVI',
      fullName: 'Normalized Difference Vegetation Index',
      formula: '(B08 - B04) / (B08 + B04)',
      bands: 'NIR (Band 8: 842 nm) & Red (Band 4: 665 nm)',
      description:
        'The global benchmark for assessing vegetative vigor and live green plant canopy. Healthy chlorophyll reflects strongly in the near-infrared while absorbing visible red light.',
      ranges: [
        { label: '0.6 to 1.0', meaning: 'Dense, vigorous vegetation canopy (Healthy)' },
        { label: '0.4 to 0.6', meaning: 'Moderate vegetative development (Normal)' },
        { label: '0.2 to 0.4', meaning: 'Sparse canopy or vegetation decline (Potential Stress)' },
        { label: '< 0.2', meaning: 'Bare soil, water, harvest stubble, or non-vegetated' },
      ],
    },
    {
      name: 'NDRE',
      fullName: 'Normalized Difference Red Edge Index',
      formula: '(B08 - B05) / (B08 + B05)',
      bands: 'NIR (Band 8: 842 nm) & Red Edge (Band 5: 705 nm)',
      description:
        'Sensitive to chlorophyll concentrations in dense canopies where traditional NDVI saturates. Excellent for early detection of nutrient stress and mid-to-late crop stages.',
      ranges: [
        { label: '0.4 to 0.7', meaning: 'High chlorophyll concentration' },
        { label: '0.2 to 0.4', meaning: 'Moderate chlorophyll vigor' },
        { label: '< 0.2', meaning: 'Low chlorophyll or senescence' },
      ],
    },
    {
      name: 'NDMI',
      fullName: 'Normalized Difference Moisture Index',
      formula: '(B08 - B11) / (B08 + B11)',
      bands: 'NIR (Band 8: 842 nm) & SWIR (Band 11: 1610 nm)',
      description:
        'Measures liquid water content in crop canopy leaves. Liquid water strongly absorbs SWIR radiation, making this index a sensitive indicator of canopy water stress.',
      ranges: [
        { label: '0.4 to 0.6', meaning: 'High canopy water content (No moisture stress)' },
        { label: '0.2 to 0.4', meaning: 'Moderate canopy moisture' },
        { label: '< 0.2', meaning: 'Canopy water deficit' },
      ],
    },
  ];

  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex items-center justify-between">
        <div>
          <h1 className="text-lg font-bold text-slate-900 tracking-tight">Vegetation Indices Reference Guide</h1>
          <p className="text-xs text-slate-500 mt-1">
            Mathematical formulations, spectral bands, and scientific interpretation rules implemented in AgriSight.
          </p>
        </div>
        <div className="flex items-center space-x-1.5 text-xs text-emerald-800 bg-emerald-50 px-3 py-1 rounded-lg border border-emerald-200 font-semibold">
          <BookOpen className="w-4 h-4" />
          <span>Scientific Specification</span>
        </div>
      </div>

      <div className="space-y-4">
        {indices.map((idx) => (
          <div key={idx.name} className="bg-white rounded-xl border border-slate-200 p-5 shadow-card space-y-3">
            <div className="flex items-center justify-between">
              <div>
                <span className="text-xs font-bold text-emerald-700 uppercase tracking-wider">{idx.name}</span>
                <h2 className="text-base font-bold text-slate-900">{idx.fullName}</h2>
              </div>
              <div className="bg-slate-100 text-slate-800 font-mono text-xs px-3 py-1.5 rounded-lg border border-slate-200 font-semibold">
                {idx.formula}
              </div>
            </div>

            <p className="text-xs text-slate-600 leading-relaxed">{idx.description}</p>
            <p className="text-xs text-slate-500 font-medium">Bands: <span className="text-slate-800">{idx.bands}</span></p>

            <div className="bg-slate-50 p-3 rounded-lg border border-slate-200/80">
              <h4 className="text-[11px] font-bold text-slate-700 uppercase tracking-wider mb-2">Classification Thresholds</h4>
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-2 text-xs">
                {idx.ranges.map((r) => (
                  <div key={r.label} className="bg-white p-2 rounded border border-slate-200">
                    <p className="font-bold text-slate-900">{r.label}</p>
                    <p className="text-[11px] text-slate-500 mt-0.5">{r.meaning}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
