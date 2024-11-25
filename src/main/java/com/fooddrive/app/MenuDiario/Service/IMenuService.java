package com.fooddrive.app.MenuDiario.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.fooddrive.app.entity.Menu;
@Service
public interface IMenuService {
    
    public Menu buscarPorFecha(LocalDate fecha);
    public void guardar(Menu menu);
    public Menu buscarPorId(Long id);
    public void eliminar(Long id);
}
