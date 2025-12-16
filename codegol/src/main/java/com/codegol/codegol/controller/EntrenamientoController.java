package com.codegol.codegol.controller;

import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.service.EntrenamientoService;
import com.codegol.codegol.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/entrenamientos")
public class EntrenamientoController {

    @Autowired
    private EntrenamientoService entrenamientoService;

    @Autowired
    private UsuarioService usuarioService;

    // -------------------------------------------------------------
    // LISTAR ENTRENAMIENTOS (solo activos)
    // -------------------------------------------------------------
    @GetMapping
    public String listarEntrenamientos(
            @RequestParam(required = false) String q,
            Model model
    ) {

        List<Entrenamiento> lista;

        if (q != null && !q.trim().isEmpty()) {
            lista = entrenamientoService.buscarFlexible(q.trim());
        } else {
            lista = entrenamientoService.listarActivos();
        }

        model.addAttribute("entrenamientos", lista);
        model.addAttribute("q", q);

        return "entrenamiento/entrenamiento-list";
    }





    // -------------------------------------------------------------
    // NUEVO ENTRENAMIENTO
    // -------------------------------------------------------------
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("entrenamiento", new Entrenamiento());
        model.addAttribute("usuarios", usuarioService.listarTodas());  // usuario creador del entrenamiento
        return "entrenamiento/entrenamiento-form";
    }

    // -------------------------------------------------------------
    // GUARDAR / ACTUALIZAR ENTRENAMIENTO
    // -------------------------------------------------------------
    @PostMapping
    public String guardarEntrenamiento(Entrenamiento entrenamiento) {

        // Si es edición, conservar estado antiguo
        if (entrenamiento.getId_entrenamiento() != 0) {
            Entrenamiento existente =
                    entrenamientoService.obtenerPorId(entrenamiento.getId_entrenamiento());

            if (existente != null) {
                // si ya estaba desactivado, conservar
                entrenamiento.setEstado(existente.isEstado());
            }
        }

        entrenamientoService.guardar(entrenamiento);

        return "redirect:/entrenamientos";
    }


    // -------------------------------------------------------------
    // EDITAR ENTRENAMIENTO
    // -------------------------------------------------------------
    @GetMapping("/editar/{id}")
    public String editarEntrenamiento(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Entrenamiento entrenamiento = entrenamientoService.obtenerPorId(id);

        if (entrenamiento == null) {
            redirectAttributes.addFlashAttribute("error", "No existe el entrenamiento.");
            return "redirect:/entrenamientos";
        }

        if (!entrenamiento.isEstado()) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede editar un entrenamiento inactivo.");
            return "redirect:/entrenamientos";
        }

        model.addAttribute("entrenamiento", entrenamiento);
        model.addAttribute("usuarios", usuarioService.listarTodas());

        return "entrenamiento/entrenamiento-form";
    }

    // -------------------------------------------------------------
    // ELIMINACIÓN LÓGICA
    // -------------------------------------------------------------
    @GetMapping("/eliminar/{id}")
    public String eliminarEntrenamiento(@PathVariable("id") int id) {
        entrenamientoService.eliminar(id);
        return "redirect:/entrenamientos";
    }
}

