import api from './api';

const authService = {
  login: async (email, password) => {
    const { data } = await api.post('/auth/login', { email, password });
    return data; // { token, correo, rol, nombre, clienteId, usuarioId }
  },

  register: async (email, nombre, password, telefono) => {
    const { data } = await api.post('/auth/register', { email, nombre, password, telefono });
    return data;
  },

  getPerfil: async () => {
    const { data } = await api.get('/clientes/perfil');
    return data; // { nombre, telefono, correo }
  },

  updatePerfil: async (perfil) => {
    const { data } = await api.put('/clientes/perfil', perfil);
    return data; // { nombre, telefono, correo }
  },
};

export default authService;
