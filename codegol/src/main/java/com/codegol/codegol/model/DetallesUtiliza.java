package com.codegol.codegol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "detalles_utiliza")
public class DetallesUtiliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_utiliza;

    @Positive
    @Column(nullable = false)
    private int cantidad_usada;

    @Size(max = 100)
    private String observaciones;

    // 🔗 MUCHOS usos pertenecen a un entrenamiento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenamiento", nullable = false)
    private Entrenamiento entrenamiento;

    // 🔗 MUCHOS usos pertenecen a un inventario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    // -------- GETTERS & SETTERS --------

    public int getId_utiliza() {
        return id_utiliza;
    }

    public void setId_utiliza(int id_utiliza) {
        this.id_utiliza = id_utiliza;
    }

    public int getCantidad_usada() {
        return cantidad_usada;
    }

    public void setCantidad_usada(int cantidad_usada) {
        this.cantidad_usada = cantidad_usada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
}

