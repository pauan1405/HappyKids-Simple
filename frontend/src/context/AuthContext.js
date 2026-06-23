import React, { createContext, useContext, useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);      // { token, correo, rol, nombre, clienteId, usuarioId }
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const stored = localStorage.getItem('hk_user');
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        // Verificar que el token no haya expirado
        const decoded = jwtDecode(parsed.token);
        if (decoded.exp * 1000 > Date.now()) {
          setUser(parsed);
        } else {
          localStorage.removeItem('hk_user');
        }
      } catch {
        localStorage.removeItem('hk_user');
      }
    }
    setLoading(false);
  }, []);

  const login = (userData) => {
    localStorage.setItem('hk_user', JSON.stringify(userData));
    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem('hk_user');
    setUser(null);
  };

  const isAdmin = () => user?.rol === 'ADMIN';
  const isAuthenticated = () => !!user;

  return (
    <AuthContext.Provider value={{ user, login, logout, isAdmin, isAuthenticated, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
