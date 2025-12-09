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

        // --- Resolver FKs si Thymeleaf bindea solo el id en matricula/entrenamiento/usuario ---
        if (rendimiento.getMatricula() != null && rendimiento.getMatricula().getId_matricula() != 0) {
            Matricula m = matriculaService.obtenerPorId(rendimiento.getMatricula().getId_matricula());
            rendimiento.setMatricula(m);
        } else {
            rendimiento.setMatricula(null);
        }

        if (rendimiento.getEntrenamiento() != null && rendimiento.getEntrenamiento().getId_entrenamiento() != 0) {
            Entrenamiento e = entrenamientoService.obtenerPorId(rendimiento.getEntrenamiento().getId_entrenamiento());
            rendimiento.setEntrenamiento(e);
        } else {
            rendimiento.setEntrenamiento(null);
        }

        if (rendimiento.getUsuario() != null && rendimiento.getUsuario().getId_usuario() != 0) {
            Usuario u = usuarioService.obtenerPorId(rendimiento.getUsuario().getId_usuario());
            rendimiento.setUsuario(u);
        } else {
            rendimiento.setUsuario(null);
        }

        // --- Calcular promedio para mostrar en UI si quieres (NO lo persistiremos si la DB lo calcula) ---
        double v = rendimiento.getVelocidad();
        double pt = rendimiento.getPotencia_tiro();
        double d = rendimiento.getDefensa();
        double rg = rendimiento.getRegate();
        double p = rendimiento.getPase();
        double t = rendimiento.getTecnica();

        double prom = (v + pt + d + rg + p + t) / 6.0;
        BigDecimal promedioBd = BigDecimal.valueOf(prom).setScale(2, RoundingMode.HALF_UP);

        // Si quieres usar el valor para mostrar en la vista antes de persistir, puedes dejarlo asignado,
        // pero **asegúrate** de quitarlo antes de guardar (la columna es GENERATED en la BD).
        // rendimiento.setPromedio(promedioBd);

        // Evitar enviar valor en el INSERT/UPDATE porque la DB calcula 'promedio'
        rendimiento.setPromedio(null);

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
