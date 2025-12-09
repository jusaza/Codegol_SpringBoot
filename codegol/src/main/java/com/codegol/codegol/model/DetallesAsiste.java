package com.codegol.codegol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "detalles_asiste")
public class DetallesAsiste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_asiste;

    // ---------------- ENUM de asistencia ----------------
    public enum TipoAsistencia {
        asiste,
        inasiste,
        llegada_tarde
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_asistencia", nullable = true) // permitir null
    private TipoAsistencia tipoAsistencia;

    // ----------------------------------------------------

    @Size(max = 100)
    private String justificacion;

    @Size(max = 100)
    private String observaciones;

    // ------------ Relación con MATRICULA --------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    // ----------- Relación con ENTRENAMIENTO -----------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenamiento", nullable = false)
    private Entrenamiento entrenamiento;

    // ---------------- GETTERS & SETTERS ----------------

    public int getId_asiste() {
        return id_asiste;
    }

    public void setId_asiste(int id_asiste) {
        this.id_asiste = id_asiste;
    }

    public TipoAsistencia getTipoAsistencia() {
        return tipoAsistencia;
    }

    public void setTipoAsistencia(TipoAsistencia tipoAsistencia) {
        this.tipoAsistencia = tipoAsistencia;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

}

