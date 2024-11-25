package com.fooddrive.app.MenuDiario.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.Menu;
@Repository
public interface MenuDiarioRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByFecha(LocalDate fecha);
}