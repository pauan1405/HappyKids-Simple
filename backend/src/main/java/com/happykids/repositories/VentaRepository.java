package com.happykids.repositories;

import com.happykids.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByCliente_CodCliente(Integer codCliente);

    @Query("SELECT COALESCE(MAX(v.codVenta), 0) + 1 FROM Venta v")
    Integer nextId();

    @Query("SELECT COALESCE(SUM(v.totalVenta), 0) FROM Venta v WHERE v.status.codStatus = 1")
    BigDecimal sumTotalVentas();

    @Query("SELECT COALESCE(COUNT(v), 0) FROM Venta v WHERE MONTH(v.fechaVenta) = MONTH(CURRENT_DATE) AND YEAR(v.fechaVenta) = YEAR(CURRENT_DATE)")
    Long countVentasMesActual();
}
