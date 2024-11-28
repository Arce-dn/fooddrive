package com.fooddrive.app.Pedidos.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Pedidos.Repository.DetallePedidoRepository;
import com.fooddrive.app.entity.DetallePedido;
import com.fooddrive.app.entity.Pedido;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Override
    public List<DetallePedido> listarPorPedido(Pedido pedido) {
        return detallePedidoRepository.findByPedido(pedido);
    }

    @Override
    public void guardar(DetallePedido detallePedido) {
        detallePedidoRepository.save(detallePedido);
    }

    @Override
    public void eliminar(Long id) {
        if (detallePedidoRepository.existsById(id)) {
            detallePedidoRepository.deleteById(id);
        } else {
            throw new RuntimeException("El detalle con ID " + id + " no existe");
        }
    }
}
