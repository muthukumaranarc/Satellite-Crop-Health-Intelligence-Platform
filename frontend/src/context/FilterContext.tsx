import React, { createContext, useContext, useState } from 'react';
import { GlobalFilterState } from '../types';

interface FilterContextType {
  filters: GlobalFilterState;
  setRegion: (region: string) => void;
  setField: (field: string) => void;
  setCrop: (crop: string) => void;
  setDateRange: (range: string) => void;
  resetFilters: () => void;
}

const initialFilters: GlobalFilterState = {
  region: 'Malwa Region',
  field: 'All Fields',
  crop: 'Wheat',
  dateRange: 'Apr 20 – May 20, 2024',
};

const FilterContext = createContext<FilterContextType | undefined>(undefined);

export const FilterProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [filters, setFilters] = useState<GlobalFilterState>(initialFilters);

  const setRegion = (region: string) => setFilters((prev) => ({ ...prev, region }));
  const setField = (field: string) => setFilters((prev) => ({ ...prev, field }));
  const setCrop = (crop: string) => setFilters((prev) => ({ ...prev, crop }));
  const setDateRange = (dateRange: string) => setFilters((prev) => ({ ...prev, dateRange }));
  const resetFilters = () => setFilters(initialFilters);

  return (
    <FilterContext.Provider value={{ filters, setRegion, setField, setCrop, setDateRange, resetFilters }}>
      {children}
    </FilterContext.Provider>
  );
};

export const useFilters = () => {
  const context = useContext(FilterContext);
  if (!context) {
    throw new Error('useFilters must be used within a FilterProvider');
  }
  return context;
};
