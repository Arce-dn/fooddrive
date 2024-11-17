package com.fooddrive.app.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "puntos")
public class Punto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDate fechaVencimiento; // Último día del año

    @Column(nullable = false)
    private boolean activo; // Para desactivar puntos vencidos

    // Constructor vacío
    public Punto() {
    }

    // Constructor completo
    public Punto(User user, Integer cantidad, LocalDate fechaVencimiento, boolean activo) {
        this.user = user;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Método para verificar si los puntos están vencidos
    public boolean estanVencidos() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    // Método para desactivar puntos vencidos
    public void actualizarEstado() {
        if (estanVencidos()) {
            this.activo = false;
        }
    }

    @Override
    public String toString() {
        return "Punto{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", cantidad=" + cantidad +
                ", fechaVencimiento=" + fechaVencimiento +
                ", activo=" + activo +
                '}';
    }
}
