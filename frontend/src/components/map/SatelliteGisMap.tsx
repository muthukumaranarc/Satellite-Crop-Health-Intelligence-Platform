import React, { useRef, useMemo } from 'react';
import { MapContainer, TileLayer, GeoJSON, useMap } from 'react-leaflet';
import L from 'leaflet';
import { Field, HealthClass } from '../../types';
import { MALWA_FIELDS, MAP_INITIAL_CENTER, MAP_INITIAL_ZOOM } from '../../mock/malwaData';
import { useMapState } from '../../context/MapStateContext';
import { getHealthColorStyle } from '../../utils/healthColors';
import { MapIndexControls } from './MapIndexControls';
import { MapLegendOverlay } from './MapLegendOverlay';
import { MapToolControls } from './MapToolControls';
import { FieldDetailDrawer } from './FieldDetailDrawer';

// Child component to control map programmatic actions (zoom, center)
const MapController: React.FC<{
  center: [number, number];
  zoom: number;
  controllerRef: React.MutableRefObject<any>;
}> = ({ center, zoom, controllerRef }) => {
  const map = useMap();

  React.useEffect(() => {
    controllerRef.current = {
      zoomIn: () => map.zoomIn(),
      zoomOut: () => map.zoomOut(),
      recenter: () => map.setView(center, zoom, { animate: true }),
      flyTo: (latLng: [number, number], targetZoom = 14) =>
        map.flyTo(latLng, targetZoom, { animate: true, duration: 1 }),
    };
  }, [map, center, zoom, controllerRef]);

  return null;
};

interface SatelliteGisMapProps {
  onNavigateToDetail?: (fieldId: string) => void;
  className?: string;
}

export const SatelliteGisMap: React.FC<SatelliteGisMapProps> = ({
  onNavigateToDetail,
  className = '',
}) => {
  const { mapState, selectedFieldId, setSelectedFieldId } = useMapState();
  const mapControllerRef = useRef<any>(null);

  const selectedField = useMemo(() => {
    return MALWA_FIELDS.find((f) => f.id === selectedFieldId) || null;
  }, [selectedFieldId]);

  // GeoJSON style function
  const styleFeature = (feature: any) => {
    const healthClass: HealthClass = feature?.properties?.healthClass || 'NO_DATA';
    const isSelected = selectedFieldId === feature?.properties?.id;
    const colors = getHealthColorStyle(healthClass);

    return {
      fillColor: colors.fill,
      fillOpacity: mapState.opacity,
      color: isSelected ? '#ffffff' : colors.stroke,
      weight: isSelected ? 3 : 1.5,
      opacity: 1,
      dashArray: isSelected ? '4, 4' : undefined,
    };
  };

  // GeoJSON feature interactions
  const onEachFeature = (feature: any, layer: L.Layer) => {
    const props = feature.properties;

    // Tooltip
    layer.bindTooltip(
      `<div class="p-1 text-xs">
        <p class="font-bold text-slate-900">${props.name} — ${props.block}</p>
        <p class="text-slate-600 text-[11px]">${props.crop} • NDVI: <strong class="text-emerald-700">${props.ndvi.toFixed(2)}</strong></p>
      </div>`,
      { sticky: true, className: 'leaflet-custom-tooltip' }
    );

    // Event listeners
    layer.on({
      mouseover: (e) => {
        const target = e.target;
        if (target.feature.properties.id !== selectedFieldId) {
          target.setStyle({
            weight: 2.5,
            color: '#ffffff',
            fillOpacity: Math.min(1, mapState.opacity + 0.15),
          });
        }
      },
      mouseout: (e) => {
        const target = e.target;
        if (target.feature.properties.id !== selectedFieldId) {
          target.setStyle(styleFeature(target.feature));
        }
      },
      click: () => {
        setSelectedFieldId(props.id);
      },
    });
  };

  // GeoJSON FeatureCollection
  const geoJsonData = useMemo(() => {
    return {
      type: 'FeatureCollection' as const,
      features: MALWA_FIELDS.map((f) => f.polygon),
    };
  }, []);

  return (
    <div className={`relative w-full h-full min-h-[420px] rounded-xl overflow-hidden border border-slate-200 shadow-card bg-slate-900 ${className}`}>
      {/* Leaflet Map Canvas */}
      <MapContainer
        center={MAP_INITIAL_CENTER}
        zoom={MAP_INITIAL_ZOOM}
        zoomControl={false}
        className="w-full h-full z-0"
        attributionControl={false}
      >
        <MapController
          center={MAP_INITIAL_CENTER}
          zoom={MAP_INITIAL_ZOOM}
          controllerRef={mapControllerRef}
        />

        {/* Dynamic Basemap Layer */}
        {mapState.basemap === 'satellite' ? (
          <TileLayer
            url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
            maxZoom={19}
          />
        ) : (
          <TileLayer
            url="https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png"
            maxZoom={19}
            subdomains="abcd"
          />
        )}

        {/* Polygons */}
        <GeoJSON
          key={`geojson-${mapState.index}-${mapState.opacity}-${selectedFieldId}`}
          data={geoJsonData}
          style={styleFeature}
          onEachFeature={onEachFeature}
        />
      </MapContainer>

      {/* Floating Top-Left: Index, Date, Opacity Controls */}
      <div className="absolute top-3 left-3 z-30 pointer-events-auto">
        <MapIndexControls />
      </div>

      {/* Floating Bottom-Left: Crop Health Legend */}
      <div className="absolute bottom-3 left-3 z-30 pointer-events-auto">
        <MapLegendOverlay />
      </div>

      {/* Floating Top-Right: Zoom, Basemap, Recenter */}
      <div className="absolute top-3 right-3 z-30 pointer-events-auto">
        <MapToolControls
          onZoomIn={() => mapControllerRef.current?.zoomIn()}
          onZoomOut={() => mapControllerRef.current?.zoomOut()}
          onRecenter={() => mapControllerRef.current?.recenter()}
        />
      </div>

      {/* Scale Indicator Bar at Bottom-Right */}
      <div className="absolute bottom-3 right-3 z-30 pointer-events-none bg-slate-900/80 backdrop-blur-xs text-white px-2 py-0.5 rounded text-[10px] font-mono border border-slate-700/60 flex items-center space-x-1.5">
        <div className="w-10 h-1 bg-white border border-black/40" />
        <span>1 km</span>
      </div>

      {/* Selected Field Inspection Drawer */}
      {selectedField && (
        <FieldDetailDrawer
          field={selectedField}
          onClose={() => setSelectedFieldId(null)}
          onNavigateToDetail={onNavigateToDetail}
        />
      )}
    </div>
  );
};
