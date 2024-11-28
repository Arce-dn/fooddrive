package com.fooddrive.app.Pedidos.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Pedidos.Repository.PedidoRepository;
import com.fooddrive.app.entity.Pedido;
import com.fooddrive.app.entity.User;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Override
    public void guardar(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public void eliminar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
            throw new RuntimeException("El pedido con ID " + id + " no existe");
        }
    }

    @Override
    public List<Pedido> listarPorCliente(User cliente) {
        return pedidoRepository.findByCliente(cliente);
    }

    @Override
    public List<Pedido> listarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }
}
