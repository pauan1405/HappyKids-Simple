package com.happykids.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tmusuarios")

public class Usuario {

    @Id
    @Column(name = "pkcodusuario")
    private Integer codUsuario;

    @Column(name = "correo_usuario", nullable = false, length = 50, unique = true)
    private String correoUsuario;

    @Column(name = "contrasena_usuario", nullable = false, length = 255)
    private String contrasenaUsuario;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkcods", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkrol_usuario", nullable = false)
    private Rol rol;

    
    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) fechaRegistro = LocalDate.now();
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
