package com.happykids.controllers;

import com.happykids.dto.ProductoDTOs.*;
import com.happykids.services.ProductoService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")

public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    

    /** GET /api/productos?nombre=&categoria= */
    @GetMapping
    public List<ProductoResponse> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria) {
        return productoService.buscar(nombre, categoria);
    }

    /** GET /api/productos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(productoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    /** POST /api/productos — solo ADMIN */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest req) {
        return ResponseEntity.ok(productoService.crear(req));
    }

    /** PUT /api/productos/{id} — solo ADMIN */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                         @Valid @RequestBody ProductoRequest req) {
        try {
            return ResponseEntity.ok(productoService.actualizar(id, req));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    /** DELETE /api/productos/{id} — solo ADMIN (soft delete) */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok(Map.of("message", "Producto eliminado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    public ProductoService getProductoService() {
        return productoService;
    }
    
}
