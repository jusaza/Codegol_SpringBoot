package com.codegol.codegol.repository;

import com.codegol.codegol.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRespository extends JpaRepository<Inventario, Integer> {
}
