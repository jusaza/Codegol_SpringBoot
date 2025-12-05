package com.codegol.codegol.controller;

import com.codegol.codegol.model.Matricula;
import com.codegol.codegol.service.MatriculaService;
import com.codegol.codegol.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public String listaMatriculas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
            @RequestParam(required = false) String descripcion,
            Model model
    ) {

        List<Matricula> lista;

        if (fecha != null || (descripcion != null && !descripcion.isEmpty())){
            lista = matriculaService.buscar(fecha,descripcion);
        }else {
            lista = matriculaService.listarActivos()
        }
        model.addAttribute("matriculas", lista);
        model.addAttribute("fecha", fecha);
        model.addAttribute("descripcion", descripcion);

        return "matricula/matricula-list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo (Model model) {
        model.addAttribute("matricula", new Matricula());
        return "matricula/matricula-form";
    }

    @PostMapping
    public String guardarMatricula(Matricula matricula) {

        if (matricula.getId_matricula() != 0) {
            Matricula existente =
                    matriculaService.obtenerPorId(matricula.getId_matricula());

            if (existente != null) {
                matricula.setEstado(existente.isEstado());
            }
        }

        matriculaService.guardar(matricula);

        return "redirect:/matriculas";
    }


}
