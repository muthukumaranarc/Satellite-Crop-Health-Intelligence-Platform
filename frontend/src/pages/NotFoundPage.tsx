import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Home } from 'lucide-react';

export const NotFoundPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center justify-center h-[calc(100vh-10rem)] text-center p-4">
      <h1 className="text-4xl font-extrabold text-slate-900">404</h1>
      <p className="text-sm text-slate-500 mt-2">The requested view or field record does not exist.</p>
      <button
        onClick={() => navigate('/dashboard')}
        className="mt-4 flex items-center space-x-2 px-4 py-2 bg-[#15803D] hover:bg-emerald-700 text-white rounded-lg text-xs font-semibold transition-colors"
      >
        <Home className="w-4 h-4" />
        <span>Return to Dashboard</span>
      </button>
    </div>
  );
};
