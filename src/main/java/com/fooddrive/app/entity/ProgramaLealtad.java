package com.fooddrive.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programa_lealtad")
public class ProgramaLealtad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgramaLealtad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private User usuario;

    @Column(nullable = false)
    private int puntosAcumulados;

    @OneToMany(mappedBy = "programaLealtad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Puntos> historialPuntos = new ArrayList<>();

    @OneToMany(mappedBy = "programaLealtad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cupon> cuponesDisponibles = new ArrayList<>();

    @Column(nullable = false)
    private String estadoPrograma; // "Activo" o "Inactivo"

    private LocalDate fechaExpiracionPuntos;

    @Column(nullable = false)
    private LocalDateTime fechaUltimaActualizacion;

    // Constructor por defecto
    public ProgramaLealtad() {}

    // Constructor personalizado
    public ProgramaLealtad(User usuario) {
        this.usuario = usuario;
        this.puntosAcumulados = 0;
        this.estadoPrograma = "Activo";
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getIdProgramaLealtad() {
        return idProgramaLealtad;
    }

    public void setIdProgramaLealtad(Long idProgramaLealtad) {
        this.idProgramaLealtad = idProgramaLealtad;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public List<Puntos> getHistorialPuntos() {
        return historialPuntos;
    }

    public void setHistorialPuntos(List<Puntos> historialPuntos) {
        this.historialPuntos = historialPuntos;
    }

    public List<Cupon> getCuponesDisponibles() {
        return cuponesDisponibles;
    }

    public void setCuponesDisponibles(List<Cupon> cuponesDisponibles) {
        this.cuponesDisponibles = cuponesDisponibles;
    }

    public String getEstadoPrograma() {
        return estadoPrograma;
    }

    public void setEstadoPrograma(String estadoPrograma) {
        this.estadoPrograma = estadoPrograma;
    }

    public LocalDate getFechaExpiracionPuntos() {
        return fechaExpiracionPuntos;
    }

    public void setFechaExpiracionPuntos(LocalDate fechaExpiracionPuntos) {
        this.fechaExpiracionPuntos = fechaExpiracionPuntos;
    }

    public LocalDateTime getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }
}
