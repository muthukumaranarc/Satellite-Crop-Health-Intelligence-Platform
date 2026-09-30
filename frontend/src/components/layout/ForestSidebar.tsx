import React from 'react';
import { NavLink } from 'react-router-dom';
import {
  LayoutDashboard,
  Map as MapIcon,
  Grid3X3,
  BarChart3,
  Bell,
  FileText,
  Radio,
  Activity,
  CloudSun,
  Users,
  Settings,
  Code2,
  ChevronDown,
  Building2,
  Leaf
} from 'lucide-react';

interface SidebarProps {
  alertCount?: number;
}

export const ForestSidebar: React.FC<SidebarProps> = ({ alertCount = 12 }) => {
  const mainNav = [
    { label: 'Dashboard', path: '/dashboard', icon: LayoutDashboard },
    { label: 'Map', path: '/map', icon: MapIcon },
    { label: 'Fields', path: '/fields', icon: Grid3X3 },
    { label: 'Analytics', path: '/analytics', icon: BarChart3 },
    { label: 'Alerts', path: '/alerts', icon: Bell, badge: alertCount },
    { label: 'Reports', path: '/reports', icon: FileText },
  ];

  const dataNav = [
    { label: 'Imagery', path: '/imagery', icon: Radio },
    { label: 'Indices', path: '/indices', icon: Activity },
    { label: 'Weather', path: '/weather', icon: CloudSun },
  ];

  const systemNav = [
    { label: 'Users', path: '/users', icon: Users },
    { label: 'Settings', path: '/settings', icon: Settings },
    { label: 'API Docs', path: '/docs', icon: Code2 },
  ];

  return (
    <aside className="w-64 bg-[#0B2B20] text-slate-300 flex flex-col h-screen select-none shrink-0 border-r border-[#0d3427] z-30">
      {/* Brand Header */}
      <div className="p-5 pb-6 flex items-center space-x-3">
        <div className="w-10 h-10 rounded-lg bg-emerald-700/40 border border-emerald-500/30 flex items-center justify-center text-emerald-400">
          <Leaf className="w-6 h-6 fill-emerald-500/20" />
        </div>
        <div>
          <h1 className="font-bold text-white text-lg tracking-tight leading-none">AgriSight</h1>
          <p className="text-[11px] text-emerald-400/80 font-normal mt-1 leading-tight">
            Satellite Crop Health<br />Intelligence Platform
          </p>
        </div>
      </div>

      {/* Navigation Scrollable Area */}
      <div className="flex-1 overflow-y-auto px-3 space-y-6 text-sm">
        {/* Dashboard prominent item */}
        <div>
          <NavLink
            to="/dashboard"
            className={({ isActive }) =>
              `flex items-center space-x-3 px-3 py-2.5 rounded-lg font-medium transition-colors ${
                isActive
                  ? 'bg-[#15803D] text-white shadow-sm'
                  : 'text-slate-300 hover:bg-white/5 hover:text-white'
              }`
            }
          >
            <LayoutDashboard className="w-4 h-4 shrink-0" />
            <span className="text-[13.5px]">Dashboard</span>
          </NavLink>
        </div>

        {/* MAIN Group */}
        <div>
          <div className="text-[10.5px] uppercase tracking-wider font-semibold text-emerald-300/50 px-3 mb-2">
            Main
          </div>
          <div className="space-y-1">
            {mainNav.slice(1).map((item) => {
              const Icon = item.icon;
              return (
                <NavLink
                  key={item.path}
                  to={item.path}
                  className={({ isActive }) =>
                    `flex items-center justify-between px-3 py-2 rounded-lg transition-colors ${
                      isActive
                        ? 'bg-[#15803D] text-white font-medium'
                        : 'text-slate-300 hover:bg-white/5 hover:text-white'
                    }`
                  }
                >
                  <div className="flex items-center space-x-3">
                    <Icon className="w-4 h-4 shrink-0 opacity-80" />
                    <span className="text-[13px]">{item.label}</span>
                  </div>
                  {item.badge !== undefined && (
                    <span className="bg-emerald-500/20 border border-emerald-500/40 text-emerald-300 text-[11px] font-semibold px-2 py-0.5 rounded-full">
                      {item.badge}
                    </span>
                  )}
                </NavLink>
              );
            })}
          </div>
        </div>

        {/* DATA Group */}
        <div>
          <div className="text-[10.5px] uppercase tracking-wider font-semibold text-emerald-300/50 px-3 mb-2">
            Data
          </div>
          <div className="space-y-1">
            {dataNav.map((item) => {
              const Icon = item.icon;
              return (
                <NavLink
                  key={item.path}
                  to={item.path}
                  className={({ isActive }) =>
                    `flex items-center space-x-3 px-3 py-2 rounded-lg transition-colors ${
                      isActive
                        ? 'bg-[#15803D] text-white font-medium'
                        : 'text-slate-300 hover:bg-white/5 hover:text-white'
                    }`
                  }
                >
                  <Icon className="w-4 h-4 shrink-0 opacity-80" />
                  <span className="text-[13px]">{item.label}</span>
                </NavLink>
              );
            })}
          </div>
        </div>

        {/* SYSTEM Group */}
        <div>
          <div className="text-[10.5px] uppercase tracking-wider font-semibold text-emerald-300/50 px-3 mb-2">
            System
          </div>
          <div className="space-y-1">
            {systemNav.map((item) => {
              const Icon = item.icon;
              return (
                <NavLink
                  key={item.path}
                  to={item.path}
                  className={({ isActive }) =>
                    `flex items-center space-x-3 px-3 py-2 rounded-lg transition-colors ${
                      isActive
                        ? 'bg-[#15803D] text-white font-medium'
                        : 'text-slate-300 hover:bg-white/5 hover:text-white'
                    }`
                  }
                >
                  <Icon className="w-4 h-4 shrink-0 opacity-80" />
                  <span className="text-[13px]">{item.label}</span>
                </NavLink>
              );
            })}
          </div>
        </div>
      </div>

      {/* Organization Footer Dropdown */}
      <div className="p-3 border-t border-[#0e372a]">
        <button className="w-full flex items-center justify-between p-2.5 rounded-xl bg-white/5 hover:bg-white/10 transition-colors text-left border border-white/5">
          <div className="flex items-center space-x-2.5 overflow-hidden">
            <div className="w-7 h-7 rounded-lg bg-emerald-600/30 flex items-center justify-center text-emerald-400 shrink-0">
              <Building2 className="w-4 h-4" />
            </div>
            <div className="truncate">
              <p className="text-[10px] uppercase text-emerald-400/80 font-medium">Organization</p>
              <p className="text-xs font-medium text-white truncate">Agriculture Dept.</p>
            </div>
          </div>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400 shrink-0 ml-1" />
        </button>
      </div>
    </aside>
  );
};
