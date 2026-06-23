import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useToast } from '../context/ToastContext';
import authService from '../services/authService';

export default function Login() {
  const [loginData, setLoginData]     = useState({ email: '', password: '' });
  const [regData, setRegData]         = useState({ email: '', nombre: '', password: '', confirm: '', telefono: '' });
  const [loadingLogin, setLoadingLogin] = useState(false);
  const [loadingReg, setLoadingReg]     = useState(false);
  const { login } = useAuth();
  const { show } = useToast();
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoadingLogin(true);
    try {
      const data = await authService.login(loginData.email, loginData.password);
      login(data);
      show(`✅ ¡Bienvenido ${data.nombre}!`);
      navigate(data.rol === 'ADMIN' ? '/admin' : '/dashboard');
    } catch {
      show('❌ Correo o contraseña incorrectos', 'error');
    } finally {
      setLoadingLogin(false);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    if (regData.password !== regData.confirm) { show('Las contraseñas no coinciden', 'error'); return; }
    if (regData.password.length < 6)          { show('La contraseña debe tener al menos 6 caracteres', 'error'); return; }
    setLoadingReg(true);
    try {
      const data = await authService.register(regData.email, regData.nombre, regData.password, Number(regData.telefono));
      login(data);
      show('✅ ¡Registro exitoso!');
      navigate('/dashboard');
    } catch (err) {
      show('❌ ' + (err.response?.data?.error || 'Error al registrar'), 'error');
    } finally {
      setLoadingReg(false);
    }
  };

  return (
    <section className="auth-page">
      <div className="container">
        <div className="auth-grid">

          {/* LOGIN */}
          <div className="auth-col left">
            <h2>Iniciar Sesión</h2>
            <form onSubmit={handleLogin}>
              <div className="form-group">
                <label>Email</label>
                <input type="email" placeholder="correo@ejemplo.com" required
                  value={loginData.email} onChange={e => setLoginData({...loginData, email: e.target.value})} />
              </div>
              <div className="form-group">
                <label>Contraseña</label>
                <input type="password" placeholder="Tu contraseña" required
                  value={loginData.password} onChange={e => setLoginData({...loginData, password: e.target.value})} />
              </div>
              <button type="submit" className="btn-login" disabled={loadingLogin}>
                {loadingLogin ? 'Ingresando...' : 'Log In'}
              </button>
            </form>
          </div>

          {/* REGISTRO */}
          <div className="auth-col right">
            <h2>Crear Cuenta</h2>
            <form onSubmit={handleRegister}>
              <div className="form-group">
                <label>Email</label>
                <input type="email" placeholder="correo@ejemplo.com" required
                  value={regData.email} onChange={e => setRegData({...regData, email: e.target.value})} />
              </div>
              <div className="form-group">
                <label>Nombre completo</label>
                <input type="text" placeholder="Tu nombre" required
                  value={regData.nombre} onChange={e => setRegData({...regData, nombre: e.target.value})} />
              </div>
              <div className="form-group">
                <label>Contraseña</label>
                <input type="password" placeholder="Mínimo 6 caracteres" required
                  value={regData.password} onChange={e => setRegData({...regData, password: e.target.value})} />
              </div>
              <div className="form-group">
                <label>Verificar Contraseña</label>
                <input type="password" placeholder="Repite tu contraseña" required
                  value={regData.confirm} onChange={e => setRegData({...regData, confirm: e.target.value})} />
              </div>
              <div className="form-group">
                <label>Teléfono</label>
                <input type="tel" placeholder="Número de contacto" required
                  value={regData.telefono} onChange={e => setRegData({...regData, telefono: e.target.value})} />
              </div>
              <button type="submit" className="btn-registro" disabled={loadingReg}>
                {loadingReg ? 'Registrando...' : 'Registrar'}
              </button>
            </form>
          </div>

        </div>
      </div>
    </section>
  );
}
