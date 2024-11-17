package com.fooddrive.app.Ajustes.ProgramaLealtad.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.ConfiguracionPrograma;

@Repository
public interface ConfiguracionProgramaRepository extends JpaRepository<ConfiguracionPrograma, Long> {}
