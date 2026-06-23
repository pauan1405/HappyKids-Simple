import React from 'react';
import { Link } from 'react-router-dom';

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer-inner">
        <div className="footer-cols">
          <nav className="footer-nav">
            <Link to="/">INICIO</Link>
            <Link to="/catalogo">CATÁLOGO DE PRODUCTO</Link>
            <Link to="/nosotros">SOBRE NOSOTROS</Link>
            <Link to="/faq">PREGUNTAS FRECUENTES</Link>
          </nav>
          <div className="footer-social">
            <a href="#" className="social-btn">📞</a>
            <a href="#" className="social-btn">📘</a>
            <a href="#" className="social-btn">📸</a>
          </div>
        </div>
        <p className="footer-copy">
          © 2026 Happy Kids. Desarrollado por EventStock | Todos los derechos reservados
        </p>
      </div>
    </footer>
  );
}
