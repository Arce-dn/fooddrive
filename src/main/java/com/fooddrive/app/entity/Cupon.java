package com.fooddrive.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cupones")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCupon;

    @Column(nullable = false, unique = true)
    private String codigoCupon;

    @Column(nullable = false)
    private double descuento;

    @Column(nullable = false)
    private LocalDate fechaExpiracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_lealtad_id", nullable = false)
    private ProgramaLealtad programaLealtad;

    // Constructor por defecto
    public Cupon() {}

    // Constructor personalizado
    public Cupon(String codigoCupon, double descuento, LocalDate fechaExpiracion) {
        this.codigoCupon = codigoCupon;
        this.descuento = descuento;
        this.fechaExpiracion = fechaExpiracion;
    }

    // Getters y Setters
    public Long getIdCupon() {
        return idCupon;
    }

    public void setIdCupon(Long idCupon) {
        this.idCupon = idCupon;
    }

    public String getCodigoCupon() {
        return codigoCupon;
    }

    public void setCodigoCupon(String codigoCupon) {
        this.codigoCupon = codigoCupon;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public ProgramaLealtad getProgramaLealtad() {
        return programaLealtad;
    }

    public void setProgramaLealtad(ProgramaLealtad programaLealtad) {
        this.programaLealtad = programaLealtad;
    }
}
