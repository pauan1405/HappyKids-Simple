# 🎈 HappyKids-Simple

**Piñatería y Confitería** — Sistema web full-stack con React + Spring Boot + PostgreSQL.

---

## 📁 Estructura del proyecto

```
HappyKids-Simple/
├── frontend/                        ← React (puerto 3000)
│   ├── public/
│   │   ├── css/
│   │   │   ├── images/              ← imágenes del sitio
│   │   │   └── styles.css
│   │   └── producto-default.jpg
│   ├── src/
│   │   ├── components/
│   │   │   └── shared/
│   │   │       ├── Navbar.js
│   │   │       ├── Footer.js
│   │   │       ├── Toast.js
│   │   │       └── PrivateRoute.js
│   │   ├── context/
│   │   │   ├── AuthContext.js       ← sesión JWT
│   │   │   ├── CartContext.js       ← carrito
│   │   │   └── ToastContext.js      ← notificaciones
│   │   ├── pages/
│   │   │   ├── Home.js
│   │   │   ├── Catalogo.js
│   │   │   ├── Login.js
│   │   │   ├── Cart.js
│   │   │   ├── Dashboard.js
│   │   │   ├── Pedidos.js
│   │   │   ├── Nosotros.js
│   │   │   ├── Faq.js
│   │   │   └── admin/
│   │   │       ├── AdminPanel.js
│   │   │       ├── ProductosList.js
│   │   │       ├── ProductoForm.js
│   │   │       └── AdminVentas.js
│   │   ├── services/
│   │   │   ├── api.js               ← axios con JWT interceptor
│   │   │   ├── authService.js
│   │   │   ├── productService.js
│   │   │   └── ventaService.js
│   │   ├── App.js
│   │   ├── index.js
│   │   └── styles.css
│   └── package.json
│
├── backend/                         ← Spring Boot (puerto 8080)
│   ├── src/main/java/com/happykids/
│   │   ├── HappyKidsApplication.java
│   │   ├── controllers/
│   │   │   ├── AuthController.java
│   │   │   ├── CatalogController.java
│   │   │   ├── ProductoController.java
│   │   │   └── VentaController.java
│   │   ├── services/
│   │   │   ├── AuthService.java
│   │   │   ├── ProductoService.java
│   │   │   └── VentaService.java
│   │   ├── repositories/
│   │   │   ├── CategoriaRepository.java
│   │   │   ├── ClienteRepository.java
│   │   │   ├── DetalleVentaRepository.java
│   │   │   ├── MetodoPagoRepository.java
│   │   │   ├── ProductoRepository.java
│   │   │   ├── RolRepository.java
│   │   │   ├── StatusRepository.java
│   │   │   ├── UsuarioRepository.java
│   │   │   └── VentaRepository.java
│   │   ├── entities/
│   │   │   ├── Categoria.java
│   │   │   ├── Cliente.java
│   │   │   ├── DetalleCategoria.java
│   │   │   ├── DetalleProveedor.java
│   │   │   ├── DetalleVenta.java
│   │   │   ├── MetodoPago.java
│   │   │   ├── Producto.java
│   │   │   ├── Proveedor.java
│   │   │   ├── Rol.java
│   │   │   ├── Status.java
│   │   │   ├── Usuario.java
│   │   │   └── Venta.java
│   │   ├── security/
│   │   │   ├── JwtAuthFilter.java
│   │   │   ├── JwtUtils.java
│   │   │   ├── SecurityConfig.java
│   │   │   └── UserDetailsServiceImpl.java
│   │   └── dto/
│   │       ├── AuthDTOs.java
│   │       ├── ProductoDTOs.java
│   │       └── VentaDTOs.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
└── README.md
```

---

## 🚀 Cómo ejecutar

### Backend (Spring Boot)
```bash
cd backend
# Edita application.properties con tu contraseña de PostgreSQL
mvn spring-boot:run
# Corre en http://localhost:8080
```

### Frontend (React)
```bash
cd frontend
npm install
npm start
# Corre en http://localhost:3000
# El proxy ya está configurado hacia :8080 en package.json
```

---

## 🔐 Credenciales de prueba

| Rol    | Correo                     | Contraseña       |
|--------|----------------------------|------------------|
| ADMIN  | san.cvelandia@gmail.com    | (ver BD)         |
| CLIENTE| proyectm7@gmail.com        | (ver BD)         |

> Las contraseñas están encriptadas con BCrypt en la BD.
> Debes actualizarlas usando `BCryptPasswordEncoder` o creando nuevos usuarios desde el registro.

---

## ⚙️ Requisitos

- Java 17+
- Maven 3.8+
- Node.js 18+
- PostgreSQL con BD `happykids` creada y script SQL ejecutado
