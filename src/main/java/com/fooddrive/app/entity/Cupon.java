package com.fooddrive.app.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "cupones")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private Double descuento; // Puede ser un porcentaje o un valor fijo

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    private boolean activo; // Indica si el cupón está activo

    // Constructor vacío
    public Cupon() {
    }

    // Constructor completo
    public Cupon(User user, String codigo, Double descuento, LocalDate fechaVencimiento, boolean activo) {
        this.user = user;
        this.codigo = codigo;
        this.descuento = descuento;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
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

    // Método para verificar si el cupón está vencido
    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    // Método para actualizar el estado activo según la fecha de vencimiento
    public void actualizarEstado() {
        if (estaVencido()) {
            this.activo = false;
        }
    }

    @Override
    public String toString() {
        return "Cupon{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", codigo='" + codigo + '\'' +
                ", descuento=" + descuento +
                ", fechaVencimiento=" + fechaVencimiento +
                ", activo=" + activo +
                '}';
    }
}

