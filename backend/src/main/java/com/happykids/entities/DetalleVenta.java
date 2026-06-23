package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tdventas")

public class DetalleVenta {

    @Id
    @Column(name = "pkcodetalle_v")
    private Integer codDetalleVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcodventa", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcodproducto", nullable = false)
    private Producto producto;

    public Integer getCodDetalleVenta() {
        return codDetalleVenta;
    }

    public void setCodDetalleVenta(Integer codDetalleVenta) {
        this.codDetalleVenta = codDetalleVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
}
