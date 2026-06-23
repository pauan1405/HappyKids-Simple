package com.happykids.repositories;

import com.happykids.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Solo productos activos (fkcods = 1)
    @Query("SELECT p FROM Producto p WHERE p.status.codStatus = 1")
    List<Producto> findAllActivos();

    // Buscar por nombre (case insensitive)
    @Query("SELECT p FROM Producto p WHERE p.status.codStatus = 1 AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> findByNombreContaining(String nombre);

    // Buscar por categoria via join
    @Query("SELECT p FROM Producto p JOIN DetalleCategoria dc ON dc.producto = p JOIN Categoria c ON dc.categoria = c WHERE c.nombre = :nombreCategoria AND p.status.codStatus = 1")
    List<Producto> findByCategoriaNombre(String nombreCategoria);

    // Siguiente ID disponible
    @Query("SELECT COALESCE(MAX(p.codProducto), 0) + 1 FROM Producto p")
    Integer nextId();
}
