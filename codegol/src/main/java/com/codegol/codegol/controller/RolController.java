/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codegol.codegol.controller;
import com.codegol.codegol.model.Rol;
import com.codegol.codegol.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RolController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarRoles(Model model){
        model.addAttribute("roles", usuarioService.listarRoles());
        return "usuario/rol-list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model){
        model.addAttribute("rol", new Rol());
        return "usuario/rol-form";
    }
    
    @PostMapping
    public String guardarUsuario(Rol rol) {

        usuarioService.guardarRol(rol);

        return "redirect:/roles";
    }
    
    @GetMapping("/editar/{id_rol}")
    public String mostrarFormularioEditarRol(@PathVariable int id_rol, Model model) {

        model.addAttribute("rol", usuarioService.obtenerIdRol(id_rol)); 
        
        return "usuario/rol-form";
    } 
    
    @GetMapping("/eliminar/{id_rol}")
    public String eliminarroles(@PathVariable int id_rol){
        usuarioService.eliminarRol(id_rol);
        return "redirect:/roles";
    }
   }
