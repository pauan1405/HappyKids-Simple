package com.happykids.repositories;

import com.happykids.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByUsuarioAsociado_CodUsuario(Integer codUsuario);
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(MAX(c.codCliente), 0) + 1 FROM Cliente c")
    Integer nextId();
}
