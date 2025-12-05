package com.codegol.codegol.service;

import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.repository.MatriculaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRespository matriculaRepository;

<<<<<<< HEAD
    // Listar solo matrículas activas
    public List<Matricula> listarActivos(){
        return matriculaRepository.findByEstado(true);
=======
  public List<Matricula> listarActivos() {return matriculaRespository.findByEstado(true);}

  public Matricula guardar(Matricula matricula) { return matriculaRespository.save(matricula);}

  public Matricula obtenerPorId(int id) {
    return matriculaRespository.findById(id).orElse(null);
  }

    public List<Matricula> buscar(LocalDate fecha, String descripcion) {
        return List.of();
    }

    public void eliminar(int id) {
>>>>>>> 1d0b23c4233599790e2a07e6cd8b9bbdcfeb0c78
    }

    // Buscar matrículas por fecha y observaciones
    public List<Matricula> buscar(LocalDate fecha, String observaciones) {
        return matriculaRepository.buscar(fecha, observaciones);
    }

    // Guardar o actualizar matrícula
    public Matricula guardar(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    // Obtener matrícula por ID
    public Matricula obtenerPorId(int id) {
        return matriculaRepository.findById(id).orElse(null);
    }

    // Eliminación lógica
    public void eliminar(int id) {
        Matricula matricula = matriculaRepository.findById(id).orElse(null);
        if (matricula != null) {
            matricula.setEstado(false);
            matriculaRepository.save(matricula);
        }
    }
}
