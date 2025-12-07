/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codegol.codegol.service;

import com.codegol.codegol.model.Rol;
import com.codegol.codegol.model.Usuario;
import com.codegol.codegol.repository.RolRepository;
import com.codegol.codegol.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    public Usuario buscarPorCorreo(String correo){
    return usuarioRepository.findByCorreo(correo);
    }
    
    public List<Usuario> listarTodas(){
        return usuarioRepository.findByEstado(true);
    }
    
    public List<Usuario> listarInactivos() {
    return usuarioRepository.findByEstado(false);
    }

    public List<Rol> listarRoles(){
        return rolRepository.findAll();
    }
    
    public Usuario guardar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    
    public Usuario obtenerPorId(int id_usuario){
        return usuarioRepository.findById(id_usuario).orElse(null);
    }
    
    public void activar(int id_usuario){
    Usuario usuario = usuarioRepository.findById(id_usuario).orElse(null);
    if(usuario != null){
        usuario.setEstado(true); // activar
        usuarioRepository.save(usuario); 
      }
    }
    
    public void eliminar(int id_usuario){
    Usuario usuario = usuarioRepository.findById(id_usuario).orElse(null);
    if(usuario != null){
        usuario.setEstado(false); // desactivar
        usuarioRepository.save(usuario); // guardar
    }
  }
}
