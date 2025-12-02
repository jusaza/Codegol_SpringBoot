package com.codegol.codegol.service;

import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.repository.EntrenamientoRespository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntrenamientoService {

    @Autowired
    private EntrenamientoRespository entrenamientoRepository;

    // Listar solo entrenamientos activos
    public List<Entrenamiento> listarActivos() {
        return entrenamientoRepository.findByEstado(true);
    }

    // Guardar o actualizar
    public Entrenamiento guardar(Entrenamiento entrenamiento) {
        return entrenamientoRepository.save(entrenamiento);
    }

    // Buscar por ID
    public Entrenamiento obtenerPorId(int id) {
        return entrenamientoRepository.findById(id).orElse(null);
    }

    // Buscar por fecha
    public List<Entrenamiento> buscarPorFecha(LocalDate fecha) {
        return entrenamientoRepository.findByFecha(fecha);
    }

    // Eliminación lógica
    public void eliminar(int id) {
        Entrenamiento entrenamiento = entrenamientoRepository.findById(id).orElse(null);
        if (entrenamiento != null) {
            entrenamiento.setEstado(false);
            entrenamientoRepository.save(entrenamiento);
        }
    }
}

