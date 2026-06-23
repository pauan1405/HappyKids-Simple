import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import productService from '../../services/productService';
import { useToast } from '../../context/ToastContext';

export default function ProductosList() {
  const [productos, setProductos] = useState([]);
  const [cargando, setCargando]   = useState(true);
  const { show } = useToast();

  useEffect(() => {
    productService.listar().then(setProductos).catch(() => show('Error al cargar', 'error')).finally(() => setCargando(false));
  }, []);

  const handleEliminar = async (id) => {
    if (!window.confirm('¿Eliminar este producto?')) return;
    try {
      await productService.eliminar(id);
      setProductos(prev => prev.filter(p => p.id !== id));
      show('Producto eliminado');
    } catch { show('Error al eliminar', 'error'); }
  };

  return (
    <section className="catalogo">
      <div className="container">
        <div style={{display:'flex',justifyContent:'space-between',alignItems:'center',marginBottom:'24px'}}>
          <h2>📦 Gestión de Productos</h2>
          <Link to="/admin/productos/nuevo">
            <button className="btn-comprar" style={{width:'auto',padding:'10px 24px'}}>+ Nuevo Producto</button>
          </Link>
        </div>
        {cargando ? <p>Cargando...</p> : (
          <div className="productos-grid">
            {productos.map(p => (
              <div key={p.id} className="producto-card">
                <div className="producto-icono">🎈</div>
                <h3>{p.nombre}</h3>
                <p className="descripcion">{p.descripcion}</p>
                <div className="precio">${p.precioVenta?.toLocaleString('es-CO')}</div>
                <p className="stock">📦 Stock: {p.stock}</p>
                <div style={{display:'flex',gap:'8px',marginTop:'8px'}}>
                  <Link to={`/admin/productos/editar/${p.id}`} style={{flex:1}}>
                    <button className="btn-comprar" style={{background:'var(--sky-dark)'}}>✏️ Editar</button>
                  </Link>
                  <button className="btn-eliminar" onClick={() => handleEliminar(p.id)}
                    style={{background:'#FFEBEE',color:'#e53935',border:'none',padding:'8px',borderRadius:'8px',cursor:'pointer'}}>
                    🗑️
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  );
}
