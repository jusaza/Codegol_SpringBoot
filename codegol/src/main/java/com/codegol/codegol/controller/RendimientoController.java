package com.codegol.codegol.controller;

import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.model.Rendimiento;
import com.codegol.codegol.model.Usuario;
import com.codegol.codegol.service.RendimientoService;
import com.codegol.codegol.service.UsuarioService;
import com.codegol.codegol.service.MatriculaService;
import com.codegol.codegol.service.EntrenamientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rendimientos")
public class RendimientoController {

    @Autowired
    private RendimientoService rendimientoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private EntrenamientoService entrenamientoService;

    // LISTAR RENDIMIENTOS (igual)
    @GetMapping
    public String listarRendimientos(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
            @RequestParam(required = false) String posicion,
            Model model
    ) {

        List<Rendimiento> lista;

        if (fecha != null || (posicion != null && !posicion.isEmpty())) {
            lista = rendimientoService.buscar(fecha, posicion);
        } else {
            lista = rendimientoService.listarActivos();
        }

        model.addAttribute("rendimientos", lista);
        model.addAttribute("fecha", fecha);
        model.addAttribute("posicion", posicion);

        return "rendimiento/rendimiento-list";
    }

    // NUEVO RENDIMIENTO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        Rendimiento e = new Rendimiento();
        e.setEstado(true); // Inicializa como activo

        if (e.getMatricula() == null) {
            e.setMatricula(new Matricula()); // crea instancia vacía
        }
        if (e.getEntrenamiento() == null) {
            e.setEntrenamiento(new Entrenamiento());
        }
        if (e.getUsuario() == null) {
            e.setUsuario(new Usuario());
        }


        model.addAttribute("rendimiento", e);

        model.addAttribute("usuarios", usuarioService.listarTodas());
        model.addAttribute("matriculas", matriculaService.listarTodas() != null ? matriculaService.listarTodas() : new ArrayList<>());
        model.addAttribute("entrenamientos", entrenamientoService.listarTodas() != null ? entrenamientoService.listarTodas() : new ArrayList<>());

        return "rendimiento/rendimiento-form";
    }

    // GUARDAR / ACTUALIZAR RENDIMIENTO
    @PostMapping
    public String guardarRendimiento(@ModelAttribute Rendimiento rendimiento, RedirectAttributes redirectAttributes) {

        // Validar selects (no permitimos placeholder 0)
        if (rendimiento.getMatricula() == null || rendimiento.getMatricula().getId_matricula() == 0) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una Matrícula válida.");
            return "redirect:/rendimientos/nuevo";
        }
        if (rendimiento.getEntrenamiento() == null || rendimiento.getEntrenamiento().getId_entrenamiento() == 0) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar un Entrenamiento válido.");
            return "redirect:/rendimientos/nuevo";
        }
        if (rendimiento.getUsuario() == null || rendimiento.getUsuario().getId_usuario() == 0) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar un Usuario válido.");
            return "redirect:/rendimientos/nuevo";
        }

        // Si es edición, conservar estado antiguo
        if (rendimiento.getId_rendimiento() != 0) {
            Rendimiento existente = rendimientoService.obtenerPorId(rendimiento.getId_rendimiento());
            if (existente != null) {
                rendimiento.setEstado(existente.getEstado());
            }
        }

        // Guardar (el servicio resolverá FKs y calculará promedio)
        rendimientoService.guardar(rendimiento);

        return "redirect:/rendimientos";
    }



    // EDITAR RENDIMIENTO
    @GetMapping("/editar/{id}")
    public String editarRendimiento(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Rendimiento rendimiento = rendimientoService.obtenerPorId(id);

        if (rendimiento == null) {
            redirectAttributes.addFlashAttribute("error", "No existe el rendimiento.");
            return "redirect:/rendimientos";
        }

        if (!rendimiento.getEstado()) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede editar un rendimiento inactivo.");
            return "redirect:/rendimientos";
        }

        if (rendimiento.getMatricula() == null) rendimiento.setMatricula(new Matricula());
        if (rendimiento.getEntrenamiento() == null) rendimiento.setEntrenamiento(new Entrenamiento());
        if (rendimiento.getUsuario() == null) rendimiento.setUsuario(new Usuario());

        model.addAttribute("rendimiento", rendimiento);
        model.addAttribute("usuarios", usuarioService.listarTodas());
        model.addAttribute("matriculas", matriculaService.listarTodas() != null ? matriculaService.listarTodas() : new ArrayList<>());
        model.addAttribute("entrenamientos", entrenamientoService.listarTodas() != null ? entrenamientoService.listarTodas() : new ArrayList<>());

        return "rendimiento/rendimiento-form";
    }

    // ELIMINACIÓN LÓGICA
    @GetMapping("/eliminar/{id}")
    public String eliminarRendimiento(@PathVariable("id") int id) {
        rendimientoService.eliminar(id);
        return "redirect:/rendimientos";
    }
}
