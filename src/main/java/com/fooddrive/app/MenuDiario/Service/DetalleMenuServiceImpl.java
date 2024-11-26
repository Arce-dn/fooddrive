package com.fooddrive.app.MenuDiario.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.MenuDiario.Repository.DetalleMenuRepository;
import com.fooddrive.app.entity.DetalleMenu;
import com.fooddrive.app.entity.Menu;
@Service
public class DetalleMenuServiceImpl implements IDetalleMenuService {
    
    @Autowired
    private DetalleMenuRepository detalleMenuRepository;

    @Override
	public List<DetalleMenu> listarPorMenu(Menu menu) {		
    
		return (List<DetalleMenu>) detalleMenuRepository.findByMenu(menu);
	}

	@Override
	public void guardar(DetalleMenu detalleMenu) {
		detalleMenuRepository.save(detalleMenu);

	}

	@Override
	public DetalleMenu buscarPorId(Long id_detalle) {
		
		return detalleMenuRepository.findById(id_detalle).orElse(null);
	}

	@Override
	public void eliminar(Long id_detalle) {
		detalleMenuRepository.deleteById(id_detalle);

	}
}
