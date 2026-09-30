import React from 'react';
import { X, ExternalLink, AlertTriangle, ShieldCheck, Activity, Calendar } from 'lucide-react';
import { Field } from '../../types';
import { getHealthColorStyle } from '../../utils/healthColors';

interface FieldDetailDrawerProps {
  field: Field | null;
  onClose: () => void;
  onNavigateToDetail?: (fieldId: string) => void;
}

export const FieldDetailDrawer: React.FC<FieldDetailDrawerProps> = ({
  field,
  onClose,
  onNavigateToDetail,
}) => {
  if (!field) return null;

  const healthStyle = getHealthColorStyle(field.healthClass);

  return (
    <div className="absolute top-4 right-4 z-400 w-80 bg-white/95 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-200 p-4 transition-all duration-300 animate-in fade-in slide-in-from-right">
      {/* Header */}
      <div className="flex items-start justify-between pb-3 border-b border-slate-100">
        <div>
          <div className="flex items-center space-x-2">
            <h3 className="font-bold text-slate-800 text-sm tracking-tight">{field.name}</h3>
            <span className="text-[11px] text-slate-500 font-medium">— {field.block}</span>
          </div>
          <p className="text-[11px] text-slate-400 mt-0.5">{field.region} • {field.crop}</p>
        </div>
        <button
          onClick={onClose}
          className="p-1 rounded-full text-slate-400 hover:text-slate-600 hover:bg-slate-100 transition-colors"
        >
          <X className="w-4 h-4" />
        </button>
      </div>

      {/* Health Badge */}
      <div className="my-3 flex items-center justify-between p-2 rounded-xl bg-slate-50 border border-slate-100">
        <span className="text-xs font-medium text-slate-600">Status</span>
        <span className={`inline-flex items-center space-x-1.5 px-2.5 py-1 rounded-full text-xs font-semibold ${healthStyle.badgeBg} ${healthStyle.badgeBorder} ${healthStyle.badgeText} border`}>
          <span className={`w-2 h-2 rounded-full ${healthStyle.bg}`} />
          <span>{healthStyle.shortLabel}</span>
        </span>
      </div>

      {/* Metrics Grid */}
      <div className="grid grid-cols-2 gap-2 text-xs mb-3">
        <div className="p-2.5 rounded-xl bg-slate-50 border border-slate-100">
          <p className="text-[10.5px] text-slate-400 font-medium">NDVI</p>
          <p className="text-base font-bold text-slate-800 mt-0.5">{field.currentNdvi.toFixed(2)}</p>
        </div>
        <div className="p-2.5 rounded-xl bg-slate-50 border border-slate-100">
          <p className="text-[10.5px] text-slate-400 font-medium">NDRE</p>
          <p className="text-base font-bold text-slate-800 mt-0.5">{field.currentNdre.toFixed(2)}</p>
        </div>
        <div className="p-2.5 rounded-xl bg-slate-50 border border-slate-100">
          <p className="text-[10.5px] text-slate-400 font-medium">Area</p>
          <p className="text-sm font-bold text-slate-800 mt-0.5">{field.areaHa} ha</p>
        </div>
        <div className="p-2.5 rounded-xl bg-slate-50 border border-slate-100">
          <p className="text-[10.5px] text-slate-400 font-medium">Stress Score</p>
          <p className={`text-sm font-bold mt-0.5 ${field.stressScore > 50 ? 'text-orange-600' : 'text-slate-800'}`}>
            {field.stressScore}%
          </p>
        </div>
      </div>

      {/* Scientific Caution Note */}
      <div className="p-2.5 rounded-xl bg-amber-50/70 border border-amber-200/60 text-[11px] text-amber-800 leading-snug flex items-start space-x-2 mb-3">
        <AlertTriangle className="w-4 h-4 text-amber-600 shrink-0 mt-0.5" />
        <span>
          Satellite indicators reflect vegetation vigor anomalies. Ground inspection is advised for diagnosis.
        </span>
      </div>

      {/* Action Button */}
      <button
        onClick={() => onNavigateToDetail?.(field.id)}
        className="w-full flex items-center justify-center space-x-2 py-2 px-3 rounded-xl bg-[#15803D] hover:bg-emerald-700 text-white text-xs font-semibold transition-colors shadow-sm"
      >
        <span>View Full Field Dossier</span>
        <ExternalLink className="w-3.5 h-3.5" />
      </button>
    </div>
  );
};
