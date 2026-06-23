package com.happykids.security;

import com.happykids.entities.Usuario;
import com.happykids.repositories.UsuarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoUsuario(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        // Solo usuarios activos pueden autenticarse
        if (usuario.getStatus().getCodStatus() != 1) {
            throw new UsernameNotFoundException("Usuario inactivo: " + correo);
        }

        String rol = "ROLE_" + usuario.getRol().getDrol(); // ROLE_ADMIN o ROLE_CLIENTE

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreoUsuario(),
                usuario.getContrasenaUsuario(),
                List.of(new SimpleGrantedAuthority(rol))
        );
    }
}
