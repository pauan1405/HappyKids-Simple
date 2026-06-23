import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
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

// Interceptor: si el servidor devuelve 401, limpia la sesión
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
