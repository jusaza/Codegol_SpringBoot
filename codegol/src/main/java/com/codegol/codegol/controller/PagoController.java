package com.codegol.codegol.controller;

import com.codegol.codegol.model.Pago;
import com.codegol.codegol.service.PagoService;
import com.codegol.codegol.service.UsuarioService;
import com.codegol.codegol.service.MatriculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MatriculaService matriculaService;

    // LISTAR PAGOS
    @GetMapping
    public String listarPagos(Model model) {
        model.addAttribute("listaPagos", pagoService.listarTodos());
        return "pago/pago-list";
    }

    // FORMULARIO NUEVO
    @GetMapping("/registrar")
    public String mostrarFormulario(Model model) {

        model.addAttribute("pago", new Pago());
        model.addAttribute("usuarios", usuarioService.listarTodas());
        model.addAttribute("matriculas", matriculaService.listarActivos());

        return "pago/pago-form";
    }

    // GUARDAR PAGO
    @PostMapping("/guardar")
    public String guardarPago(@ModelAttribute("pago") Pago pago, RedirectAttributes redirectAttrs) {

        pagoService.guardar(pago);

        redirectAttrs.addFlashAttribute("mensaje", "Pago guardado correctamente!");
        return "redirect:/pago";
    }

    // EDITAR PAGO
    @GetMapping("/editar/{id}")
    public String editarPago(@PathVariable("id") Integer id, Model model) {

        Pago pago = pagoService.buscarPorId(id);

        if (pago == null) {
            model.addAttribute("error", "No se encontró el pago solicitado.");
            return "redirect:/pago";
        }

        model.addAttribute("pago", pago);
        model.addAttribute("usuarios", usuarioService.listarTodas());
        model.addAttribute("matriculas", matriculaService.listarActivos());

        return "pago/pago-form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarPago(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {

        pagoService.eliminar(id);
        redirectAttrs.addFlashAttribute("mensaje", "Pago eliminado correctamente!");

        return "redirect:/pago";
    }
}
