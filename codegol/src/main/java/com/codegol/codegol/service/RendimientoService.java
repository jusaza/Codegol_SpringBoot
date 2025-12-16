package com.codegol.codegol.service;

import com.codegol.codegol.model.Rendimiento;
import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.model.Usuario;
import com.codegol.codegol.repository.RendimientoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class RendimientoService {

    @Autowired
    private RendimientoRespository rendimientoRespository;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private EntrenamientoService entrenamientoService;

    @Autowired
    private UsuarioService usuarioService;

    //Listar solo rendimientos activos
    public List<Rendimiento> listarActivos() {
        return rendimientoRespository.findByEstado(true);
    }

    //Buscar rendimientos por fecha y posicion
    public List<Rendimiento> buscar(LocalDate fecha, String posicion) {
        return rendimientoRespository.buscar(fecha, posicion);
    }

    /// GUARDAR O ACTUALIZAR
    public Rendimiento guardar(Rendimiento rendimiento) {

        if (rendimiento.getMatricula() != null && rendimiento.getMatricula().getId_matricula() != 0) {
            rendimiento.setMatricula(matriculaService.obtenerPorId(rendimiento.getMatricula().getId_matricula()));
        } else {
            rendimiento.setMatricula(null);
        }

        if (rendimiento.getEntrenamiento() != null && rendimiento.getEntrenamiento().getId_entrenamiento() != 0) {
            rendimiento.setEntrenamiento(entrenamientoService.obtenerPorId(rendimiento.getEntrenamiento().getId_entrenamiento()));
        } else {
            rendimiento.setEntrenamiento(null);
        }

        if (rendimiento.getUsuario() != null && rendimiento.getUsuario().getId_usuario() != 0) {
            rendimiento.setUsuario(usuarioService.obtenerPorId(rendimiento.getUsuario().getId_usuario()));
        } else {
            rendimiento.setUsuario(null);
        }

        // Calcular promedio
        double prom = (
                rendimiento.getVelocidad()
                        + rendimiento.getPotencia_tiro()
                        + rendimiento.getDefensa()
                        + rendimiento.getRegate()
                        + rendimiento.getPase()
                        + rendimiento.getTecnica()
        ) / 6.0;


        rendimiento.setPromedio(BigDecimal.valueOf(prom).setScale(2, RoundingMode.HALF_UP));

        // Guardar en BD
        return rendimientoRespository.save(rendimiento);
    }


    //BUSCAR POR ID
    public Rendimiento obtenerPorId(int id) {
        return rendimientoRespository.findById(id).orElse(null);
    }

    //ELIMINACIÓN LÓGICA
    public void eliminar(int id) {
        Rendimiento rendimiento = rendimientoRespository.findById(id).orElse(null);
        if (rendimiento != null) {
            rendimiento.setEstado(false);
            rendimientoRespository.save(rendimiento);
        }
    }
}
