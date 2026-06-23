import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import ventaService from '../services/ventaService';

export default function Pedidos() {
  const { user } = useAuth();
  const [pedidos, setPedidos] = useState([]);
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    if (!user?.clienteId) { setCargando(false); return; }
    ventaService.historialCliente(user.clienteId)
      .then(setPedidos)
      .catch(() => {})
      .finally(() => setCargando(false));
  }, [user]);

  return (
    <section className="nosotros-page">
      <div className="container">
        <h2>🛒 Mis Pedidos</h2>
        {cargando ? <p>Cargando...</p> : pedidos.length === 0 ? (
          <p style={{color:'var(--gray-600)'}}>Aún no tienes pedidos realizados.</p>
        ) : (
          <div style={{display:'flex',flexDirection:'column',gap:'14px',marginTop:'20px'}}>
            {pedidos.map(p => (
              <div key={p.id} className="carrito-item">
                <div className="carrito-item-info">
                  <h4>Pedido #{p.id} — {p.fecha}</h4>
                  <p className="carrito-item-meta">💳 {p.metodoPago} · Cliente: {p.clienteNombre}</p>
                  <p className="carrito-item-meta">📍 {p.direccionEnvio}</p>
                  <p style={{fontSize:'0.8rem',color:'var(--gray-400)'}}>
                    Productos: {p.productos?.join(', ')}
                  </p>
                </div>
                <div className="carrito-item-precio">${p.total?.toLocaleString('es-CO')}</div>
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
