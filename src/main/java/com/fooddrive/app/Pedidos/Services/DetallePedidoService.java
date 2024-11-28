package com.fooddrive.app.Pedidos.Services;

import java.util.List;

import com.fooddrive.app.entity.DetallePedido;
import com.fooddrive.app.entity.Pedido;

public interface DetallePedidoService {
    List<DetallePedido> listarPorPedido(Pedido pedido);
    void guardar(DetallePedido detallePedido);
    void eliminar(Long id);
}
