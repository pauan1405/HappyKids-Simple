import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import productService from '../../services/productService';
import { useToast } from '../../context/ToastContext';

export default function ProductoForm() {
  const { id } = useParams();
  const esEdicion = !!id;
  const navigate  = useNavigate();
  const { show }  = useToast();
  const [form, setForm] = useState({
    nombre: '', descripcion: '', precioCompra: '', precioVenta: '', stock: '', serial: '', imagenUrl: '', categoriaId: ''
  });
  const [categorias, setCategorias] = useState([]);
  const [guardando, setGuardando]   = useState(false);

  useEffect(() => {
    productService.categorias().then(setCategorias).catch(() => {});
    if (esEdicion) {
      productService.obtener(id).then(p => setForm({
        nombre: p.nombre, descripcion: p.descripcion,
        precioCompra: '', precioVenta: p.precioVenta,
        stock: p.stock, serial: p.serial || '', imagenUrl: p.imagenUrl || '', categoriaId: ''
      })).catch(() => {});
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setGuardando(true);
    try {
      const data = { ...form, precioCompra: Number(form.precioCompra), precioVenta: Number(form.precioVenta), stock: Number(form.stock), categoriaId: Number(form.categoriaId) || null };
      if (esEdicion) { await productService.actualizar(id, data); show('✅ Producto actualizado'); }
      else           { await productService.crear(data);          show('✅ Producto creado'); }
      navigate('/admin/productos');
    } catch (err) {
      show('❌ ' + (err.response?.data?.error || 'Error al guardar'), 'error');
    } finally { setGuardando(false); }
  };

  return (
    <section className="auth-page">
      <div className="container">
        <div style={{background:'white',borderRadius:'20px',padding:'40px',maxWidth:'600px',margin:'0 auto',boxShadow:'var(--shadow-md)'}}>
          <h2 style={{fontFamily:'var(--font-brand)',color:'var(--pink-dark)',marginBottom:'24px'}}>
            {esEdicion ? '✏️ Editar Producto' : '+ Nuevo Producto'}
          </h2>
          <form onSubmit={handleSubmit}>
            {[
              { label: 'Nombre',        key: 'nombre',       type: 'text',   req: true },
              { label: 'Descripción',   key: 'descripcion',  type: 'text',   req: true },
              { label: 'Precio Compra', key: 'precioCompra', type: 'number', req: !esEdicion },
              { label: 'Precio Venta',  key: 'precioVenta',  type: 'number', req: true },
              { label: 'Stock',         key: 'stock',        type: 'number', req: true },
              { label: 'Serial',        key: 'serial',       type: 'text',   req: false },
              { label: 'URL Imagen',    key: 'imagenUrl',    type: 'text',   req: false },
            ].map(f => (
              <div className="form-group" key={f.key}>
                <label>{f.label}</label>
                <input type={f.type} required={f.req} value={form[f.key]}
                  onChange={e => setForm({...form, [f.key]: e.target.value})} />
              </div>
            ))}
            <div className="form-group">
              <label>Categoría</label>
              <select value={form.categoriaId} onChange={e => setForm({...form, categoriaId: e.target.value})}
                style={{width:'100%',padding:'11px 14px',border:'2px solid var(--gray-200)',borderRadius:'10px',fontFamily:'var(--font-main)'}}>
                <option value="">Sin categoría</option>
                {categorias.map(c => <option key={c.codCategoria} value={c.codCategoria}>{c.nombre}</option>)}
              </select>
            </div>
            <div style={{display:'flex',gap:'12px',marginTop:'8px'}}>
              <button type="submit" className="btn-login" disabled={guardando}>
                {guardando ? 'Guardando...' : 'Guardar'}
              </button>
              <button type="button" className="btn-nav-logout" onClick={() => navigate('/admin/productos')}>
                Cancelar
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
}
