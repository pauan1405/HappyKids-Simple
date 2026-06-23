package com.happykids.repositories;
import com.happykids.entities.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    @Query("SELECT COALESCE(MAX(d.codDetalleVenta), 0) + 1 FROM DetalleVenta d")
    Integer nextId();
}
