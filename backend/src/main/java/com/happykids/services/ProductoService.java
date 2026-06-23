package com.happykids.services;

import com.happykids.dto.ProductoDTOs.*;
import com.happykids.entities.*;
import com.happykids.repositories.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final StatusRepository   statusRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoService(ProductoRepository productoRepo, StatusRepository statusRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.statusRepo = statusRepo;
        this.categoriaRepo = categoriaRepo;
    }
     
    public List<ProductoResponse> buscar(String nombre, String categoria) {
        List<Producto> lista;
        if (nombre != null && !nombre.isBlank()) {
            lista = productoRepo.findByNombreContaining(nombre.trim());
        } else if (categoria != null && !categoria.isBlank()) {
            lista = productoRepo.findByCategoriaNombre(categoria.trim());
        } else {
            lista = productoRepo.findAllActivos();
        }
        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ProductoResponse obtenerPorId(Integer id) {
        Producto p = productoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(p);
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest req) {
        Status activo = statusRepo.findById(1).orElseThrow();
        Producto p = new Producto();
        p.setCodProducto(productoRepo.nextId());
        p.setNombre(req.getNombre());
        p.setDescripcion(req.getDescripcion());
        p.setPrecioCompra(req.getPrecioCompra());
        p.setPrecioVenta(req.getPrecioVenta());
        p.setStock(req.getStock());
        p.setSerial(req.getSerial());
        p.setImagenUrl(req.getImagenUrl());
        p.setStatus(activo);
        productoRepo.save(p);
        return toResponse(p);
    }

    @Transactional
    public ProductoResponse actualizar(Integer id, ProductoRequest req) {
        Producto p = productoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (req.getNombre()      != null) p.setNombre(req.getNombre());
        if (req.getDescripcion() != null) p.setDescripcion(req.getDescripcion());
        if (req.getPrecioVenta() != null) p.setPrecioVenta(req.getPrecioVenta());
        if (req.getStock()       != null) p.setStock(req.getStock());
        if (req.getImagenUrl()   != null) p.setImagenUrl(req.getImagenUrl());
        productoRepo.save(p);
        return toResponse(p);
    }

    @Transactional
    public void eliminar(Integer id) {
        Producto p = productoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Status eliminado = statusRepo.findById(0).orElseThrow();
        p.setStatus(eliminado);
        productoRepo.save(p);
    }

    private ProductoResponse toResponse(Producto p) {
        return new ProductoResponse(
            p.getCodProducto(), p.getNombre(), p.getDescripcion(),
            p.getPrecioVenta(), p.getStock(), p.getSerial(),
            p.getImagenUrl(), ""
        );
    }
}
