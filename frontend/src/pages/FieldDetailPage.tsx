import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { MALWA_FIELDS } from '../mock/malwaData';
import { getHealthColorStyle } from '../utils/healthColors';
import { 
  ArrowLeft, 
  AlertTriangle, 
  Sprout, 
  Calendar, 
  Layers, 
  Activity, 
  ShieldCheck, 
  Download,
  TrendingDown
} from 'lucide-react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from 'recharts';

export const FieldDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  // Find field or default to Field 245
  const field = MALWA_FIELDS.find((f) => f.id === id) || MALWA_FIELDS[0];
  const healthStyle = getHealthColorStyle(field.healthClass);

  // Time series for this field
  const fieldHistory = [
    { date: 'Apr 10', ndvi: 0.68, ndre: 0.42, ndmi: 0.38 },
    { date: 'Apr 20', ndvi: 0.65, ndre: 0.39, ndmi: 0.35 },
    { date: 'Apr 30', ndvi: 0.58, ndre: 0.33, ndmi: 0.31 },
    { date: 'May 10', ndvi: 0.45, ndre: 0.25, ndmi: 0.26 },
    { date: 'May 20', ndvi: field.currentNdvi, ndre: field.currentNdre, ndmi: 0.22 },
  ];

  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      {/* Top Navigation Back Bar */}
      <div className="flex items-center justify-between">
        <button
          onClick={() => navigate(-1)}
          className="inline-flex items-center space-x-1.5 text-xs font-semibold text-slate-600 hover:text-slate-900 transition-colors bg-white px-3 py-1.5 rounded-lg border border-slate-200 shadow-2xs"
        >
          <ArrowLeft className="w-3.5 h-3.5" />
          <span>Back to Dashboard</span>
        </button>

        <div className="flex items-center space-x-2">
          <button
            onClick={() => alert('Downloading Sentinel-2 Field Analysis Report (PDF)...')}
            className="inline-flex items-center space-x-1.5 text-xs font-semibold text-white bg-[#15803D] hover:bg-emerald-700 px-3.5 py-1.5 rounded-lg transition-colors shadow-2xs"
          >
            <Download className="w-3.5 h-3.5" />
            <span>Export Field Report</span>
          </button>
        </div>
      </div>

      {/* Field Dossier Header Card */}
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex flex-wrap items-center justify-between gap-4">
        <div>
          <div className="flex items-center space-x-3">
            <h1 className="text-xl font-extrabold text-slate-900 tracking-tight">{field.name}</h1>
            <span className={`inline-flex items-center space-x-1.5 px-3 py-0.5 rounded-full text-xs font-semibold ${healthStyle.badgeBg} ${healthStyle.badgeBorder} ${healthStyle.badgeText} border`}>
              <span className={`w-2 h-2 rounded-full ${healthStyle.bg}`} />
              <span>{healthStyle.shortLabel}</span>
            </span>
          </div>
          <p className="text-xs text-slate-500 mt-1">
            {field.block} • {field.region} • Crop: <strong className="text-slate-700">{field.crop}</strong> • Area: <strong className="text-slate-700">{field.areaHa} ha</strong>
          </p>
        </div>

        <div className="text-right">
          <p className="text-[11px] text-slate-400 font-medium">Latest Satellite Pass</p>
          <p className="text-xs font-bold text-slate-800">{field.lastObservationDate} (Sentinel-2 L2A)</p>
          <p className="text-[11px] text-emerald-700 font-medium">Valid Pixels: {field.validPixelPercent}%</p>
        </div>
      </div>

      {/* Scientific Caution Alert Banner */}
      <div className="bg-amber-50/80 border border-amber-200/80 rounded-xl p-4 flex items-start space-x-3 text-xs text-amber-900 leading-relaxed shadow-2xs">
        <AlertTriangle className="w-5 h-5 text-amber-600 shrink-0 mt-0.5" />
        <div>
          <h4 className="font-bold text-amber-950 mb-0.5">Satellite Remote Sensing Advisory</h4>
          <p>
            AgriSight detected a <strong>{field.ndviChangePercent}%</strong> trajectory change in vegetation indices over recent Sentinel-2 observations. 
            Remote sensing measurements indicate potential stress or vegetative decline, but do not provide autonomous proof of disease, pest attack, or soil deficiency. Ground verification is required.
          </p>
        </div>
      </div>

      {/* Key Metric Tiles */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 text-xs">
        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <p className="text-[11px] font-semibold text-slate-400">Current NDVI</p>
          <p className="text-2xl font-black text-slate-900 mt-1">{field.currentNdvi.toFixed(2)}</p>
          <p className="text-[10.5px] text-slate-400 mt-1">Range: 0.0 – 1.0 (Canopy vigor)</p>
        </div>
        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <p className="text-[11px] font-semibold text-slate-400">Current NDRE</p>
          <p className="text-2xl font-black text-slate-900 mt-1">{field.currentNdre.toFixed(2)}</p>
          <p className="text-[10.5px] text-slate-400 mt-1">Red-edge chlorophyll index</p>
        </div>
        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <p className="text-[11px] font-semibold text-slate-400">Stress Score</p>
          <p className={`text-2xl font-black mt-1 ${field.stressScore > 50 ? 'text-orange-600' : 'text-slate-900'}`}>
            {field.stressScore}%
          </p>
          <p className="text-[10.5px] text-slate-400 mt-1">Multi-temporal anomaly metric</p>
        </div>
        <div className="bg-white p-4 rounded-xl border border-slate-200 shadow-card">
          <p className="text-[11px] font-semibold text-slate-400">Vegetation Trajectory</p>
          <p className={`text-2xl font-black mt-1 flex items-center space-x-1 ${field.ndviChangePercent < 0 ? 'text-red-600' : 'text-emerald-600'}`}>
            <span>{field.ndviChangePercent}%</span>
          </p>
          <p className="text-[10.5px] text-slate-400 mt-1">Change vs baseline pass</p>
        </div>
      </div>

      {/* Historical Trend Multi-Index Chart */}
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card">
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="text-sm font-bold text-slate-900">Multi-Temporal Index Trajectory</h3>
            <p className="text-xs text-slate-500">Comparative Sentinel-2 NDVI, NDRE, and NDMI progression</p>
          </div>
          <span className="text-[11px] font-medium text-slate-400 bg-slate-50 border border-slate-200 px-2 py-1 rounded">
            Sentinel-2 MSI (10m)
          </span>
        </div>

        <div className="w-full h-64">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={fieldHistory} margin={{ top: 10, right: 20, left: -20, bottom: 0 }}>
              <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f1f5f9" />
              <XAxis dataKey="date" tick={{ fontSize: 11, fill: '#64748b' }} axisLine={false} tickLine={false} />
              <YAxis domain={[0, 1.0]} ticks={[0, 0.2, 0.4, 0.6, 0.8, 1.0]} tick={{ fontSize: 11, fill: '#64748b' }} axisLine={false} tickLine={false} />
              <Tooltip
                contentStyle={{
                  backgroundColor: '#0f172a',
                  borderColor: '#1e293b',
                  color: '#fff',
                  borderRadius: '0.5rem',
                  fontSize: '11px',
                }}
              />
              <Legend verticalAlign="top" height={36} wrapperStyle={{ fontSize: '11px' }} />
              <Line type="monotone" name="NDVI (Canopy Vigor)" dataKey="ndvi" stroke="#22c55e" strokeWidth={2.5} dot={{ r: 4 }} activeDot={{ r: 6 }} />
              <Line type="monotone" name="NDRE (Chlorophyll)" dataKey="ndre" stroke="#eab308" strokeWidth={2} dot={{ r: 3 }} />
              <Line type="monotone" name="NDMI (Canopy Moisture)" dataKey="ndmi" stroke="#3b82f6" strokeWidth={2} strokeDasharray="4 4" dot={{ r: 3 }} />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Observation History Table */}
      <div className="bg-white rounded-xl border border-slate-200 shadow-card overflow-hidden">
        <div className="p-4 border-b border-slate-100 flex items-center justify-between">
          <h3 className="text-sm font-bold text-slate-900">Sentinel-2 Observations Record</h3>
          <span className="text-xs text-slate-400">Atmospherically Corrected (L2A)</span>
        </div>
        <table className="w-full text-left text-xs border-collapse">
          <thead>
            <tr className="bg-slate-50/80 border-b border-slate-200 text-slate-500 font-semibold text-[11px] uppercase tracking-wider">
              <th className="py-2.5 px-4">Observation Date</th>
              <th className="py-2.5 px-4">Scene ID</th>
              <th className="py-2.5 px-4">Cloud Cover</th>
              <th className="py-2.5 px-4">Valid Pixels</th>
              <th className="py-2.5 px-4">NDVI</th>
              <th className="py-2.5 px-4">NDRE</th>
              <th className="py-2.5 px-4 text-right">Classification</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-100 font-medium">
            <tr className="hover:bg-slate-50/80">
              <td className="py-2.5 px-4 font-bold text-slate-800">May 20, 2024</td>
              <td className="py-2.5 px-4 text-slate-500 font-mono text-[10.5px]">S2A_MSIL2A_20240520</td>
              <td className="py-2.5 px-4 text-slate-600">12%</td>
              <td className="py-2.5 px-4 text-slate-600">96%</td>
              <td className="py-2.5 px-4 font-bold text-orange-600">{field.currentNdvi.toFixed(2)}</td>
              <td className="py-2.5 px-4 text-slate-700">{field.currentNdre.toFixed(2)}</td>
              <td className="py-2.5 px-4 text-right">
                <span className={`px-2 py-0.5 rounded-full text-[10px] font-semibold ${healthStyle.badgeBg} ${healthStyle.badgeText} border ${healthStyle.badgeBorder}`}>
                  {healthStyle.shortLabel}
                </span>
              </td>
            </tr>
            <tr className="hover:bg-slate-50/80">
              <td className="py-2.5 px-4 font-bold text-slate-800">May 10, 2024</td>
              <td className="py-2.5 px-4 text-slate-500 font-mono text-[10.5px]">S2B_MSIL2A_20240510</td>
              <td className="py-2.5 px-4 text-slate-600">6%</td>
              <td className="py-2.5 px-4 text-slate-600">99%</td>
              <td className="py-2.5 px-4 font-bold text-slate-700">0.45</td>
              <td className="py-2.5 px-4 text-slate-700">0.25</td>
              <td className="py-2.5 px-4 text-right">
                <span className="px-2 py-0.5 rounded-full text-[10px] font-semibold bg-yellow-50 text-yellow-700 border border-yellow-200">
                  Moderate
                </span>
              </td>
            </tr>
            <tr className="hover:bg-slate-50/80">
              <td className="py-2.5 px-4 font-bold text-slate-800">Apr 30, 2024</td>
              <td className="py-2.5 px-4 text-slate-500 font-mono text-[10.5px]">S2A_MSIL2A_20240430</td>
              <td className="py-2.5 px-4 text-slate-600">18%</td>
              <td className="py-2.5 px-4 text-slate-600">97%</td>
              <td className="py-2.5 px-4 font-bold text-slate-700">0.58</td>
              <td className="py-2.5 px-4 text-slate-700">0.33</td>
              <td className="py-2.5 px-4 text-right">
                <span className="px-2 py-0.5 rounded-full text-[10px] font-semibold bg-yellow-50 text-yellow-700 border border-yellow-200">
                  Moderate
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};
