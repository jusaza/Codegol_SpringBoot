package com.codegol.codegol.controller;

import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.service.MatriculaService;
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
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private UsuarioService usuarioService;

    // LISTAR MATRICULAS
    @GetMapping
    public String listarMatriculas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
            @RequestParam(required = false, name = "q") String descripcion,
            Model model
    ) {

        List<Matricula> lista;

        if (fecha != null || (descripcion != null && !descripcion.isEmpty())) {
            lista = matriculaService.buscar(fecha, descripcion);
        } else {
            lista = matriculaService.listarActivos();
        }

        model.addAttribute("matriculas", lista);
        model.addAttribute("fecha", fecha);
        model.addAttribute("q", descripcion);

        return "matricula/matricula-list";
    }

    // NUEVA MATRICULA
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("matricula", new Matricula());
        model.addAttribute("usuarios", usuarioService.listarTodas());
        model.addAttribute("jugadores", usuarioService.listarTodas());

        model.addAttribute("categorias", List.of("Sub 12", "Sub 14", "Sub 16", "Sub 18", "Adulto"));
        model.addAttribute("niveles", Matricula.nivel.values());

        return "matricula/matricula-form";
    }

    // GUARDAR / ACTUALIZAR MATRICULA
    @PostMapping
    public String guardarMatricula(Matricula matricula) {

        if (matricula.getId_matricula() != 0) {
            Matricula existente = matriculaService.obtenerPorId(matricula.getId_matricula());
            if (existente != null) {
                matricula.setEstado(existente.isEstado());
            }
        }

        matriculaService.guardar(matricula);

        return "redirect:/matricula-list";
    }

    // EDITAR MATRICULA
    @GetMapping("/editar/{id}")
    public String editarMatricula(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        Matricula matricula = matriculaService.obtenerPorId(id);

        if (matricula == null) {
            redirectAttributes.addFlashAttribute("error", "No existe la matrícula.");
            return "redirect:/matricula-list";
        }

        if (!matricula.isEstado()) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede editar una matrícula inactiva.");
            return "redirect:/matricula-list";
        }

        model.addAttribute("matricula", matricula);
        model.addAttribute("usuarios", usuarioService.listarTodas());
        model.addAttribute("jugadores", usuarioService.listarTodas());
        model.addAttribute("categorias", List.of("Sub 12", "Sub 14", "Sub 16", "Sub 18", "Adulto"));
        model.addAttribute("niveles", Matricula.nivel.values());


        return "matricula/matricula-form";
    }

    // ELIMINACIÓN LÓGICA
    @GetMapping("/eliminar/{id}")
    public String eliminarMatricula(@PathVariable("id") int id) {
        matriculaService.eliminar(id);
        return "redirect:/matricula-list";
    }



}
