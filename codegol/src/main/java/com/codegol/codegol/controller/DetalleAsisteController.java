package com.codegol.codegol.controller;

import com.codegol.codegol.model.DetallesAsiste;
import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.service.DetallesAsisteService;
import com.codegol.codegol.service.EntrenamientoService;
import com.codegol.codegol.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/asistencia")
public class DetalleAsisteController {

    @Autowired
    private DetallesAsisteService detallesAsisteService;

    @Autowired
    private EntrenamientoService entrenamientoService;

    @Autowired
    private MatriculaService matriculaService;

    // -----------------------------
    // VISTA PRINCIPAL
    // -----------------------------
    @GetMapping("/{id_entrenamiento}")
    public String verAsistencia(@PathVariable("id_entrenamiento") int id,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        Entrenamiento entrenamiento = entrenamientoService.obtenerPorId(id);

        if (entrenamiento == null) {
            redirectAttributes.addFlashAttribute("error", "El entrenamiento no existe.");
            return "redirect:/entrenamientos";
        }

        // 1. Matrículas activas
        List<Matricula> matriculas = matriculaService.listarActivos();

        // 2. Asistencias registradas
        List<DetallesAsiste> asistencias = detallesAsisteService.listar()
                .stream()
                .filter(a -> a.getEntrenamiento().getId_entrenamiento() == id)
                .toList();

        // 3. Matriculas ya registradas
        List<Integer> matriculasYaRegistradas = asistencias.stream()
                .map(a -> a.getMatricula().getId_matricula())
                .toList();

        // 4. Filtrar solo las disponibles para el combo
        List<Matricula> matriculasDisponibles = matriculas.stream()
                .filter(m -> !matriculasYaRegistradas.contains(m.getId_matricula()))
                .toList();

        model.addAttribute("entrenamiento", entrenamiento);
        model.addAttribute("asistencias", asistencias);
        model.addAttribute("matriculas", matriculasDisponibles);

        return "entrenamiento/asistencia-table";
    }


    // -----------------------------
    // AGREGAR JUGADOR A ASISTENCIA
    // -----------------------------
    @PostMapping("/agregar")
    public String agregarAsistencia(@RequestParam("id_entrenamiento") int idEntrenamiento,
                                    @RequestParam("id_matricula") int idMatricula,
                                    RedirectAttributes redirectAttributes) {

        // Verificar si ya existe la asistencia
        boolean existe = detallesAsisteService.listar()
                .stream()
                .anyMatch(a -> a.getEntrenamiento().getId_entrenamiento() == idEntrenamiento
                        && a.getMatricula().getId_matricula() == idMatricula);

        if (!existe) {
            DetallesAsiste nueva = new DetallesAsiste();
            nueva.setEntrenamiento(entrenamientoService.obtenerPorId(idEntrenamiento));
            nueva.setMatricula(matriculaService.obtenerPorId(idMatricula));
            nueva.setTipoAsistencia(DetallesAsiste.TipoAsistencia.asiste); // default
            detallesAsisteService.guardar(nueva);
        }

        return "redirect:/asistencia/" + idEntrenamiento;
    }

    // -----------------------------
    // GUARDAR TODAS LAS ASISTENCIAS
    // -----------------------------
    @PostMapping("/guardar")
    public String guardarAsistencias(@RequestParam  MultiValueMap<String, String> params,
                                     @RequestParam("id_entrenamiento") int idEntrenamiento) {

        // Recorremos todas las claves del form
        detallesAsisteService.listar()
                .stream()
                .filter(a -> a.getEntrenamiento().getId_entrenamiento() == idEntrenamiento)
                .forEach(a -> {
                    String tipoStr = params.getFirst("tipoAsistencia_" + a.getId_asiste());
                    String just = params.getFirst("justificacion_" + a.getId_asiste());
                    String obs = params.getFirst("observaciones_" + a.getId_asiste());

                    if (tipoStr != null) {
                        a.setTipoAsistencia(DetallesAsiste.TipoAsistencia.valueOf(tipoStr));
                    }
                    a.setJustificacion(just);
                    a.setObservaciones(obs);

                    detallesAsisteService.guardar(a);
                });

        return "redirect:/asistencia/" + idEntrenamiento;
    }

    // -----------------------------
// ELIMINAR ASISTENCIA POR ID ASISTENCIA
// -----------------------------
    @GetMapping("/eliminar/{id_asiste}")
    public String eliminarAsistencia(@PathVariable("id_asiste") int idAsiste) {

        // Primero obtenemos la asistencia para saber a qué entrenamiento pertenece
        DetallesAsiste asistencia = detallesAsisteService.obtenerPorId(idAsiste);
        if (asistencia != null) {
            int idEntrenamiento = asistencia.getEntrenamiento().getId_entrenamiento();
            detallesAsisteService.eliminar(idAsiste);
            return "redirect:/asistencia/" + idEntrenamiento;
        }

        // Si no se encuentra la asistencia, redirigimos al listado de entrenamientos
        return "redirect:/entrenamientos";
    }

}



