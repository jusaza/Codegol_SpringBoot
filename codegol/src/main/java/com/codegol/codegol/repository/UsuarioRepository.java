/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codegol.codegol.repository;

import com.codegol.codegol.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    Usuario findByCorreo(String correo);
    List<Usuario> findByEstado(boolean estado);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.rol_usuario = :rol AND u.estado = true")
    List<Usuario> findJugadoresActivos(@Param("rol") String rol);

}
