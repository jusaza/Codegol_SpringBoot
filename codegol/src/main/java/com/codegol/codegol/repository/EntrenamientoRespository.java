package com.codegol.codegol.repository;

import com.codegol.codegol.model.Entrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntrenamientoRespository extends JpaRepository<Entrenamiento, Integer> {
    List<Entrenamiento> findByEstado(boolean estado);

    List<Entrenamiento> findByFecha(LocalDate fecha);
}
