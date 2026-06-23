package com.happykids.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VentaDTOs {

    // VentaRequest
    public static class VentaRequest {
        @NotNull private Integer clienteId;
        @NotNull private Integer metodoPagoId;
        @NotNull @Size(min = 1) private List<Integer> productosIds;
        @NotNull @DecimalMin("0.01") private BigDecimal total;
        @NotBlank @Size(max = 150) private String direccionEnvio;

        public VentaRequest() {}

        public Integer getClienteId() { return clienteId; }
        public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
        public Integer getMetodoPagoId() { return metodoPagoId; }
        public void setMetodoPagoId(Integer metodoPagoId) { this.metodoPagoId = metodoPagoId; }
        public List<Integer> getProductosIds() { return productosIds; }
        public void setProductosIds(List<Integer> productosIds) { this.productosIds = productosIds; }
        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }
        public String getDireccionEnvio() { return direccionEnvio; }
        public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }
    }

    // VentaResponse
    public static class VentaResponse {
        private Integer id;
        private LocalDate fecha;
        private BigDecimal total;
        private String metodoPago;
        private String clienteNombre;
        private String direccionEnvio;
        private List<String> productos;

        public VentaResponse(Integer id, LocalDate fecha, BigDecimal total, String metodoPago, String clienteNombre, String direccionEnvio, List<String> productos) {
            this.id = id;
            this.fecha = fecha;
            this.total = total;
            this.metodoPago = metodoPago;
            this.clienteNombre = clienteNombre;
            this.direccionEnvio = direccionEnvio;
            this.productos = productos;
        }

        // Getters (necesarios si los usas)
        public Integer getId() { return id; }
        public LocalDate getFecha() { return fecha; }
        public BigDecimal getTotal() { return total; }
        public String getMetodoPago() { return metodoPago; }
        public String getClienteNombre() { return clienteNombre; }
        public String getDireccionEnvio() { return direccionEnvio; }
        public List<String> getProductos() { return productos; }
    }

    // AdminStatsResponse
    public static class AdminStatsResponse {
        private Long totalProductos;
        private Long totalClientes;
        private Long totalUsuarios;
        private Long ventasMesActual;
        private BigDecimal totalIngresos;

        public AdminStatsResponse(Long totalProductos, Long totalClientes, Long totalUsuarios, Long ventasMesActual, BigDecimal totalIngresos) {
            this.totalProductos = totalProductos;
            this.totalClientes = totalClientes;
            this.totalUsuarios = totalUsuarios;
            this.ventasMesActual = ventasMesActual;
            this.totalIngresos = totalIngresos;
        }

        // Getters (necesarios en VentaController.stats())
        public Long getTotalProductos() { return totalProductos; }
        public Long getTotalClientes() { return totalClientes; }
        public Long getTotalUsuarios() { return totalUsuarios; }
        public Long getVentasMesActual() { return ventasMesActual; }
        public BigDecimal getTotalIngresos() { return totalIngresos; }
    }
}