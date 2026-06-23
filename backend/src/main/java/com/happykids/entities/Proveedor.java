package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tmproveedor")
public class Proveedor {

    @Id
    @Column(name = "pkcodproveedor")
    private Integer codProveedor;

    @Column(name = "pro_num", nullable = false)
    private Long numero;

    @Column(name = "pro_nom", nullable = false, length = 50)
    private String nombre;

    @Column(name = "pro_direc", length = 80)
    private String direccion;

    @Column(name = "pro_correo", length = 50)
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcods", nullable = false)
    private Status status;

    public Integer getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(Integer codProveedor) {
        this.codProveedor = codProveedor;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    
}
