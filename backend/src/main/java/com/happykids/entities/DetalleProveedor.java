package com.happykids.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tdproveedor")

public class DetalleProveedor {

    @Id
    @Column(name = "pkcodetalle_p")
    private Integer codDetalleProveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcod_pro", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcod_proveedor", nullable = false)
    private Proveedor proveedor;

    public Integer getCodDetalleProveedor() {
        return codDetalleProveedor;
    }

    public void setCodDetalleProveedor(Integer codDetalleProveedor) {
        this.codDetalleProveedor = codDetalleProveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    
}
