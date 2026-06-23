import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import productService from '../services/productService';
import { useCart } from '../context/CartContext';
import { useToast } from '../context/ToastContext';

export default function Catalogo() {
  const [productos, setProductos] = useState([]);
  const [busqueda, setBusqueda] = useState('');
  const [categoria, setCategoria] = useState('');
  const [cargando, setCargando] = useState(true);
  const [searchParams] = useSearchParams();
  const { agregar } = useCart();
  const { show } = useToast();

  useEffect(() => {
    const cat = searchParams.get('categoria') || '';
    setCategoria(cat);
    cargar('', cat);
  }, []);

  const cargar = (nombre = '', cat = '') => {
    setCargando(true);
    productService.listar(nombre, cat)
      .then(setProductos)
      .catch(() => show('❌ Error al cargar productos', 'error'))
      .finally(() => setCargando(false));
  };

  const handleBuscar = (e) => {
    const val = e.target.value;
    setBusqueda(val);
    cargar(val, categoria);
  };

  const handleCategoria = (e) => {
    const val = e.target.value;
    setCategoria(val);
    cargar(busqueda, val);
  };

  const handleAgregar = (p) => {
    agregar(p);
    show(`✅ ${p.nombre} agregado al carrito`);
  };

  return (
    <section className="catalogo">
      <div className="container">
        <h2>Catálogo de Productos</h2>
        <div className="filtros">
          <input type="text" placeholder="🔍 Buscar productos..." value={busqueda} onChange={handleBuscar} />
          <select value={categoria} onChange={handleCategoria}>
            <option value="">Todas las categorías</option>
            <option value="PELUCHES">Peluches</option>
            <option value="CONFITERIA">Confitería</option>
            <option value="INFLABLES">Globos / Inflables</option>
            <option value="DECORACION">Decoración</option>
            <option value="DESECHABLES">Desechables</option>
            <option value="EMPAQUES">Empaques</option>
            <option value="BISUTERIA">Bisutería</option>
          </select>
        </div>

        {cargando ? (
          <div className="cargando">⏳ Cargando productos...</div>
        ) : productos.length === 0 ? (
          <div className="sin-productos">📭 No hay productos disponibles</div>
        ) : (
          <div className="productos-grid">
            {productos.map(p => (
              <div key={p.id} className="producto-card">
                <div className="producto-icono">
                  {p.imagenUrl
                    ? <img src={p.imagenUrl} alt={p.nombre} style={{width:'100%',height:'100%',objectFit:'cover',borderRadius:'12px'}} />
                    : '🎈'}
                </div>
                <h3>{p.nombre}</h3>
                <p className="descripcion">{p.descripcion}</p>
                <div className="precio">${p.precioVenta?.toLocaleString('es-CO')}</div>
                <p className="stock">📦 Stock: {p.stock} unidades</p>
                <button className="btn-comprar" onClick={() => handleAgregar(p)}>🛒 Agregar al Carrito</button>
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
