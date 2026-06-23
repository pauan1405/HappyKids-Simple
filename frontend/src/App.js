import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { PrivateRoute, AdminRoute } from './components/shared/PrivateRoute';

// Shared
import Navbar    from './components/shared/Navbar';
import Footer    from './components/shared/Footer';
import Toast     from './components/shared/Toast';

// Pages públicas
import Home      from './pages/Home';
import Catalogo  from './pages/Catalogo';
import Nosotros  from './pages/Nosotros';
import Faq       from './pages/Faq';
import Login     from './pages/Login';

// Pages privadas (clientes)
import Cart      from './pages/Cart';
import Dashboard from './pages/Dashboard';
import Pedidos   from './pages/Pedidos';
import EditarPerfil from './pages/EditarPerfil';

// Pages admin
import AdminPanel      from './pages/admin/AdminPanel';
import ProductosList   from './pages/admin/ProductosList';
import ProductoForm    from './pages/admin/ProductoForm';
import AdminVentas     from './pages/admin/AdminVentas';

import './styles.css';

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Toast />
      <main>
        <Routes>
          {/* Públicas */}
          <Route path="/"          element={<Home />} />
          <Route path="/catalogo"  element={<Catalogo />} />
          <Route path="/nosotros"  element={<Nosotros />} />
          <Route path="/faq"       element={<Faq />} />
          <Route path="/login"     element={<Login />} />

          {/* Privadas – cualquier usuario autenticado */}
          <Route path="/carrito"   element={<PrivateRoute><Cart /></PrivateRoute>} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
          <Route path="/mis-pedidos" element={<PrivateRoute><Pedidos /></PrivateRoute>} />
          <Route path="/mi-perfil" element={<PrivateRoute><EditarPerfil /></PrivateRoute>} />

          {/* Admin */}
          <Route path="/admin"                         element={<AdminRoute><AdminPanel /></AdminRoute>} />
          <Route path="/admin/productos"               element={<AdminRoute><ProductosList /></AdminRoute>} />
          <Route path="/admin/productos/nuevo"         element={<AdminRoute><ProductoForm /></AdminRoute>} />
          <Route path="/admin/productos/editar/:id"    element={<AdminRoute><ProductoForm /></AdminRoute>} />
          <Route path="/admin/ventas"                  element={<AdminRoute><AdminVentas /></AdminRoute>} />

          {/* Fallback */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
      <Footer />
    </BrowserRouter>
  );
}
