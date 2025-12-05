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

    // Listar solo matrículas activas
    public List<Matricula> listarActivos() {
        return matriculaRepository.findByEstado(true);
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
