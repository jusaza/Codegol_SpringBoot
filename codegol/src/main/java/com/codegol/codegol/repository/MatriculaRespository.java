package com.codegol.codegol.repository;

import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;

public interface MatriculaRespository extends JpaRepository <Matricula ,Integer> {
  List<Matricula> findByEstado(boolean estado);

  List<Matricula> findByCategoria (int categoria);
}
