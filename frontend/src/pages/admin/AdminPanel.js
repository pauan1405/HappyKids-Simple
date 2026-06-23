import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ventaService from '../../services/ventaService';

export default function AdminPanel() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    ventaService.stats().then(setStats).catch(() => {});
  }, []);

  return (
    <section className="dashboard-page">
      <div className="container">
        <div className="dashboard-header">
          <h2>🔑 Panel de Administración</h2>
          <p>Happy Kids — Control general del sistema</p>
        </div>

        {stats && (
          <div className="dashboard-cards" style={{marginBottom:'28px'}}>
            <div className="dash-card"><div className="dash-icon">📦</div><h3>{stats.totalProductos}</h3><p>Productos</p></div>
            <div className="dash-card"><div className="dash-icon">👥</div><h3>{stats.totalClientes}</h3><p>Clientes</p></div>
            <div className="dash-card"><div className="dash-icon">🛒</div><h3>{stats.ventasMesActual}</h3><p>Ventas este mes</p></div>
            <div className="dash-card"><div className="dash-icon">💰</div><h3>${stats.totalIngresos?.toLocaleString('es-CO')}</h3><p>Ingresos totales</p></div>
          </div>
        )}

        <div className="dashboard-cards">
          <div className="dash-card"><div className="dash-icon">📦</div><h3>Productos</h3><p>Gestionar catálogo</p>
            <Link to="/admin/productos"><button>Ir a Productos</button></Link></div>
          <div className="dash-card"><div className="dash-icon">🧾</div><h3>Ventas</h3><p>Historial de ventas</p>
            <Link to="/admin/ventas"><button>Ir a Ventas</button></Link></div>
        </div>
      </div>
    </section>
  );
}
