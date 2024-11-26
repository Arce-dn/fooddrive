package com.fooddrive.app.MenuDiario.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.MenuDiario.Repository.MenuDiarioRepository;
import com.fooddrive.app.entity.Menu;

@Service
public class MenuServiceImpl implements IMenuService {
    
    @Autowired
    private MenuDiarioRepository menuDiarioRepository;

	@Override
	public void guardar(Menu menu) {
		menuDiarioRepository.save(menu);

	}
    @Override
    public Menu buscarPorFecha(LocalDate fecha) {
        return menuDiarioRepository.findByFecha(fecha).orElse(null);
    }

	@Override
	public Menu buscarPorId(Long id_menu) {
		
		return menuDiarioRepository.findById(id_menu).orElse(null);
	}

	@Override
	public void eliminar(Long id_menu) {
		menuDiarioRepository.deleteById(id_menu);

	}
}
