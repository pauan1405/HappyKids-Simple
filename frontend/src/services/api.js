import axios from 'axios';

// Detecta si está en Railway (usa la variable interna) o en tu PC (usa localhost)
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: `${API_BASE_URL}/api`,
  headers: { 'Content-Type': 'application/json' },
});

// Interceptor: adjunta el token JWT en cada petición
api.interceptors.request.use((config) => {
  const stored = localStorage.getItem('hk_user');
  if (stored) {
    try {
      const { token } = JSON.parse(stored);
      if (token) config.headers.Authorization = `Bearer ${token}`;
    } catch { /* ignorar */ }
  }
  return config;
});

// Interceptor: si el servidor devuelve 401 (No autorizado), limpia la sesión y redirige
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('hk_user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
