package com.codegol.codegol.controller;

import com.codegol.codegol.model.DetallesUtiliza;
import com.codegol.codegol.model.Entrenamiento;
import com.codegol.codegol.model.Inventario;
import com.codegol.codegol.service.DetallesUtilizaService;
import com.codegol.codegol.service.EntrenamientoService;
import com.codegol.codegol.service.InventarioService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/utiliza")
public class DetallesUtilizaController {

    private final DetallesUtilizaService utilizaService;
    private final EntrenamientoService entrenamientoService;
    private final InventarioService inventarioService;

    public DetallesUtilizaController(
            DetallesUtilizaService utilizaService,
            EntrenamientoService entrenamientoService,
            InventarioService inventarioService
    ) {
        this.utilizaService = utilizaService;
        this.entrenamientoService = entrenamientoService;
        this.inventarioService = inventarioService;
    }

    // ============================================================================
    // VISTA PRINCIPAL — Muestra lo usado por un entrenamiento
    // ============================================================================
    @GetMapping("/{idEntrenamiento}")
    public String vistaUtiliza(@PathVariable int idEntrenamiento, Model model) {

        Entrenamiento entrenamiento = entrenamientoService.obtenerPorId(idEntrenamiento);
        if (entrenamiento == null) {
            return "redirect:/entrenamientos";
        }

        List<Inventario> inventarios = inventarioService.listarActivos();
        List<DetallesUtiliza> usos = utilizaService.listarPorEntrenamiento(idEntrenamiento);

        model.addAttribute("entrenamiento", entrenamiento);
        model.addAttribute("inventarios", inventarios);
        model.addAttribute("usos", usos); // ← NOMBRE CORRECTO PARA LA TABLA
        model.addAttribute("nuevoUso", new DetallesUtiliza());

        return "entrenamiento/utiliza-table";
    }

    // ============================================================================
    // REGISTRAR USO
    // ============================================================================
    @PostMapping("/agregar")
    public String registrarUso(
            @ModelAttribute DetallesUtiliza nuevoUso,
            @RequestParam int id_entrenamiento,
            @RequestParam int id_inventario
    ) {

        Entrenamiento entrenamiento = entrenamientoService.obtenerPorId(id_entrenamiento);
        Inventario inventario = inventarioService.obtenerPorId(id_inventario);

        nuevoUso.setEntrenamiento(entrenamiento);
        nuevoUso.setInventario(inventario);

        utilizaService.registrarUso(nuevoUso);

        return "redirect:/utiliza/" + id_entrenamiento;
    }

    // ============================================================================
    // ACTUALIZAR USO (editar cantidad u observaciones)
    // ============================================================================
    @PostMapping("/guardar")
    public String guardarCambios(
            @RequestParam int id_entrenamiento,
            @RequestParam Map<String, String> params
    ) {
        utilizaService.guardarMasivo(id_entrenamiento, params);
        return "redirect:/utiliza/" + id_entrenamiento;
    }



    // -------------------------------------------------------------
    // MARCAR COMO DEVUELTO (bloquea los campos)
    // -------------------------------------------------------------
        @GetMapping("/devolver/{id}")
        public String marcarDevuelto(@PathVariable int id, @RequestParam int id_entrenamiento) {

            utilizaService.marcarDevuelto(id);
            return "redirect:/utiliza/" + id_entrenamiento;
        }

        // -------------------------------------------------------------
    // DESBLOQUEAR (para corregir un error del usuario)
    // -------------------------------------------------------------
        @GetMapping("/editar/{id}")
        public String desmarcarDevuelto(@PathVariable int id, @RequestParam int id_entrenamiento) {

            utilizaService.desmarcarDevuelto(id);
            return "redirect:/utiliza/" + id_entrenamiento;
        }



}

