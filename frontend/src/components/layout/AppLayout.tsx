import React, { useState } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { ForestSidebar } from './ForestSidebar';
import { TopHeader } from './TopHeader';
import { FilterToolbar } from './FilterToolbar';

export const AppLayout: React.FC = () => {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const navigate = useNavigate();

  const handleExport = () => {
    alert('Exporting current view data to GeoJSON & CSV report...');
  };

  const handleOpenFilters = () => {
    navigate('/map');
  };

  return (
    <div className="flex h-screen w-screen overflow-hidden bg-slate-50 font-sans">
      {/* Dark Forest Green Sidebar */}
      {sidebarOpen && <ForestSidebar alertCount={12} />}

      {/* Main Right Content Panel */}
      <div className="flex-1 flex flex-col h-full overflow-hidden">
        {/* Top Header Navbar */}
        <TopHeader onToggleSidebar={() => setSidebarOpen((prev) => !prev)} />

        {/* Sub-Header Filter Bar */}
        <FilterToolbar
          onOpenAdvancedFilters={handleOpenFilters}
          onExport={handleExport}
        />

        {/* Viewport Content */}
        <main className="flex-1 overflow-y-auto p-4 bg-[#F8FAFC]">
          <Outlet />
        </main>
      </div>
    </div>
  );
};
