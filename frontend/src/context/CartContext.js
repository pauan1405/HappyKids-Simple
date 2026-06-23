import React, { createContext, useContext, useState } from 'react';

const CartContext = createContext(null);

export function CartProvider({ children }) {
  const [items, setItems] = useState([]); // [{ producto, cantidad }]

  const agregar = (producto) => {
    setItems(prev => {
      const existe = prev.find(i => i.producto.id === producto.id);
      if (existe) {
        return prev.map(i =>
          i.producto.id === producto.id
            ? { ...i, cantidad: i.cantidad + 1 }
            : i
        );
      }
      return [...prev, { producto, cantidad: 1 }];
    });
  };

  const quitar = (productoId) => {
    setItems(prev => prev.filter(i => i.producto.id !== productoId));
  };

  const cambiarCantidad = (productoId, cantidad) => {
    if (cantidad < 1) { quitar(productoId); return; }
    setItems(prev =>
      prev.map(i => i.producto.id === productoId ? { ...i, cantidad } : i)
    );
  };

  const vaciar = () => setItems([]);

  const total = items.reduce((sum, i) => sum + i.producto.precioVenta * i.cantidad, 0);
  const totalItems = items.reduce((sum, i) => sum + i.cantidad, 0);

  return (
    <CartContext.Provider value={{ items, agregar, quitar, cambiarCantidad, vaciar, total, totalItems }}>
      {children}
    </CartContext.Provider>
  );
}

export const useCart = () => useContext(CartContext);
