package com.codegol.codegol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name= "Matricula")
public class Matricula {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id_matricula;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fecha_matriucula;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fecha_inicio;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fecha_fin;

  private boolean estado = true;

  private String observaciones;

  @NotNull
  @Positive
  private  int categoria;

  public enum nivel {
    Bajo,
    Medio,
    Alto
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "nivel", nullable = false)
  private nivel nivel;

  @ManyToOne
  @JoinColumn(name = "id_jugador")
  private Usuario id_jugador;

  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario id_usuario;

  public int getId_matricula() {
    return id_matricula;
  }

  public void setId_matricula(int id_matricula) {
    this.id_matricula = id_matricula;
  }

  public LocalDate getFecha_matriucula() {
    return fecha_matriucula;
  }

  public void setFecha_matriucula(LocalDate fecha_matriucula) {
    this.fecha_matriucula = fecha_matriucula;
  }

  public LocalDate getFecha_inicio() {
    return fecha_inicio;
  }

  public void setFecha_inicio(LocalDate fecha_inicio) {
    this.fecha_inicio = fecha_inicio;
  }

  public LocalDate getFecha_fin() {
    return fecha_fin;
  }

  public void setFecha_fin(LocalDate fecha_fin) {
    this.fecha_fin = fecha_fin;
  }

  public boolean isEstado() {
    return estado;
  }

  public void setEstado(boolean estado) {
    this.estado = estado;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public int getCategoria() {
    return categoria;
  }

  public void setCategoria(int categoria) {
    this.categoria = categoria;
  }

  public nivel getNivel() {
    return nivel;
  }

  public void setNivel(nivel nivel) {
    this.nivel = nivel;
  }

  public Usuario getId_jugador() {
    return id_jugador;
  }

  public void setId_jugador(Usuario id_jugador) {
    this.id_jugador = id_jugador;
  }

  public Usuario getId_usuario() {
    return id_usuario;
  }

  public void setId_usuario(Usuario id_usuario) {
    this.id_usuario = id_usuario;
  }
}
