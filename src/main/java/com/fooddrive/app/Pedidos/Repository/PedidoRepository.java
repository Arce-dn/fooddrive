package com.fooddrive.app.Pedidos.Repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddrive.app.entity.Pedido;
import com.fooddrive.app.entity.User;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente(User cliente); // Buscar pedidos de un cliente
    List<Pedido> findByEstado(String estado); // Buscar pedidos por estado
    List<Pedido> findByRepartidor(User repartidor);
}
