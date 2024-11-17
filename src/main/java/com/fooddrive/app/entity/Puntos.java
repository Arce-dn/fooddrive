package com.fooddrive.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "puntos")
public class Puntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPunto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private String tipo; // "Ganados", "Utilizados" o "Puntos expirados"

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_lealtad_id", nullable = false)
    private ProgramaLealtad programaLealtad;

    // Constructor por defecto
    public Puntos() {}

    // Constructor personalizado
    public Puntos(int cantidad, String tipo, LocalDate fecha) {
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ProgramaLealtad getProgramaLealtad() {
        return programaLealtad;
    }

    public void setProgramaLealtad(ProgramaLealtad programaLealtad) {
        this.programaLealtad = programaLealtad;
    }
}

