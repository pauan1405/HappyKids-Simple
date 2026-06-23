import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import productService from '../services/productService';
import { useCart } from '../context/CartContext';
import { useToast } from '../context/ToastContext';

export default function Home() {
  const [destacados, setDestacados] = useState([]);
  const { agregar } = useCart();
  const { show } = useToast();
  const navigate = useNavigate();

  useEffect(() => {
    productService.listar().then(data => setDestacados(data.slice(0, 4))).catch(() => {});
  }, []);

  const handleAgregar = (p) => {
    agregar(p);
    show(`✅ ${p.nombre} agregado al carrito`);
  };

  return (
    <>
      {/* HERO */}
      <section className="hero">
        <div className="hero-content">
          <p className="hero-eyebrow">✨ Bienvenido a</p>
          <h1>Happy <span>Kids</span></h1>
          <p className="hero-subtitle">Piñatería y Confitería — Todo para que tu celebración sea inolvidable</p>
          <button className="btn-hero" onClick={() => navigate('/catalogo')}>🎉 VER PRODUCTOS</button>
        </div>
      </section>

      {/* SOBRE NOSOTROS */}
      <section className="sobre-nosotros-home">
        <div className="container">
          <div className="sobre-texto">
            <h2>Sobre Nosotros</h2>
            <p>En Happy Kids, no solo organizamos eventos; capturamos la esencia de tus momentos más especiales.
              Con más de 15 años de experiencia en el sector, nos hemos consolidado como una opción confiable y creativa.</p>
            <Link to="/nosotros" className="btn-ver-mas">VER MÁS →</Link>
          </div>
          <div className="sobre-imagen">
  <img src="/css/images/sobrenosotros.jpeg" alt="Sobre nosotros" />
</div>
        </div>
      </section>

      {/* POR QUÉ ELEGIRNOS */}
      <section className="porque-elegirnos">
        <div className="container">
          <h2 className="section-title">¿Por qué Elegirnos?</h2>
          <div className="beneficios-grid">
            <div className="beneficio-card"><div className="beneficio-icono">🌟</div><h3>Variedad</h3><p>Más de 500 productos para tu evento perfecto</p></div>
            <div className="beneficio-card"><div className="beneficio-icono">🎯</div><h3>Experiencia Personalizada</h3><p>15+ años creando celebraciones únicas</p></div>
            <div className="beneficio-card"><div className="beneficio-icono">🏆</div><h3>Calidad</h3><p>Productos premium de las mejores marcas</p></div>
          </div>
        </div>
      </section>

      {/* CATEGORÍAS */}
      <section className="categorias-section">
        <div className="container">
          <h2 className="section-title">Categorías</h2>
          <div className="categorias-grid">
            {[
              { label: 'PELUCHES',            icon: '🧸', cat: 'cat-peluches' },
              { label: 'PIÑATAS',             icon: '🎭', cat: 'cat-pinatas' },
              { label: 'ARREGLOS',            icon: '💐', cat: 'cat-arreglos' },
              { label: 'CONFITERÍA',          icon: '🍬', cat: 'cat-confiteria' },
              { label: 'GLOBOS',              icon: '🎈', cat: 'cat-globos' },
              { label: 'ARTÍCULOS DECORATIVOS', icon: '🎊', cat: 'cat-decoracion' },
            ].map(c => (
              <div key={c.label} className={`categoria-card ${c.cat}`}
                   onClick={() => navigate(`/catalogo?categoria=${c.label}`)}>
                <div className="categoria-img">{c.icon}</div>
                <div className="categoria-label">{c.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* PRODUCTOS DESTACADOS */}
      <section className="productos-destacados">
        <div className="container">
          <h2 className="section-title">Productos Más Vendidos</h2>
          <div className="cta-login-banner">
            <Link to="/login">INICIA SESIÓN</Link> O <Link to="/login">CREA UNA CUENTA</Link> PARA REALIZAR TUS PEDIDOS
          </div>
          <div className="productos-grid">
            {destacados.map(p => (
              <div key={p.id} className="producto-card">
                {p.imagenUrl ? (
  <img src={p.imagenUrl} alt={p.nombre} className="producto-img" />
) : (
  <div className="producto-icono">🎁</div>
)} 
                <h3>{p.nombre}</h3>
                <p className="descripcion">{p.descripcion}</p>
                <div className="precio">${p.precioVenta?.toLocaleString('es-CO')}</div>
                <p className="stock">📦 Stock: {p.stock} unidades</p>
                <button className="btn-comprar" onClick={() => handleAgregar(p)}>🛒 Agregar al Carrito</button>
              </div>
            ))}
          </div>
        </div>
      </section>
    </>
  );
}
