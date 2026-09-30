import React from 'react';
import { Calendar, SlidersHorizontal, Download, ChevronDown } from 'lucide-react';
import { useFilters } from '../../context/FilterContext';

interface FilterToolbarProps {
  onOpenAdvancedFilters?: () => void;
  onExport?: () => void;
}

export const FilterToolbar: React.FC<FilterToolbarProps> = ({
  onOpenAdvancedFilters,
  onExport,
}) => {
  const { filters, setRegion, setField, setCrop, setDateRange } = useFilters();

  return (
    <div className="bg-white border-b border-slate-200 px-4 py-2.5 flex flex-wrap items-center justify-between gap-3 shrink-0">
      {/* Dropdown Filters Group */}
      <div className="flex flex-wrap items-center gap-2.5">
        {/* Region */}
        <div className="flex items-center space-x-2 bg-slate-50 border border-slate-200 rounded-lg px-3 py-1.5 hover:bg-slate-100 transition-colors cursor-pointer">
          <div className="text-[11px] text-slate-400 font-medium">Region</div>
          <select
            value={filters.region}
            onChange={(e) => setRegion(e.target.value)}
            className="bg-transparent text-xs font-semibold text-slate-800 cursor-pointer focus:outline-none pr-1"
          >
            <option value="Malwa Region">Malwa Region</option>
            <option value="Doaba Region">Doaba Region</option>
            <option value="Majha Region">Majha Region</option>
          </select>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 pointer-events-none" />
        </div>

        {/* Field */}
        <div className="flex items-center space-x-2 bg-slate-50 border border-slate-200 rounded-lg px-3 py-1.5 hover:bg-slate-100 transition-colors cursor-pointer">
          <div className="text-[11px] text-slate-400 font-medium">Field</div>
          <select
            value={filters.field}
            onChange={(e) => setField(e.target.value)}
            className="bg-transparent text-xs font-semibold text-slate-800 cursor-pointer focus:outline-none pr-1"
          >
            <option value="All Fields">All Fields</option>
            <option value="Field 245 - North Block">Field 245</option>
            <option value="Field 128 - West Zone">Field 128</option>
            <option value="Field 367 - South Block">Field 367</option>
            <option value="Field 450 - Central Zone">Field 450</option>
          </select>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 pointer-events-none" />
        </div>

        {/* Crop */}
        <div className="flex items-center space-x-2 bg-slate-50 border border-slate-200 rounded-lg px-3 py-1.5 hover:bg-slate-100 transition-colors cursor-pointer">
          <div className="text-[11px] text-slate-400 font-medium">Crop</div>
          <select
            value={filters.crop}
            onChange={(e) => setCrop(e.target.value)}
            className="bg-transparent text-xs font-semibold text-slate-800 cursor-pointer focus:outline-none pr-1"
          >
            <option value="Wheat">Wheat</option>
            <option value="Rice">Rice</option>
            <option value="Cotton">Cotton</option>
            <option value="Maize">Maize</option>
          </select>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 pointer-events-none" />
        </div>

        {/* Date Range */}
        <div className="flex items-center space-x-2 bg-slate-50 border border-slate-200 rounded-lg px-3 py-1.5 hover:bg-slate-100 transition-colors cursor-pointer">
          <div className="text-[11px] text-slate-400 font-medium">Date Range</div>
          <div className="flex items-center space-x-1.5">
            <Calendar className="w-3.5 h-3.5 text-slate-500" />
            <select
              value={filters.dateRange}
              onChange={(e) => setDateRange(e.target.value)}
              className="bg-transparent text-xs font-semibold text-slate-800 cursor-pointer focus:outline-none pr-1"
            >
              <option value="Apr 20 – May 20, 2024">Apr 20 – May 20, 2024</option>
              <option value="May 01 – May 20, 2024">May 01 – May 20, 2024</option>
              <option value="Last 30 Days">Last 30 Days</option>
              <option value="Full Season (2024)">Full Season (2024)</option>
            </select>
          </div>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 pointer-events-none" />
        </div>
      </div>

      {/* Action Buttons */}
      <div className="flex items-center space-x-2">
        <button
          onClick={onOpenAdvancedFilters}
          className="flex items-center space-x-1.5 px-3 py-1.5 text-xs font-medium text-slate-700 bg-white border border-slate-200 rounded-lg hover:bg-slate-50 transition-colors shadow-2xs"
        >
          <SlidersHorizontal className="w-3.5 h-3.5 text-slate-500" />
          <span>Filters</span>
        </button>

        <button
          onClick={onExport}
          className="flex items-center space-x-1.5 px-3.5 py-1.5 text-xs font-semibold text-white bg-[#15803D] hover:bg-emerald-700 rounded-lg transition-colors shadow-2xs"
        >
          <Download className="w-3.5 h-3.5" />
          <span>Export</span>
        </button>
      </div>
    </div>
  );
};
