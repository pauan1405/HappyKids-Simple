import React, { useEffect, useState } from 'react';
import ventaService from '../../services/ventaService';

export default function AdminVentas() {
  const [ventas, setVentas]     = useState([]);
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    ventaService.todas().then(setVentas).catch(() => {}).finally(() => setCargando(false));
  }, []);

  return (
    <section className="catalogo">
      <div className="container">
        <h2>🧾 Historial de Ventas</h2>
        {cargando ? <p>Cargando...</p> : ventas.length === 0 ? (
          <p style={{color:'var(--gray-600)',marginTop:'20px'}}>No hay ventas registradas aún.</p>
        ) : (
          <div style={{display:'flex',flexDirection:'column',gap:'14px',marginTop:'20px'}}>
            {ventas.map(v => (
              <div key={v.id} className="carrito-item">
                <div className="carrito-item-info">
                  <h4>Venta #{v.id} — {v.fecha}</h4>
                  <p className="carrito-item-meta">👤 {v.clienteNombre} · 💳 {v.metodoPago}</p>
                  <p className="carrito-item-meta">📍 {v.direccionEnvio}</p>
                  <p style={{fontSize:'0.78rem',color:'var(--gray-400)'}}>
                    Productos: {v.productos?.join(', ')}
                  </p>
                </div>
                <div className="carrito-item-precio">${v.total?.toLocaleString('es-CO')}</div>
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
