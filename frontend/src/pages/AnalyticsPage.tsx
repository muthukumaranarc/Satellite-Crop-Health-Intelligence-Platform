import React, { useState } from 'react';
import { BarChart3, TrendingDown, ArrowRight, Download, Filter } from 'lucide-react';
import { MALWA_FIELDS } from '../mock/malwaData';
import { MOCK_NDVI_TREND } from '../mock/trendsData';
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

export const AnalyticsPage: React.FC = () => {
  const [selectedField1, setSelectedField1] = useState('F-245');
  const [selectedField2, setSelectedField2] = useState('F-301');

  const comparativeData = [
    { date: 'Apr 20', Field245: 0.48, Field301: 0.72 },
    { date: 'Apr 30', Field245: 0.44, Field301: 0.74 },
    { date: 'May 10', Field245: 0.38, Field301: 0.76 },
    { date: 'May 20', Field245: 0.32, Field301: 0.76 },
  ];

  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex items-center justify-between">
        <div>
          <h1 className="text-lg font-bold text-slate-900 tracking-tight">Temporal Comparative Analytics</h1>
          <p className="text-xs text-slate-500 mt-1">
            Compare vegetative performance trajectories between different agricultural blocks and fields.
          </p>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card space-y-4">
        <div className="flex flex-wrap items-center gap-3">
          <div className="text-xs">
            <label className="text-[11px] font-semibold text-slate-500 block mb-1">Field 1 (Stressed Case)</label>
            <select
              value={selectedField1}
              onChange={(e) => setSelectedField1(e.target.value)}
              className="bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-semibold text-slate-800"
            >
              {MALWA_FIELDS.map((f) => (
                <option key={f.id} value={f.id}>{f.name} — {f.block} ({f.healthClass})</option>
              ))}
            </select>
          </div>

          <div className="text-xs">
            <label className="text-[11px] font-semibold text-slate-500 block mb-1">Field 2 (Control / Healthy)</label>
            <select
              value={selectedField2}
              onChange={(e) => setSelectedField2(e.target.value)}
              className="bg-slate-50 border border-slate-200 rounded-lg p-2 text-xs font-semibold text-slate-800"
            >
              {MALWA_FIELDS.map((f) => (
                <option key={f.id} value={f.id}>{f.name} — {f.block} ({f.healthClass})</option>
              ))}
            </select>
          </div>
        </div>

        <div className="w-full h-80 pt-4">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={comparativeData} margin={{ top: 10, right: 20, left: -20, bottom: 0 }}>
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
              <Legend verticalAlign="top" height={36} wrapperStyle={{ fontSize: '12px' }} />
              <Line type="monotone" name="Field 245 (North Block - Decline)" dataKey="Field245" stroke="#f97316" strokeWidth={3} dot={{ r: 4 }} />
              <Line type="monotone" name="Field 301 (Kothla North - Healthy)" dataKey="Field301" stroke="#22c55e" strokeWidth={3} dot={{ r: 4 }} />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};
