package com.codegol.codegol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entrenamiento")
public class Entrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_entrenamiento;

    @Size(max = 100)
    private String descripcion;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hora_inicio;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hora_fin;

    @NotBlank
    @Size(max = 50)
    private String lugar;

    @Size(max = 100)
    private String observaciones;

    private boolean estado = true;

    // RELACIÓN: muchos entrenamientos pertenecen a un usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // ===== RELACIÓN CON DETALLES_UTILIZA =====
    @OneToMany(mappedBy = "entrenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesUtiliza> detallesUtiliza = new ArrayList<>();

    // ===== RELACIÓN CON DETALLES_ASISTE =====
    @OneToMany(mappedBy = "entrenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesAsiste> detallesAsiste = new ArrayList<>();


    // Getters y Setters -------------------

    public int getId_entrenamiento() {
        return id_entrenamiento;
    }

    public void setId_entrenamiento(int id_entrenamiento) {
        this.id_entrenamiento = id_entrenamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(LocalTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<DetallesAsiste> getDetallesAsiste() {
        return detallesAsiste;
    }

    public void setDetallesAsiste(List<DetallesAsiste> detallesAsiste) {
        this.detallesAsiste = detallesAsiste;
    }

    public List<DetallesUtiliza> getDetallesUtiliza() {
        return detallesUtiliza;
    }

    public void setDetallesUtiliza(List<DetallesUtiliza> detallesUtiliza) {
        this.detallesUtiliza = detallesUtiliza;
    }


}

