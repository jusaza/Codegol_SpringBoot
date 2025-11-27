package com.codegol.codegol.controller;

import com.codegol.codegol.model.Usuario;
import com.codegol.codegol.service.UsuarioService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarUsuarios(Model model){
        model.addAttribute("usuarios", usuarioService.listarTodas());
        return "usuario/usuario-list";
    }
    
    @GetMapping("/inactivos")
    public String listarInactivos(Model model) {
    model.addAttribute("usuarios", usuarioService.listarInactivos());
    return "usuario/usuario-inactivos";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", usuarioService.listarRoles()); // <<--- LISTA DE ROLES
        return "usuario/usuario-form";
    }
    
    @PostMapping
    public String guardarUsuario(Usuario usuario) throws IOException {

        if (usuario.getId_usuario() != 0) {
            Usuario usuarioExistente = usuarioService.obtenerPorId(usuario.getId_usuario());

            if (usuarioExistente != null) {
                // contraseña si no se ingresó nueva
                if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                    usuario.setContrasena(usuarioExistente.getContrasena());
                }

                // foto de perfil si no se subió nueva
                if (usuario.getFotoArchivo() == null || usuario.getFotoArchivo().isEmpty()) {
                    usuario.setFoto_perfil(usuarioExistente.getFoto_perfil());
                }
            }
        } else {
            // contraseña por defecto
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                usuario.setContrasena("12345codegol");
            }
        }

        // Guardar imagen si hay nueva
        MultipartFile archivo = usuario.getFotoArchivo();
        if (archivo != null && !archivo.isEmpty()) {
            String nombreArchivo = archivo.getOriginalFilename();
            Path ruta = Paths.get("public/images/" + nombreArchivo);
            Files.write(ruta, archivo.getBytes());
            usuario.setFoto_perfil(nombreArchivo);
        }

        // <-- Aquí Spring ya llena usuario.getRoles() automáticamente desde el formulario
        usuarioService.guardar(usuario);

        return "redirect:/usuarios";
    }
    
    @GetMapping("/ver/{id_usuario}")
    public String mostrarConsultaEspecificaUsuario(@PathVariable int id_usuario, Model model) {

        Usuario usuario = usuarioService.obtenerPorId(id_usuario);

        model.addAttribute("usuario", usuario);
        
        return "usuario/usuario-view";
    } 
    
    @GetMapping("/editar/{id_usuario}")
    public String mostrarFormularioEditarUsuario(@PathVariable int id_usuario, Model model, RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioService.obtenerPorId(id_usuario);

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "No existe el usuario.");
            return "redirect:/usuarios";
        }

        if (!usuario.isEstado()) {
            redirectAttributes.addFlashAttribute("error", "No se puede editar un usuario inactivo.");
            return "redirect:/usuarios"; 
        }

        // Usuario activo
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", usuarioService.listarRoles()); // <<--- PARA EDITAR
        return "usuario/usuario-form";
    } 
    
    @GetMapping("/eliminar/{id_usuario}")
    public String eliminarUsuario(@PathVariable int id_usuario){
        usuarioService.eliminar(id_usuario);
        return "redirect:/usuarios";
    }
}
