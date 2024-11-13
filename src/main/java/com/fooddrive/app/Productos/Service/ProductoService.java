package com.fooddrive.app.Productos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddrive.app.entity.Producto;

@Service
public interface ProductoService {


    public List<Producto> listarTodos();
    public void guardar(Producto producto);
    public Producto buscarPorId(Long id_producto);
    public void eliminar(Long id_producto);
}
