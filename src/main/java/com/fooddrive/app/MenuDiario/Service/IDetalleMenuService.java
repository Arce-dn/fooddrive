package com.fooddrive.app.MenuDiario.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddrive.app.entity.DetalleMenu;
import com.fooddrive.app.entity.Menu;
@Service
public interface IDetalleMenuService {

    public List<DetalleMenu> listarPorMenu(Menu menu);
    public void guardar(DetalleMenu menu);
    public DetalleMenu buscarPorId(Long id);
    public void eliminar(Long id);
}
