package com.fooddrive.app.Productos.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Productos.Repository.ProductoRepository;
import com.fooddrive.app.entity.Producto;


@Service
public class ProductoServiseimpl implements ProductoService{

    @Autowired
	private ProductoRepository productoRepository;


	@Override
	public List<Producto> listarTodos() {		
		return (List<Producto>) productoRepository.findAll();
	}

	@Override
	public void guardar(Producto producto) {
		productoRepository.save(producto);

	}

	@Override
	public Producto buscarPorId(Long id_producto) {
		
		return productoRepository.findById(id_producto).orElse(null);
	}

	@Override
	public void eliminar(Long id_producto) {
		productoRepository.deleteById(id_producto);

	}

}
