package com.codegol.codegol.repository;

import com.codegol.codegol.model.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EntrenamientoRespository extends JpaRepository<Entrenamiento, Integer> {
    List<Entrenamiento> findByEstado(boolean estado);

    @Query("""
    SELECT e FROM Entrenamiento e 
    WHERE e.estado = true
      AND (:fecha IS NULL OR e.fecha = :fecha)
      AND (:descripcion IS NULL OR LOWER(e.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%')))""")
    List<Entrenamiento> buscar(
            @Param("fecha") LocalDate fecha,
            @Param("descripcion") String descripcion
    );

}
