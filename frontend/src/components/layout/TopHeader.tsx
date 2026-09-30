import React from 'react';
import { Search, Bell, HelpCircle, ChevronDown, Menu, User } from 'lucide-react';

interface TopHeaderProps {
  onToggleSidebar?: () => void;
}

export const TopHeader: React.FC<TopHeaderProps> = ({ onToggleSidebar }) => {
  return (
    <header className="h-14 bg-white border-b border-slate-200 px-4 flex items-center justify-between shrink-0 z-20">
      {/* Left: Hamburger and Search */}
      <div className="flex items-center space-x-3 flex-1 max-w-xl">
        <button
          onClick={onToggleSidebar}
          className="p-1.5 rounded-lg text-slate-500 hover:text-slate-700 hover:bg-slate-100 transition-colors"
          title="Toggle Navigation"
        >
          <Menu className="w-5 h-5" />
        </button>

        <div className="relative w-full max-w-md">
          <Search className="w-4 h-4 text-slate-400 absolute left-3 top-1/2 -translate-y-1/2 pointer-events-none" />
          <input
            type="text"
            placeholder="Search fields, regions, crops..."
            className="w-full bg-slate-50 border border-slate-200 rounded-lg pl-9 pr-8 py-1.5 text-xs text-slate-700 placeholder-slate-400 focus:outline-none focus:ring-1 focus:ring-emerald-500 focus:border-emerald-500 transition-all"
          />
          <div className="absolute right-2.5 top-1/2 -translate-y-1/2 pointer-events-none">
            <kbd className="text-[10px] font-medium text-slate-400 bg-white border border-slate-200 px-1.5 py-0.5 rounded shadow-2xs">
              /
            </kbd>
          </div>
        </div>
      </div>

      {/* Right: Notifications, Help, Profile */}
      <div className="flex items-center space-x-3">
        {/* Notifications */}
        <button className="relative p-2 text-slate-500 hover:text-slate-700 hover:bg-slate-100 rounded-full transition-colors">
          <Bell className="w-4 h-4" />
          <span className="absolute top-1.5 right-1.5 w-4 h-4 bg-red-500 text-white text-[9px] font-bold rounded-full flex items-center justify-center border-2 border-white">
            3
          </span>
        </button>

        {/* Help */}
        <button className="p-2 text-slate-500 hover:text-slate-700 hover:bg-slate-100 rounded-full transition-colors" title="Documentation & Help">
          <HelpCircle className="w-4 h-4" />
        </button>

        <div className="h-5 w-px bg-slate-200 mx-1" />

        {/* User Profile */}
        <button className="flex items-center space-x-2 pl-1 pr-2 py-1 rounded-lg hover:bg-slate-50 transition-colors">
          <div className="w-7 h-7 rounded-full bg-emerald-100 text-emerald-800 border border-emerald-200 flex items-center justify-center font-medium text-xs">
            <User className="w-4 h-4" />
          </div>
          <span className="text-xs font-medium text-slate-700">Agricultural Officer</span>
          <ChevronDown className="w-3.5 h-3.5 text-slate-400" />
        </button>
      </div>
    </header>
  );
};
