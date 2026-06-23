package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tdcategorias")
public class DetalleCategoria {

    @Id
    @Column(name = "pkcod_cad")
    private Integer codDetalleCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcodca", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcodproducto", nullable = false)
    private Producto producto;

    public Integer getCodDetalleCategoria() {
        return codDetalleCategoria;
    }

    public void setCodDetalleCategoria(Integer codDetalleCategoria) {
        this.codDetalleCategoria = codDetalleCategoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
}
