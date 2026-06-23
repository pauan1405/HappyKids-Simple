package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tmcategorias")

public class Categoria {

    @Id
    @Column(name = "pkcod_ca")
    private Integer codCategoria;

    @Column(name = "nombre_ca", nullable = false, length = 40)
    private String nombre;

    @Column(name = "dcategoria", nullable = false, length = 70)
    private String descripcion;

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
