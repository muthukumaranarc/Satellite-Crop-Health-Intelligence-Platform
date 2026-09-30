import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { FilterProvider } from './context/FilterContext';
import { MapStateProvider } from './context/MapStateContext';

import { AppLayout } from './components/layout/AppLayout';
import { DashboardPage } from './pages/DashboardPage';
import { MapExplorerPage } from './pages/MapExplorerPage';
import { FieldsListPage } from './pages/FieldsListPage';
import { FieldDetailPage } from './pages/FieldDetailPage';
import { AnalyticsPage } from './pages/AnalyticsPage';
import { AlertsPage } from './pages/AlertsPage';
import { ReportsPage } from './pages/ReportsPage';
import { ImageryPage } from './pages/ImageryPage';
import { IndicesPage } from './pages/IndicesPage';
import { WeatherPage } from './pages/WeatherPage';
import { SettingsPage } from './pages/SettingsPage';
import { NotFoundPage } from './pages/NotFoundPage';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      staleTime: 1000 * 60 * 5,
    },
  },
});

export const App: React.FC = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <FilterProvider>
        <MapStateProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<AppLayout />}>
                <Route index element={<Navigate to="/dashboard" replace />} />
                <Route path="dashboard" element={<DashboardPage />} />
                <Route path="map" element={<MapExplorerPage />} />
                <Route path="fields" element={<FieldsListPage />} />
                <Route path="fields/:id" element={<FieldDetailPage />} />
                <Route path="analytics" element={<AnalyticsPage />} />
                <Route path="alerts" element={<AlertsPage />} />
                <Route path="reports" element={<ReportsPage />} />
                <Route path="imagery" element={<ImageryPage />} />
                <Route path="indices" element={<IndicesPage />} />
                <Route path="weather" element={<WeatherPage />} />
                <Route path="users" element={<SettingsPage />} />
                <Route path="settings" element={<SettingsPage />} />
                <Route path="docs" element={<IndicesPage />} />
                <Route path="*" element={<NotFoundPage />} />
              </Route>
            </Routes>
          </BrowserRouter>
        </MapStateProvider>
      </FilterProvider>
    </QueryClientProvider>
  );
};

export default App;
