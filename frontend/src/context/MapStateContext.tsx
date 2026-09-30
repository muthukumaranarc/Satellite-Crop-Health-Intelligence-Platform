import React, { createContext, useContext, useState } from 'react';
import { MapLayerState } from '../types';

interface MapStateContextType {
  mapState: MapLayerState;
  setIndex: (index: 'NDVI' | 'NDRE' | 'NDMI') => void;
  setDate: (date: string) => void;
  setOpacity: (opacity: number) => void;
  setBasemap: (basemap: 'satellite' | 'street') => void;
  selectedFieldId: string | null;
  setSelectedFieldId: (id: string | null) => void;
}

const initialMapState: MapLayerState = {
  index: 'NDVI',
  date: 'May 20, 2024',
  opacity: 0.75,
  basemap: 'satellite',
};

const MapStateContext = createContext<MapStateContextType | undefined>(undefined);

export const MapStateProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [mapState, setMapState] = useState<MapLayerState>(initialMapState);
  const [selectedFieldId, setSelectedFieldId] = useState<string | null>(null);

  const setIndex = (index: 'NDVI' | 'NDRE' | 'NDMI') =>
    setMapState((prev) => ({ ...prev, index }));
  const setDate = (date: string) =>
    setMapState((prev) => ({ ...prev, date }));
  const setOpacity = (opacity: number) =>
    setMapState((prev) => ({ ...prev, opacity }));
  const setBasemap = (basemap: 'satellite' | 'street') =>
    setMapState((prev) => ({ ...prev, basemap }));

  return (
    <MapStateContext.Provider
      value={{
        mapState,
        setIndex,
        setDate,
        setOpacity,
        setBasemap,
        selectedFieldId,
        setSelectedFieldId,
      }}
    >
      {children}
    </MapStateContext.Provider>
  );
};

export const useMapState = () => {
  const context = useContext(MapStateContext);
  if (!context) {
    throw new Error('useMapState must be used within a MapStateProvider');
  }
  return context;
};
