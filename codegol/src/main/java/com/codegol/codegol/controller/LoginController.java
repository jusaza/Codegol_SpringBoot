/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codegol.codegol.controller;

import com.codegol.codegol.model.Usuario;
import com.codegol.codegol.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";  
    }

    @PostMapping("/login")
    public String validarLogin(@ModelAttribute("usuario") Usuario usuario, Model model, HttpSession session) {

        Usuario acceso = usuarioService.buscarPorCorreo(usuario.getCorreo());

        if (acceso != null && acceso.getContrasena().equals(usuario.getContrasena())) {

    session.setAttribute("rol", acceso.getRoles().get(0).getRol_usuario());


    return "redirect:/usuarios"; 
}


        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }
}
