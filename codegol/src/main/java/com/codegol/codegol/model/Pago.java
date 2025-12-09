package com.codegol.codegol.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "concepto_pago", nullable = false, length = 100)
    private String conceptoPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    public enum MetodoPago {
        Efectivo,
        Transferencia,
        Tarjeta,
        Otro
    }

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "observaciones")
    private String observaciones;

    // ------------ RELACIONES ------------
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;


    // ------------ GETTERS Y SETTERS ------------

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getConceptoPago() {
        return conceptoPago;
    }

    public void setConceptoPago(String conceptoPago) {
        this.conceptoPago = conceptoPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
}
