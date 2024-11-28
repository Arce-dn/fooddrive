package com.fooddrive.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_cliente", nullable = false)
    private User cliente; // Cliente que realiza el pedido

    @ManyToOne
    @JoinColumn(name = "id_repartidor", nullable = true)
    private User repartidor; // Repartidor asignado

    @Column(nullable = false)
    private LocalDate fechaPedido;

    @Column(nullable = false)
    private String estado; //  "Pendiente", "Preparado", "Entregado"

    @Column(nullable = false)
    private Double total; // Total del pedido

    @Column(nullable = false)
    private String direccionEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getCliente() {
        return cliente;
    }
    public void setCliente(User cliente) {
        this.cliente = cliente;
    }
    public User getRepartidor() {
        return repartidor;
    }
    public void setRepartidor(User repartidor) {
        this.repartidor = repartidor;
    }
    public LocalDate getFechaPedido() {
        return fechaPedido;
    }
    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public List<DetallePedido> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
}
