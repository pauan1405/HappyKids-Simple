import api from './api';

const ventaService = {
  crear: async (clienteId, metodoPagoId, productosIds, total, direccionEnvio) => {
    const { data } = await api.post('/ventas', { clienteId, metodoPagoId, productosIds, total, direccionEnvio });
    return data;
  },

  historialCliente: async (clienteId) => {
    const { data } = await api.get(`/ventas/cliente/${clienteId}`);
    return data;
  },

  // Admin
  todas: async () => {
    const { data } = await api.get('/admin/ventas');
    return data;
  },

  stats: async () => {
    const { data } = await api.get('/admin/stats');
    return data;
  },

  metodosPago: async () => {
    const { data } = await api.get('/metodos-pago');
    return data;
  },
};

export default ventaService;
