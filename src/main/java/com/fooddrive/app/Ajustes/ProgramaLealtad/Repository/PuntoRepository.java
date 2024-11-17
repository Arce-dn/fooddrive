package com.fooddrive.app.Ajustes.ProgramaLealtad.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.Punto;
import com.fooddrive.app.entity.User;

@Repository
public interface PuntoRepository extends JpaRepository<Punto, Long> {
    Optional<Punto> findByUser(User user);
}
