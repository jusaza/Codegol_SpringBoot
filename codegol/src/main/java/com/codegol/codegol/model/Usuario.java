/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codegol.codegol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @NotBlank
    @Column(unique = true)
    private String correo;

    private String contrasena = "12345codegol";

    @NotBlank
    @Column(unique = true)
    private String nombre_completo;

    @Positive // obligatorio por ser int
    @Column(unique = true)
    private int num_identificacion;

    @NotBlank
    private String tipo_documento;

    @Positive 
    @Column(unique = true)
    private long telefono_1;

    @Positive // Opcional pero si lo envía debe ser positivo
    @Column(unique = true)
    private Long telefono_2;

    @NotBlank
    private String direccion;

    @NotBlank
    private String genero;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_nacimiento;

    private String lugar_nacimiento;

    @NotBlank
    private String grupo_sanguineo;

    private String foto_perfil;
    
    @Transient
    private MultipartFile fotoArchivo;

    private boolean estado = true;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "detalles_usuario_rol", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
    inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"))
           
    private List<Rol> roles = new ArrayList<>();

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public int getNum_identificacion() {
        return num_identificacion;
    }

    public void setNum_identificacion(int num_identificacion) {
        this.num_identificacion = num_identificacion;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public long getTelefono_1() {
        return telefono_1;
    }

    public void setTelefono_1(long telefono_1) {
        this.telefono_1 = telefono_1;
    }

    public Long getTelefono_2() {
        return telefono_2;
    }

    public void setTelefono_2(Long telefono_2) {
        this.telefono_2 = telefono_2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento;
    }

    public String getGrupo_sanguineo() {
        return grupo_sanguineo;
    }

    public void setGrupo_sanguineo(String grupo_sanguineo) {
        this.grupo_sanguineo = grupo_sanguineo;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public MultipartFile getFotoArchivo() {
        return fotoArchivo;
    }

    public void setFotoArchivo(MultipartFile fotoArchivo) {
        this.fotoArchivo = fotoArchivo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
    
}
