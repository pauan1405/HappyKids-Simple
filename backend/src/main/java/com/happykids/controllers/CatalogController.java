package com.happykids.controllers;

import com.happykids.entities.*;
import com.happykids.repositories.*;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")

public class CatalogController {

    private final CategoriaRepository categoriaRepo;
    private final MetodoPagoRepository metodoPagoRepo;
    private final ClienteRepository clienteRepo;
    private final UsuarioRepository usuarioRepo;

    /** GET /api/categorias */
    
    public CatalogController(CategoriaRepository categoriaRepo, MetodoPagoRepository metodoPagoRepo, ClienteRepository clienteRepo, UsuarioRepository usuarioRepo) {
        this.categoriaRepo = categoriaRepo;
        this.metodoPagoRepo = metodoPagoRepo;
        this.clienteRepo = clienteRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping(value = "/categorias")
    public List<Categoria> categorias() {
        return categoriaRepo.findAll();
    }

    /** GET /api/metodos-pago */
    @GetMapping("/metodos-pago")
    public List<MetodoPago> metodosPago() {
        return metodoPagoRepo.findAll();
    }

    /** GET /api/clientes — solo ADMIN */
    @GetMapping("/clientes")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public List<Cliente> clientes() {
        return clienteRepo.findAll();
    }

    /** GET /api/admin/usuarios */
    @GetMapping("/admin/usuarios")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> usuarios() {
        return usuarioRepo.findAll();
    }

    public CategoriaRepository getCategoriaRepo() {
        return categoriaRepo;
    }

    public MetodoPagoRepository getMetodoPagoRepo() {
        return metodoPagoRepo;
    }

    public ClienteRepository getClienteRepo() {
        return clienteRepo;
    }

    public UsuarioRepository getUsuarioRepo() {
        return usuarioRepo;
    }
    
    
}
