import api from './api';

const productService = {
  listar: async (nombre = '', categoria = '') => {
    const { data } = await api.get('/productos', { params: { nombre, categoria } });
    return data;
  },

  obtener: async (id) => {
    const { data } = await api.get(`/productos/${id}`);
    return data;
  },

  crear: async (producto) => {
    const { data } = await api.post('/productos', producto);
    return data;
  },

  actualizar: async (id, producto) => {
    const { data } = await api.put(`/productos/${id}`, producto);
    return data;
  },

  eliminar: async (id) => {
    const { data } = await api.delete(`/productos/${id}`);
    return data;
  },

  categorias: async () => {
    const { data } = await api.get('/categorias');
    return data;
  },
};

export default productService;
