package com.happykids.controllers;

import com.happykids.dto.AuthDTOs.*;
import com.happykids.services.AuthService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final AuthService authService;

    public ClienteController(AuthService authService) {
        this.authService = authService;
    }

    /** GET /api/clientes/perfil — devuelve los datos del usuario autenticado */
    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            PerfilResponse perfil = authService.getPerfil(userDetails.getUsername());
            return ResponseEntity.ok(perfil);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "Perfil no encontrado"));
        }
    }

    /** PUT /api/clientes/perfil — actualiza nombre, teléfono y, opcionalmente, contraseña */
    @PutMapping("/perfil")
    public ResponseEntity<?> updatePerfil(@AuthenticationPrincipal UserDetails userDetails,
                                          @Valid @RequestBody UpdatePerfilRequest req) {
        try {
            PerfilResponse perfil = authService.updatePerfil(userDetails.getUsername(), req);
            return ResponseEntity.ok(perfil);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al actualizar el perfil"));
        }
    }
}
