package com.codegol.codegol.controller;

import com.codegol.codegol.model.Inventario;
import com.codegol.codegol.model.Usuario;
import com.codegol.codegol.service.InventarioService;
import com.codegol.codegol.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private UsuarioService usuarioService;

    // -------------------------------------------------------------
    // LISTAR INVENTARIO (solo activos)
    // -------------------------------------------------------------
    @GetMapping
    public String listarInventario(
            @RequestParam(required = false) String q,
            Model model
    ) {

        List<Inventario> lista;

        if (q != null && !q.trim().isEmpty()) {
            lista = inventarioService.buscarFlexible(q.trim());
        } else {
            lista = inventarioService.listarActivos();
        }

        model.addAttribute("inventarios", lista);
        model.addAttribute("q", q);

        return "inventario/inventario-list";
    }


    // -------------------------------------------------------------
    // NUEVO INVENTARIO
    // -------------------------------------------------------------
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("usuarios", usuarioService.listarTodas());
        return "inventario/inventario-form";
    }


    // -------------------------------------------------------------
    // GUARDAR / ACTUALIZAR INVENTARIO
    // -------------------------------------------------------------
    @PostMapping
    public String guardarInventario(Inventario inventario) {

        // Si ya existe, preservar estado anterior
        if (inventario.getId_inventario() != 0) {
            Inventario existente =
                    inventarioService.obtenerPorId(inventario.getId_inventario());

            if (existente != null) {
                inventario.setEstado(existente.isEstado());
            }
        }

        inventarioService.guardar(inventario);

        return "redirect:/inventario";
    }


    // -------------------------------------------------------------
    // EDITAR INVENTARIO
    // -------------------------------------------------------------
    @GetMapping("/editar/{id}")
    public String editarInventario(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        Inventario inventario = inventarioService.obtenerPorId(id);

        if (inventario == null) {
            redirectAttributes.addFlashAttribute("error", "No existe el artículo en inventario.");
            return "redirect:/inventario";
        }

        if (!inventario.isEstado()) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede editar un artículo de inventario inactivo.");
            return "redirect:/inventario";
        }

        model.addAttribute("inventario", inventario);
        model.addAttribute("usuarios", usuarioService.listarTodas());

        return "inventario/inventario-form";
    }


    // -------------------------------------------------------------
    // ELIMINACIÓN LÓGICA
    // -------------------------------------------------------------
    @GetMapping("/eliminar/{id}")
    public String eliminarInventario(@PathVariable("id") int id) {
        inventarioService.eliminar(id);
        return "redirect:/inventario";
    }
}

