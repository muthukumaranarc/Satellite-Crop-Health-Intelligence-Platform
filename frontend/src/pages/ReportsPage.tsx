import React from 'react';
import { FileText, Download, CheckCircle, BarChart2 } from 'lucide-react';
import { MALWA_HEALTH_SUMMARY } from '../mock/malwaData';

export const ReportsPage: React.FC = () => {
  return (
    <div className="space-y-4 max-w-[1400px] mx-auto pb-8 select-none">
      <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card flex items-center justify-between">
        <div>
          <h1 className="text-lg font-bold text-slate-900 tracking-tight">Intelligence Reports & Exports</h1>
          <p className="text-xs text-slate-500 mt-1">
            Automated executive summaries and geospatial exports for agricultural extension officers.
          </p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-xs">
        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card space-y-3">
          <div className="flex items-center space-x-2">
            <FileText className="w-5 h-5 text-emerald-600" />
            <h3 className="font-bold text-slate-900 text-sm">Malwa Wheat Crop Health Synthesis (May 2024)</h3>
          </div>
          <p className="text-slate-500 leading-relaxed">
            Covers 2,543 hectares across Rampura, Kothla, and Badalgarh. Contains health distribution metrics, flagged anomalies, and decline velocity calculations.
          </p>
          <div className="bg-slate-50 p-3 rounded-lg border border-slate-100 space-y-1 text-slate-600">
            <p>• Healthy Area: <strong>{MALWA_HEALTH_SUMMARY.healthyHa} ha ({MALWA_HEALTH_SUMMARY.healthyPercent}%)</strong></p>
            <p>• Potential Stress Area: <strong>{MALWA_HEALTH_SUMMARY.potentialStressHa} ha ({MALWA_HEALTH_SUMMARY.potentialStressPercent}%)</strong></p>
            <p>• Active Triage Flags: <strong>4 critical field alerts</strong></p>
          </div>
          <button
            onClick={() => alert('Downloading Executive Health Report (PDF)...')}
            className="w-full py-2 bg-[#15803D] hover:bg-emerald-700 text-white font-semibold rounded-lg flex items-center justify-center space-x-1.5 transition-colors"
          >
            <Download className="w-4 h-4" />
            <span>Download PDF Report</span>
          </button>
        </div>

        <div className="bg-white rounded-xl border border-slate-200 p-5 shadow-card space-y-3">
          <div className="flex items-center space-x-2">
            <BarChart2 className="w-5 h-5 text-emerald-600" />
            <h3 className="font-bold text-slate-900 text-sm">Geospatial Vector Export (GeoJSON / Shapefile)</h3>
          </div>
          <p className="text-slate-500 leading-relaxed">
            Download raw GIS polygon layers with embedded NDVI, NDRE, and stress score attribute tables for use in QGIS, ArcGIS, or custom GIS pipelines.
          </p>
          <div className="bg-slate-50 p-3 rounded-lg border border-slate-100 space-y-1 text-slate-600">
            <p>• Projection: <strong>WGS 84 (EPSG:4326)</strong></p>
            <p>• Feature Count: <strong>15 field boundary polygons</strong></p>
            <p>• Attribute Format: <strong>GeoJSON RFC 7946 compliant</strong></p>
          </div>
          <button
            onClick={() => alert('Exporting GeoJSON vector feature collection...')}
            className="w-full py-2 bg-slate-800 hover:bg-slate-900 text-white font-semibold rounded-lg flex items-center justify-center space-x-1.5 transition-colors"
          >
            <Download className="w-4 h-4" />
            <span>Export GeoJSON Dataset</span>
          </button>
        </div>
      </div>
    </div>
  );
};
