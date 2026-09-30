/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        forest: {
          950: '#051812',
          900: '#08231a',
          850: '#0B2B20',
          800: '#0e3427',
          700: '#144c39',
          600: '#1a644c',
          active: '#15803d',
        },
        health: {
          healthy: '#22c55e',
          'healthy-dark': '#16a34a',
          moderate: '#eab308',
          'moderate-dark': '#ca8a04',
          stress: '#f97316',
          'stress-dark': '#ea580c',
          insufficient: '#ef4444',
          'insufficient-dark': '#dc2626',
          nodata: '#64748b',
        }
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'sans-serif'],
      },
      boxShadow: {
        'card': '0 1px 3px 0 rgba(0, 0, 0, 0.05), 0 1px 2px 0 rgba(0, 0, 0, 0.03)',
        'panel': '0 4px 6px -1px rgba(0, 0, 0, 0.08), 0 2px 4px -1px rgba(0, 0, 0, 0.04)',
      }
    },
  },
  plugins: [],
}
