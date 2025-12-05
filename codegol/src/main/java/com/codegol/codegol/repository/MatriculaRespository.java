package com.codegol.codegol.repository;

import com.codegol.codegol.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatriculaRespository extends JpaRepository<Matricula, Integer> {

    // Listar todas las matrículas activas
    List<Matricula> findByEstado(boolean estado);

    // Búsqueda por fecha de matrícula y observaciones
    @Query("""
        SELECT e FROM Matricula e
        WHERE e.estado = true
          AND (:fecha IS NULL OR e.fecha_matricula = :fecha)
          AND (:observaciones IS NULL OR LOWER(e.observaciones) LIKE LOWER(CONCAT('%', :observaciones, '%')))
    """)
    List<Matricula> buscar(
            @Param("fecha") LocalDate fecha,
            @Param("observaciones") String observaciones
    );
}
