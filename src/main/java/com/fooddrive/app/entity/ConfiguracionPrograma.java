package com.fooddrive.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "configuracion_programa")
public class ConfiguracionPrograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean puntosActivos;

    @Column(nullable = false)
    private boolean cuponesActivos;

    // Constructor vacío
    public ConfiguracionPrograma() {
    }

    // Constructor con parámetros
    public ConfiguracionPrograma(boolean puntosActivos, boolean cuponesActivos) {
        this.puntosActivos = puntosActivos;
        this.cuponesActivos = cuponesActivos;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPuntosActivos() {
        return puntosActivos;
    }

    public void setPuntosActivos(boolean puntosActivos) {
        this.puntosActivos = puntosActivos;
    }

    public boolean isCuponesActivos() {
        return cuponesActivos;
    }

    public void setCuponesActivos(boolean cuponesActivos) {
        this.cuponesActivos = cuponesActivos;
    }

    @Override
    public String toString() {
        return "ConfiguracionPrograma{" +
                "id=" + id +
                ", puntosActivos=" + puntosActivos +
                ", cuponesActivos=" + cuponesActivos +
                '}';
    }
}
