package com.fooddrive.app.MenuDiario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.DetalleMenu;
import com.fooddrive.app.entity.Menu;
@Repository
public interface DetalleMenuRepository extends JpaRepository<DetalleMenu, Long>{
    List<DetalleMenu> findByMenu(Menu menu);
}
