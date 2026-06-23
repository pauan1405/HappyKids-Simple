import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useToast } from '../context/ToastContext';
import authService from '../services/authService';

export default function EditarPerfil() {
  const { user, login } = useAuth();
  const { show } = useToast();
  const navigate = useNavigate();

  const [form, setForm] = useState({ nombre: '', telefono: '', password: '' });
  const [correo, setCorreo] = useState('');
  const [guardando, setGuardando] = useState(false);
  const [cargando, setCargando] = useState(true);

  // Cargar datos actuales al montar el componente
  useEffect(() => {
    authService.getPerfil()
      .then(data => {
        setForm({ nombre: data.nombre, telefono: String(data.telefono), password: '' });
        setCorreo(data.correo);
      })
      .catch(() => show('❌ No se pudo cargar el perfil', 'error'))
      .finally(() => setCargando(false));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setGuardando(true);
    try {
      const payload = {
        nombre: form.nombre,
        telefono: Number(form.telefono),
      };
      // Solo incluir contraseña si el usuario ingresó una nueva
      if (form.password.trim()) {
        payload.password = form.password.trim();
      }

      const updated = await authService.updatePerfil(payload);

      // Actualizar el nombre en el contexto / localStorage
      login({ ...user, nombre: updated.nombre });

      show('✅ Perfil actualizado correctamente');
      navigate('/dashboard');
    } catch (err) {
      show('❌ ' + (err.response?.data?.error || 'Error al actualizar el perfil'), 'error');
    } finally {
      setGuardando(false);
    }
  };

  if (cargando) {
    return (
      <section className="auth-page">
        <div className="container" style={{ textAlign: 'center', paddingTop: '60px' }}>
          <p>Cargando perfil...</p>
        </div>
      </section>
    );
  }

  return (
    <section className="auth-page">
      <div className="container">
        <div style={{ background: 'white', borderRadius: '20px', padding: '40px', maxWidth: '520px', margin: '0 auto', boxShadow: 'var(--shadow-md)' }}>
          <h2 style={{ fontFamily: 'var(--font-brand)', color: 'var(--pink-dark)', marginBottom: '24px' }}>
            ✏️ Editar Perfil
          </h2>
          <form onSubmit={handleSubmit}>

            <div className="form-group">
              <label>Correo electrónico</label>
              <input type="email" value={correo} disabled
                style={{ background: 'var(--gray-100)', cursor: 'not-allowed' }} />
            </div>

            <div className="form-group">
              <label>Nombre completo</label>
              <input type="text" required value={form.nombre}
                onChange={e => setForm({ ...form, nombre: e.target.value })} />
            </div>

            <div className="form-group">
              <label>Teléfono</label>
              <input type="tel" required value={form.telefono}
                onChange={e => setForm({ ...form, telefono: e.target.value })} />
            </div>

            <div className="form-group">
              <label>Nueva contraseña <span style={{ fontWeight: 400, color: 'var(--gray-500)', fontSize: '0.85em' }}>(dejar en blanco para no cambiarla)</span></label>
              <input type="password" placeholder="Mínimo 6 caracteres" value={form.password}
                onChange={e => setForm({ ...form, password: e.target.value })} />
            </div>

            <div style={{ display: 'flex', gap: '12px', marginTop: '8px' }}>
              <button type="submit" className="btn-login" disabled={guardando}>
                {guardando ? 'Guardando...' : 'Guardar cambios'}
              </button>
              <button type="button" className="btn-nav-logout" onClick={() => navigate('/dashboard')}>
                Cancelar
              </button>
            </div>

          </form>
        </div>
      </div>
    </section>
  );
}
