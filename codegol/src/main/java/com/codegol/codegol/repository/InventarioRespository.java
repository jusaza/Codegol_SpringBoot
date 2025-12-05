package com.codegol.codegol.repository;

import com.codegol.codegol.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventarioRespository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findByEstado(boolean estado);
    @Query(
        """
        SELECT i FROM Inventario i
        WHERE i.estado = true
          AND (
                LOWER(i.nombre_articulo) LIKE LOWER(CONCAT('%', :q, '%'))
          )""")
    List<Inventario> buscarFlexible(String q);
}
