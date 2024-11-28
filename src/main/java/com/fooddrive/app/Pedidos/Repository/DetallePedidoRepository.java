package com.fooddrive.app.Pedidos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.DetallePedido;
import com.fooddrive.app.entity.Pedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedido(Pedido pedido); // Buscar detalles por pedido
}
