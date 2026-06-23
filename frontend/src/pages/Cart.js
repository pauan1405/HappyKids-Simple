import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';
import { useToast } from '../context/ToastContext';
import ventaService from '../services/ventaService';

export default function Cart() {
  const { items, quitar, cambiarCantidad, vaciar, total } = useCart();
  const { user } = useAuth();
  const { show } = useToast();
  const navigate = useNavigate();
  const [metodos, setMetodos]       = useState([]);
  const [metodoPago, setMetodoPago] = useState('');
  const [direccionEnvio, setDireccionEnvio] = useState('');
  const [procesando, setProcesando] = useState(false);

  useEffect(() => {
    ventaService.metodosPago().then(data => {
      setMetodos(data);
      if (data.length) setMetodoPago(data[0].codMetodo);
    }).catch(() => {});
  }, []);

  const iva      = Math.round(total * 0.13);
  const totalFinal = total + iva;

  const handleFinalizar = async () => {
    if (!user?.clienteId) { show('Debes iniciar sesión para comprar', 'error'); navigate('/login'); return; }
    if (!direccionEnvio.trim()) { show('Debes ingresar una dirección de envío', 'error'); return; }
    setProcesando(true);
    try {
      const productosIds = items.flatMap(i => Array(i.cantidad).fill(i.producto.id));
      await ventaService.crear(user.clienteId, Number(metodoPago), productosIds, totalFinal, direccionEnvio.trim());
      show(`🎉 ¡Compra realizada! Total: $${totalFinal.toLocaleString('es-CO')}`);
      vaciar();
      navigate('/mis-pedidos');
    } catch (err) {
      show('❌ ' + (err.response?.data?.error || 'Error al procesar la compra'), 'error');
    } finally {
      setProcesando(false);
    }
  };

  if (!items.length) return (
    <section className="carrito-page">
      <div className="container">
        <h2>🛒 Carrito</h2>
        <div className="carrito-vacio">
          <div style={{fontSize:'4rem',marginBottom:'16px'}}>🛒</div>
          <h3>Tu carrito está vacío</h3>
          <p>¡Agrega productos para comenzar tu pedido!</p>
          <button className="btn-seguir-comprando" onClick={() => navigate('/catalogo')}>🛍️ Ver Productos</button>
        </div>
      </div>
    </section>
  );

  return (
    <section className="carrito-page">
      <div className="container">
        <h2>🛒 Carrito</h2>
        <div className="carrito-layout">
          <div className="carrito-items">
            {items.map(({ producto, cantidad }) => (
              <div key={producto.id} className="carrito-item">
                <div className="carrito-item-img">🎈</div>
                <div className="carrito-item-info">
                  <h4>{producto.nombre}</h4>
                  <p className="carrito-item-meta">${producto.precioVenta?.toLocaleString('es-CO')} / unidad</p>
                </div>
                <div className="carrito-item-actions">
                  <div className="carrito-item-precio">${(producto.precioVenta * cantidad).toLocaleString('es-CO')}</div>
                  <div className="qty-control">
                    <button className="qty-btn" onClick={() => cambiarCantidad(producto.id, cantidad - 1)}>−</button>
                    <span className="qty-num">{cantidad}</span>
                    <button className="qty-btn" onClick={() => cambiarCantidad(producto.id, cantidad + 1)}>+</button>
                  </div>
                  <button className="btn-eliminar" onClick={() => quitar(producto.id)}>🗑️</button>
                </div>
              </div>
            ))}
          </div>

          <div className="resumen-pedido">
            <h3>Resumen del pedido</h3>
            <div className="resumen-linea"><span>Subtotal</span><span>${total.toLocaleString('es-CO')}</span></div>
            <div className="resumen-linea"><span>IVA (13%)</span><span>${iva.toLocaleString('es-CO')}</span></div>
            <div className="resumen-linea total"><span>Total</span><span>${totalFinal.toLocaleString('es-CO')}</span></div>
            <div className="metodo-pago-resumen">
              <label>💳 Método de pago</label>
              <select value={metodoPago} onChange={e => setMetodoPago(e.target.value)}>
                {metodos.map(m => <option key={m.codMetodo} value={m.codMetodo}>{m.dmetodo}</option>)}
              </select>
            </div>
            <div className="metodo-pago-resumen">
              <label>📍 Dirección de envío</label>
              <input
                type="text"
                placeholder="Ej: Calle 5 #10-20, Barrio Centro"
                value={direccionEnvio}
                onChange={e => setDireccionEnvio(e.target.value)}
                maxLength={150}
              />
            </div>
            <button className="btn-finalizar" onClick={handleFinalizar} disabled={procesando}>
              {procesando ? 'Procesando...' : 'Finalizar compra ✅'}
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}
