package com.fooddrive.app.Ajustes.ProgramaLealtad.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {
    List<Cupon> findByActivoTrue();
}