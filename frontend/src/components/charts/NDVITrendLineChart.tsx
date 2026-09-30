import React, { useState } from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from 'recharts';
import { ChevronDown } from 'lucide-react';
import { NDVITrendDataPoint } from '../../types';

interface NDVITrendLineChartProps {
  data: NDVITrendDataPoint[];
}

export const NDVITrendLineChart: React.FC<NDVITrendLineChartProps> = ({ data }) => {
  const [period, setPeriod] = useState('Last 30 Days');

  return (
    <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-col justify-between h-full">
      {/* Header */}
      <div className="flex items-center justify-between mb-2">
        <h3 className="text-sm font-bold text-slate-800 tracking-tight">NDVI Trend</h3>
        <div className="relative">
          <select
            value={period}
            onChange={(e) => setPeriod(e.target.value)}
            className="appearance-none bg-slate-50 border border-slate-200 rounded-md pl-2.5 pr-6 py-1 text-xs font-medium text-slate-700 cursor-pointer focus:outline-none"
          >
            <option value="Last 30 Days">Last 30 Days</option>
            <option value="Last 60 Days">Last 60 Days</option>
            <option value="Full Season">Full Season</option>
          </select>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 absolute right-1.5 top-1/2 -translate-y-1/2 pointer-events-none" />
        </div>
      </div>

      {/* Chart Canvas */}
      <div className="w-full h-44">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={data} margin={{ top: 10, right: 10, left: -25, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f1f5f9" />
            <XAxis
              dataKey="date"
              axisLine={false}
              tickLine={false}
              tick={{ fontSize: 10.5, fill: '#64748b' }}
              interval="preserveStartEnd"
            />
            <YAxis
              domain={[0, 1.0]}
              ticks={[0, 0.5, 1.0]}
              axisLine={false}
              tickLine={false}
              tick={{ fontSize: 10.5, fill: '#64748b' }}
            />
            <Tooltip
              content={({ active, payload }) => {
                if (active && payload && payload.length) {
                  const val = payload[0].value as number;
                  return (
                    <div className="bg-slate-900 text-white px-2.5 py-1.5 rounded-lg shadow-lg text-[11px] space-y-0.5">
                      <p className="font-semibold">{payload[0].payload.fullDate || payload[0].payload.date}</p>
                      <p className="text-emerald-400 font-bold">NDVI: {val.toFixed(2)}</p>
                    </div>
                  );
                }
                return null;
              }}
            />
            <Line
              type="monotone"
              dataKey="ndvi"
              stroke="#22c55e"
              strokeWidth={2.5}
              dot={{ r: 3, fill: '#22c55e', stroke: '#ffffff', strokeWidth: 1.5 }}
              activeDot={{ r: 5, fill: '#16a34a', stroke: '#ffffff', strokeWidth: 2 }}
            />
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
