import React, { createContext, useContext, useState, useCallback } from 'react';
import type { AuthResponseDTO } from '../types';

interface AuthContextValue {
  user: AuthResponseDTO | null;
  token: string | null;
  login: (data: AuthResponseDTO) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

function loadUser(): AuthResponseDTO | null {
  try {
    const raw = localStorage.getItem('sz_user');
    return raw ? (JSON.parse(raw) as AuthResponseDTO) : null;
  } catch {
    return null;
  }
}

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<AuthResponseDTO | null>(loadUser);
  const [token, setToken] = useState<string | null>(localStorage.getItem('sz_token'));

  const login = useCallback((data: AuthResponseDTO) => {
    localStorage.setItem('sz_token', data.token);
    localStorage.setItem('sz_user', JSON.stringify(data));
    setToken(data.token);
    setUser(data);
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('sz_token');
    localStorage.removeItem('sz_user');
    setToken(null);
    setUser(null);
  }, []);

  return (
    <AuthContext.Provider
      value={{ user, token, login, logout, isAuthenticated: !!token }}
    >
      {children}
    </AuthContext.Provider>
  );
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
}
