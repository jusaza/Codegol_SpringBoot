package com.codegol.codegol.repository;

import com.codegol.codegol.model.DetallesUtiliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetallesUtilizaRespository extends JpaRepository<DetallesUtiliza, Integer> {
    // Lista todos los usos de un entrenamiento específico
    @Query("SELECT u FROM DetallesUtiliza u WHERE u.entrenamiento.id_entrenamiento = :idEntrenamiento")
    List<DetallesUtiliza> findByEntrenamiento(int idEntrenamiento);
}
