package com.codegol.codegol.repository;

import com.codegol.codegol.model.Rendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RendimientoRespository extends JpaRepository<Rendimiento,Integer> {

    List<Rendimiento> findByEstado(boolean estado);

    @Query("""
    SELECT e FROM Rendimiento e
    WHERE e.estado = true
        AND (:fecha IS NULL OR e.fecha_evaluacion = :fecha)
        AND (:posicion IS NULL OR LOWER(e.posicion) LIKE LOWER(CONCAT('%', :posicion, '%')))
""")

    List<Rendimiento> buscar(
            @Param("fecha")LocalDate fecha,
            @Param("posicion") String posicion
            );
}

