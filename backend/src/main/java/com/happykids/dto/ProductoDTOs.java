package com.happykids.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductoDTOs {

    // ProductoRequest
    public static class ProductoRequest {
        @NotBlank @Size(max = 50) private String nombre;
        @NotBlank @Size(max = 50) private String descripcion;
        @NotNull @DecimalMin("0.01") private BigDecimal precioCompra;
        @NotNull @DecimalMin("0.01") private BigDecimal precioVenta;
        @NotNull @Min(0) private Integer stock;
        private String serial;
        private String imagenUrl;
        private Integer categoriaId;

        public ProductoRequest() {}

        // Getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public BigDecimal getPrecioCompra() { return precioCompra; }
        public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }
        public BigDecimal getPrecioVenta() { return precioVenta; }
        public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }
        public String getSerial() { return serial; }
        public void setSerial(String serial) { this.serial = serial; }
        public String getImagenUrl() { return imagenUrl; }
        public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
        public Integer getCategoriaId() { return categoriaId; }
        public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
    }

    // ProductoResponse
    public static class ProductoResponse {
        private Integer id;
        private String nombre;
        private String descripcion;
        private BigDecimal precioVenta;
        private Integer stock;
        private String serial;
        private String imagenUrl;
        private String categoriaNombre;

        public ProductoResponse(Integer id, String nombre, String descripcion, BigDecimal precioVenta, Integer stock, String serial, String imagenUrl, String categoriaNombre) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precioVenta = precioVenta;
            this.stock = stock;
            this.serial = serial;
            this.imagenUrl = imagenUrl;
            this.categoriaNombre = categoriaNombre;
        }

        // Getters
        public Integer getId() { return id; }
        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public BigDecimal getPrecioVenta() { return precioVenta; }
        public Integer getStock() { return stock; }
        public String getSerial() { return serial; }
        public String getImagenUrl() { return imagenUrl; }
        public String getCategoriaNombre() { return categoriaNombre; }
    }
}