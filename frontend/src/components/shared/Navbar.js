import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useCart } from '../../context/CartContext';

export default function Navbar() {
  const { user, logout, isAdmin } = useAuth();
  const { totalItems } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="header">
      <div className="header-inner">
        <Link to="/" className="logo">
          <span className="logo-icon">🎈</span>
          <span className="logo-text">Happy Kids</span>
        </Link>

        <nav className="nav">
          <Link to="/"         className="nav-link">INICIO</Link>
          <Link to="/catalogo" className="nav-link">PRODUCTOS</Link>
          <Link to="/nosotros" className="nav-link">SOBRE NOSOTROS</Link>
          <Link to="/faq"      className="nav-link">PREGUNTAS FRECUENTES</Link>
          {isAdmin() && <Link to="/admin" className="nav-link">ADMIN</Link>}
        </nav>

        <div className="header-actions">
          {user ? (
            <>
              <Link to="/dashboard" className="btn-nav-login">MI CUENTA</Link>
              <button onClick={handleLogout} className="btn-nav-logout">SALIR</button>
            </>
          ) : (
            <Link to="/login" className="btn-nav-login">INICIAR SESIÓN</Link>
          )}
          <Link to="/carrito" className="btn-carrito">
            🛒 <span className="carrito-badge">{totalItems}</span>
          </Link>
        </div>
      </div>
    </header>
  );
}
