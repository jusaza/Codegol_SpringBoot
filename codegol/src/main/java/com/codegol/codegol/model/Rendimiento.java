package com.codegol.codegol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rendimiento")
public class Rendimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rendimiento;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_evaluacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 60)
    private Posicion posicion;

    public enum Posicion {

        portero("Portero"),

        defensa_central("Defensa central"),
        lateral_derecho("Lateral derecho"),
        lateral_izquierdo("Lateral izquierdo"),

        mediocentro_defensivo("Mediocentro defensivo"),
        mediocentro("Mediocentro"),
        mediocentro_ofensivo("Mediocentro ofensivo"),

        extremo_derecho("Extremo derecho"),
        extremo_izquierdo("Extremo izquierdo"),

        delantero_centro("Delantero centro"),
        segundo_delantero("Segundo delantero");

        private final String label;

        Posicion(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }



    @NotNull
    private int velocidad;

    @NotNull
    private int potencia_tiro;

    @NotNull
    private int defensa;

    @NotNull
    private int regate;

    @NotNull
    private int pase;

    @NotNull
    private int tecnica;

    // si la columna en la DB es GENERATED ALWAYS AS (...), evitar que JPA intente escribirla
    @Column(precision = 5, scale = 2, insertable = false, updatable = false)
    private BigDecimal promedio;


    @Size(max = 100)
    private String observaciones;

    private Boolean estado = true;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenamiento", nullable = false)
    private Entrenamiento entrenamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public int getId_rendimiento() { return id_rendimiento; }
    public void setId_rendimiento(int id_rendimiento) { this.id_rendimiento = id_rendimiento; }

    public LocalDate getFecha_evaluacion() { return fecha_evaluacion; }
    public void setFecha_evaluacion(LocalDate fecha_evaluacion) { this.fecha_evaluacion = fecha_evaluacion; }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }


    public int getVelocidad() { return velocidad; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }

    public int getPotencia_tiro() { return potencia_tiro; }
    public void setPotencia_tiro(int potencia_tiro) { this.potencia_tiro = potencia_tiro; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public int getRegate() { return regate; }
    public void setRegate(int regate) { this.regate = regate; }

    public int getPase() { return pase; }
    public void setPase(int pase) { this.pase = pase; }

    public int getTecnica() { return tecnica; }
    public void setTecnica(int tecnica) { this.tecnica = tecnica; }

    public BigDecimal getPromedio() { return promedio; }
    public void setPromedio(BigDecimal promedio) { this.promedio = promedio; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Boolean getEstado() {return estado;}
    public void setEstado(Boolean estado) {this.estado = estado;}

    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }

    public Entrenamiento getEntrenamiento() { return entrenamiento; }
    public void setEntrenamiento(Entrenamiento entrenamiento) { this.entrenamiento = entrenamiento; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
