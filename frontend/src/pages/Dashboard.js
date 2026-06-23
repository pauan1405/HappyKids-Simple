import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Dashboard() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <section className="dashboard-page">
      <div className="container">
        <div className="dashboard-header">
          <h2>👤 ¡Hola, {user?.nombre || user?.correo}!</h2>
          <p>Bienvenido a tu panel de Happy Kids · {user?.rol === 'ADMIN' ? '🔑 Administrador' : '🛍️ Cliente'}</p>
        </div>
        <div className="dashboard-cards">
          <div className="dash-card" onClick={() => navigate('/mis-pedidos')}>
            <div className="dash-icon">🛒</div>
            <h3>Mis Pedidos</h3>
            <p>Historial de tus compras</p>
            <button>Ver pedidos</button>
          </div>
          <div className="dash-card" onClick={() => navigate('/mi-perfil')}>
            <div className="dash-icon">👤</div>
            <h3>Mi Perfil</h3>
            <p>Actualiza tus datos</p>
            <button>Editar perfil</button>
          </div>
          <div className="dash-card" onClick={() => navigate('/catalogo')}>
            <div className="dash-icon">🎈</div>
            <h3>Mis Favoritos</h3>
            <p>Productos que te gustan</p>
            <button>Ver favoritos</button>
          </div>
        </div>
      </div>
    </section>
  );
}
