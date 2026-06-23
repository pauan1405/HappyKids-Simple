package com.happykids.dto;

import jakarta.validation.constraints.*;

public class AuthDTOs {

    // LoginRequest
    public static class LoginRequest {
        @NotBlank @Email
        private String email;
        @NotBlank
        private String password;

        public LoginRequest() {}

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // RegisterRequest
    public static class RegisterRequest {
        @NotBlank @Email
        private String email;
        @NotBlank @Size(min = 2, max = 60)
        private String nombre;
        @NotBlank @Size(min = 6, max = 40)
        private String password;
        @NotNull
        private Long telefono;

        public RegisterRequest() {}

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Long getTelefono() { return telefono; }
        public void setTelefono(Long telefono) { this.telefono = telefono; }
    }

    // UpdatePerfilRequest
    public static class UpdatePerfilRequest {
        @NotBlank @Size(min = 2, max = 60)
        private String nombre;
        @NotNull
        private Long telefono;
        // Opcional: si viene en blanco, no se cambia la contraseña
        private String password;

        public UpdatePerfilRequest() {}

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public Long getTelefono() { return telefono; }
        public void setTelefono(Long telefono) { this.telefono = telefono; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // PerfilResponse
    public static class PerfilResponse {
        private String nombre;
        private Long telefono;
        private String correo;

        public PerfilResponse(String nombre, Long telefono, String correo) {
            this.nombre = nombre;
            this.telefono = telefono;
            this.correo = correo;
        }

        public String getNombre() { return nombre; }
        public Long getTelefono() { return telefono; }
        public String getCorreo() { return correo; }
    }

    // AuthResponse
    public static class AuthResponse {
        private String token;
        private String correo;
        private String rol;
        private String nombre;
        private Integer clienteId;
        private Integer usuarioId;

        public AuthResponse(String token, String correo, String rol, String nombre, Integer clienteId, Integer usuarioId) {
            this.token = token;
            this.correo = correo;
            this.rol = rol;
            this.nombre = nombre;
            this.clienteId = clienteId;
            this.usuarioId = usuarioId;
        }

        // Getters (si los usas en algún lado)
        public String getToken() { return token; }
        public String getCorreo() { return correo; }
        public String getRol() { return rol; }
        public String getNombre() { return nombre; }
        public Integer getClienteId() { return clienteId; }
        public Integer getUsuarioId() { return usuarioId; }
    }
}