package com.happykids.services;

import com.happykids.dto.VentaDTOs.*;
import com.happykids.entities.*;
import com.happykids.repositories.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class VentaService {

    private final VentaRepository ventaRepo;
    private final DetalleVentaRepository detalleRepo;
    private final ClienteRepository clienteRepo;
    private final MetodoPagoRepository metodoPagoRepo;
    private final ProductoRepository productoRepo;
    private final StatusRepository statusRepo;

    public VentaService(VentaRepository ventaRepo, DetalleVentaRepository detalleRepo, ClienteRepository clienteRepo, MetodoPagoRepository metodoPagoRepo, ProductoRepository productoRepo, StatusRepository statusRepo) {
        this.ventaRepo = ventaRepo;
        this.detalleRepo = detalleRepo;
        this.clienteRepo = clienteRepo;
        this.metodoPagoRepo = metodoPagoRepo;
        this.productoRepo = productoRepo;
        this.statusRepo = statusRepo;
    }
    
    @Transactional
    public VentaResponse crearVenta(VentaRequest req) {
        Status activo = statusRepo.findById(1).orElseThrow();
        Cliente cliente = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        MetodoPago metodo = metodoPagoRepo.findById(req.getMetodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        // 1. Crear venta principal
        Venta venta = new Venta();
        venta.setCodVenta(ventaRepo.nextId());
        venta.setFechaVenta(LocalDate.now());
        venta.setTotalVenta(req.getTotal());
        venta.setDireccionEnvio(req.getDireccionEnvio());
        venta.setMetodoPago(metodo);
        venta.setCliente(cliente);
        venta.setStatus(activo);
        ventaRepo.save(venta);

        // 2. Crear detalles y descontar stock (todo en la misma transacción)
        int detalleId = detalleRepo.nextId();
        List<String> nombresProductos = new java.util.ArrayList<>();

        for (Integer prodId : req.getProductosIds()) {
            Producto prod = productoRepo.findById(prodId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + prodId));

            if (prod.getStock() < 1) {
                throw new RuntimeException("Stock insuficiente para: " + prod.getNombre());
            }

            // Descontar stock
            prod.setStock(prod.getStock() - 1);
            productoRepo.save(prod);

            // Detalle
            DetalleVenta det = new DetalleVenta();
            det.setCodDetalleVenta(detalleId++);
            det.setVenta(venta);
            det.setProducto(prod);
            detalleRepo.save(det);

            nombresProductos.add(prod.getNombre());
        }

        return new VentaResponse(venta.getCodVenta(), venta.getFechaVenta(),
                venta.getTotalVenta(), metodo.getDmetodo(),
                cliente.getNombreCliente(), venta.getDireccionEnvio(), nombresProductos);
    }

    public List<VentaResponse> ventasPorCliente(Integer clienteId) {
        return ventaRepo.findByCliente_CodCliente(clienteId).stream()
                .map(v -> new VentaResponse(
                        v.getCodVenta(), v.getFechaVenta(), v.getTotalVenta(),
                        v.getMetodoPago().getDmetodo(), v.getCliente().getNombreCliente(),
                        v.getDireccionEnvio(),
                        v.getDetalles() == null ? List.of() :
                        v.getDetalles().stream().map(d -> d.getProducto().getNombre()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public List<VentaResponse> todasLasVentas() {
        return ventaRepo.findAll().stream()
                .map(v -> new VentaResponse(
                        v.getCodVenta(), v.getFechaVenta(), v.getTotalVenta(),
                        v.getMetodoPago().getDmetodo(), v.getCliente().getNombreCliente(),
                        v.getDireccionEnvio(),
                        v.getDetalles() == null ? List.of() :
                        v.getDetalles().stream().map(d -> d.getProducto().getNombre()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public AdminStatsResponse obtenerStats() {
        return new AdminStatsResponse(
                productoRepo.count(),
                clienteRepo.count(),
                0L, // usuarios se obtiene en el controller
                ventaRepo.countVentasMesActual(),
                ventaRepo.sumTotalVentas()
        );
    }

    public VentaRepository getVentaRepo() {
        return ventaRepo;
    }

    public DetalleVentaRepository getDetalleRepo() {
        return detalleRepo;
    }

    public ClienteRepository getClienteRepo() {
        return clienteRepo;
    }

    public MetodoPagoRepository getMetodoPagoRepo() {
        return metodoPagoRepo;
    }

    public ProductoRepository getProductoRepo() {
        return productoRepo;
    }

    public StatusRepository getStatusRepo() {
        return statusRepo;
    }
    
}
