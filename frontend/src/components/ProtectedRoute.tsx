import { Navigate, Outlet } from 'react-router-dom';

export default function ProtectedRoute() {
  // Read directly from localStorage so the check is synchronous — avoids a
  // race where navigate() fires before the AuthContext state update flushes.
  const token = localStorage.getItem('sz_token');
  return token ? <Outlet /> : <Navigate to="/login" replace />;
}
