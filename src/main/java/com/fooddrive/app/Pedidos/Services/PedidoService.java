package com.fooddrive.app.Pedidos.Services;

import java.util.List;

import com.fooddrive.app.entity.Pedido;

import com.fooddrive.app.entity.User;


public interface PedidoService {
    List<Pedido> listarTodos();
    Pedido buscarPorId(Long id);
    void guardar(Pedido pedido);
    void eliminar(Long id);
    List<Pedido> listarPorCliente(User cliente);
    List<Pedido> listarPorEstado(String estado);
}

