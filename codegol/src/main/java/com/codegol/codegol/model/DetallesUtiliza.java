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

    @PositiveOrZero
    private int cantidad_devuelta = 0;


    private boolean devuelto = false;

    // cantidad del inventario ANTES de usarlo
    @PositiveOrZero
    private int cantidad_inicial;

    @Size(max = 100)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenamiento", nullable = false)
    private Entrenamiento entrenamiento;

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

    public int getCantidad_devuelta() {
        return cantidad_devuelta;
    }

    public void setCantidad_devuelta(int cantidad_devuelta) {
        this.cantidad_devuelta = cantidad_devuelta;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    public int getCantidad_inicial() {
        return cantidad_inicial;
    }

    public void setCantidad_inicial(int cantidad_inicial) {
        this.cantidad_inicial = cantidad_inicial;
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

