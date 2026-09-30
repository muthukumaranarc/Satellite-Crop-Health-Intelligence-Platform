import React, { useState } from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from 'recharts';
import { ChevronDown } from 'lucide-react';
import { HealthPeriodDataPoint } from '../../types';

interface HealthOverTimeBarsProps {
  data: HealthPeriodDataPoint[];
}

export const HealthOverTimeBars: React.FC<HealthOverTimeBarsProps> = ({ data }) => {
  const [period, setPeriod] = useState('Last 6 Periods');

  return (
    <div className="bg-white rounded-xl border border-slate-200 p-4 shadow-card flex flex-col justify-between h-full">
      {/* Header */}
      <div className="flex items-center justify-between mb-2">
        <h3 className="text-sm font-bold text-slate-800 tracking-tight">Health Over Time (by Area)</h3>
        <div className="relative">
          <select
            value={period}
            onChange={(e) => setPeriod(e.target.value)}
            className="appearance-none bg-slate-50 border border-slate-200 rounded-md pl-2.5 pr-6 py-1 text-xs font-medium text-slate-700 cursor-pointer focus:outline-none"
          >
            <option value="Last 6 Periods">Last 6 Periods</option>
            <option value="Last 12 Periods">Last 12 Periods</option>
            <option value="Season 2024">Season 2024</option>
          </select>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 absolute right-1.5 top-1/2 -translate-y-1/2 pointer-events-none" />
        </div>
      </div>

      {/* Stacked 100% Bar Chart */}
      <div className="w-full h-40">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={data} stackOffset="expand" margin={{ top: 10, right: 10, left: -25, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f1f5f9" />
            <XAxis
              dataKey="period"
              axisLine={false}
              tickLine={false}
              tick={{ fontSize: 10.5, fill: '#64748b' }}
            />
            <YAxis
              tickFormatter={(tick) => `${Math.round(tick * 100)}%`}
              ticks={[0, 0.5, 1.0]}
              axisLine={false}
              tickLine={false}
              tick={{ fontSize: 10.5, fill: '#64748b' }}
            />
            <Tooltip
              formatter={(value: any, name: any) => [
                `${Number(value).toFixed(1)}%`,
                name === 'healthy'
                  ? 'Healthy'
                  : name === 'moderate'
                  ? 'Moderate'
                  : name === 'potentialStress'
                  ? 'Potential Stress'
                  : 'Insufficient / No Data',
              ]}
              contentStyle={{
                backgroundColor: '#0f172a',
                borderColor: '#1e293b',
                color: '#fff',
                borderRadius: '0.5rem',
                fontSize: '11px',
              }}
            />
            {/* Stacking: from bottom Insufficient -> Potential Stress -> Moderate -> Healthy */}
            <Bar dataKey="insufficientData" stackId="a" fill="#ef4444" radius={[0, 0, 0, 0]} />
            <Bar dataKey="potentialStress" stackId="a" fill="#f97316" radius={[0, 0, 0, 0]} />
            <Bar dataKey="moderate" stackId="a" fill="#eab308" radius={[0, 0, 0, 0]} />
            <Bar dataKey="healthy" stackId="a" fill="#22c55e" radius={[2, 2, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>

      {/* Bottom Horizontal Legend */}
      <div className="flex items-center justify-center space-x-3 pt-2 text-[10.5px] text-slate-600 border-t border-slate-100">
        <div className="flex items-center space-x-1.5">
          <span className="w-2 h-2 rounded-full bg-[#22c55e]" />
          <span>Healthy</span>
        </div>
        <div className="flex items-center space-x-1.5">
          <span className="w-2 h-2 rounded-full bg-[#eab308]" />
          <span>Moderate</span>
        </div>
        <div className="flex items-center space-x-1.5">
          <span className="w-2 h-2 rounded-full bg-[#f97316]" />
          <span>Potential Stress</span>
        </div>
        <div className="flex items-center space-x-1.5">
          <span className="w-2 h-2 rounded-full bg-[#ef4444]" />
          <span>Insufficient / No Data</span>
        </div>
      </div>
    </div>
  );
};
