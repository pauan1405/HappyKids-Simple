package com.happykids.controllers;

import com.happykids.dto.VentaDTOs.*;
import com.happykids.repositories.UsuarioRepository;
import com.happykids.services.VentaService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")

public class VentaController {

    private final VentaService ventaService;
    private final UsuarioRepository usuarioRepo;

    public VentaController(VentaService ventaService, UsuarioRepository usuarioRepo) {
        this.ventaService = ventaService;
        this.usuarioRepo = usuarioRepo;
    }

    
    /** POST /api/ventas — cliente autenticado */
    @PostMapping("/ventas")
    public ResponseEntity<?> crearVenta(@Valid @RequestBody VentaRequest req) {
        try {
            return ResponseEntity.ok(ventaService.crearVenta(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** GET /api/ventas/cliente/{clienteId} */
    @GetMapping("/ventas/cliente/{clienteId}")
    public ResponseEntity<?> ventasPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(ventaService.ventasPorCliente(clienteId));
    }

    // ── ADMIN ──────────────────────────────────────────────────────

    /** GET /api/admin/stats */
    @GetMapping("/admin/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStatsResponse> stats() {
        AdminStatsResponse s = ventaService.obtenerStats();
        // Completar totalUsuarios
        long totalUsuarios = usuarioRepo.count();
        return ResponseEntity.ok(new AdminStatsResponse(
                s.getTotalProductos(), s.getTotalClientes(),
                totalUsuarios, s.getVentasMesActual(), s.getTotalIngresos()
        ));
    }

    /** GET /api/admin/ventas */
    @GetMapping("/admin/ventas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> todasVentas() {
        return ResponseEntity.ok(ventaService.todasLasVentas());
    }

    /** POST /api/admin/ventas — venta manual desde admin */
    @PostMapping("/admin/ventas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ventaManual(@Valid @RequestBody VentaRequest req) {
        try {
            return ResponseEntity.ok(ventaService.crearVenta(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public VentaService getVentaService() {
        return ventaService;
    }

    public UsuarioRepository getUsuarioRepo() {
        return usuarioRepo;
    }
    
}
