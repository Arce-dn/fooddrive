package com.fooddrive.app.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha; // Fecha del menú

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario; // Usuario encargado del menú

    // Relación uno a muchos con DetalleMenú
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleMenu> detalles;

    public Menu() {}

    public Menu(LocalDate fecha, User usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public List<DetalleMenu> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleMenu> detalles) {
        this.detalles = detalles;
    }
}
