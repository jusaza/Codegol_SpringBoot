package com.codegol.codegol.service;

import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.repository.MatriculaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

  @Autowired
  private MatriculaRespository matriculaRespository;

  public List<Matricula> listarActivos() {return matriculaRespository.findByEstado(true);}

  public Matricula guardar(Matricula matricula) { return matriculaRespository.save(matricula);}

  public Matricula obtenerPorId(int id) {
    return matriculaRespository.findById(id).orElse(null);
  }

  //public Matricula obtenerCategoria(int categoria) { return matriculaRespository.findByCategoria(categoria);}

}
