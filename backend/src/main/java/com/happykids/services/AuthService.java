package com.happykids.services;

import com.happykids.dto.AuthDTOs.*;
import com.happykids.entities.*;
import com.happykids.repositories.*;
import com.happykids.security.JwtUtils;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final ClienteRepository clienteRepo;
    private final StatusRepository statusRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public AuthService(UsuarioRepository usuarioRepo, ClienteRepository clienteRepo, StatusRepository statusRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authManager, UserDetailsService userDetailsService) {
        this.usuarioRepo = usuarioRepo;
        this.clienteRepo = clienteRepo;
        this.statusRepo = statusRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    
    public AuthResponse login(LoginRequest req) {
        // Autentica contra la BD (lanza excepción si falla)
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtUtils.generateToken(userDetails);

        Usuario usuario = usuarioRepo.findByCorreoUsuario(req.getEmail()).orElseThrow();
        String rol = usuario.getRol().getDrol();

        Integer clienteId = null;
        String nombre = "Administrador";

        if (rol.equals("CLIENTE")) {
            var cliente = clienteRepo.findByUsuarioAsociado_CodUsuario(usuario.getCodUsuario());
            if (cliente.isPresent()) {
                clienteId = cliente.get().getCodCliente();
                nombre = cliente.get().getNombreCliente();
            }
        }

        return new AuthResponse(token, usuario.getCorreoUsuario(), rol, nombre, clienteId, usuario.getCodUsuario());
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (usuarioRepo.existsByCorreoUsuario(req.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Status activo = statusRepo.findById(1).orElseThrow();
        Rol rolCliente = rolRepo.findById(1).orElseThrow();

        // 1. Crear usuario
        Usuario usuario = new Usuario();
        usuario.setCodUsuario(getNextUsuarioId());
        usuario.setCorreoUsuario(req.getEmail());
        usuario.setContrasenaUsuario(passwordEncoder.encode(req.getPassword()));
        usuario.setStatus(activo);
        usuario.setRol(rolCliente);
        usuarioRepo.save(usuario);

        // 2. Crear cliente asociado
        Cliente cliente = new Cliente();
        cliente.setCodCliente(clienteRepo.nextId());
        cliente.setNombreCliente(req.getNombre());
        cliente.setTelefonoCliente(req.getTelefono());
        cliente.setStatus(activo);
        cliente.setUsuarioAsociado(usuario);
        clienteRepo.save(cliente);

        // 3. Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtUtils.generateToken(userDetails);

        return new AuthResponse(token, usuario.getCorreoUsuario(), "CLIENTE",
                req.getNombre(), cliente.getCodCliente(), usuario.getCodUsuario());
    }

    private Integer getNextUsuarioId() {
        return usuarioRepo.findAll().stream()
                .mapToInt(Usuario::getCodUsuario).max().orElse(-1) + 1;
    }

    public PerfilResponse getPerfil(String correo) {
        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo).orElseThrow();
        Cliente cliente = clienteRepo.findByUsuarioAsociado_CodUsuario(usuario.getCodUsuario()).orElseThrow();
        return new PerfilResponse(cliente.getNombreCliente(), cliente.getTelefonoCliente(), usuario.getCorreoUsuario());
    }

    @Transactional
    public PerfilResponse updatePerfil(String correo, UpdatePerfilRequest req) {
        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo).orElseThrow();
        Cliente cliente = clienteRepo.findByUsuarioAsociado_CodUsuario(usuario.getCodUsuario()).orElseThrow();

        cliente.setNombreCliente(req.getNombre());
        cliente.setTelefonoCliente(req.getTelefono());
        clienteRepo.save(cliente);

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            if (req.getPassword().length() < 6) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
            }
            usuario.setContrasenaUsuario(passwordEncoder.encode(req.getPassword()));
            usuarioRepo.save(usuario);
        }

        return new PerfilResponse(cliente.getNombreCliente(), cliente.getTelefonoCliente(), usuario.getCorreoUsuario());
    }
}
