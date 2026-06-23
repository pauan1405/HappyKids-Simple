package com.happykids.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tmclientes")
public class Cliente {

    @Id
    @Column(name = "pkcodcliente")
    private Integer codCliente;

    @Column(name = "nombre_cliente", nullable = false, length = 60)
    private String nombreCliente;

    @Column(name = "telefono_cliente", nullable = false)
    private Long telefonoCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcods", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkusuario_asociado")
    private Usuario usuarioAsociado;

    public Integer getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Long getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(Long telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }
    
    
}
