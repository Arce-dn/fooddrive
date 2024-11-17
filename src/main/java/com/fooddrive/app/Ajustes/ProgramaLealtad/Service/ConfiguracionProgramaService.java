package com.fooddrive.app.Ajustes.ProgramaLealtad.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Ajustes.ProgramaLealtad.Repository.ConfiguracionProgramaRepository;
import com.fooddrive.app.entity.ConfiguracionPrograma;

import jakarta.transaction.Transactional;

@Service
public class ConfiguracionProgramaService {

    @Autowired
    private ConfiguracionProgramaRepository configuracionProgramaRepository;

    public ConfiguracionPrograma obtenerConfiguracion() {
        return configuracionProgramaRepository.findById(1L).orElseGet(() -> {
            // Crear una nueva configuración predeterminada si no existe
            ConfiguracionPrograma nuevaConfiguracion = new ConfiguracionPrograma();
            nuevaConfiguracion.setId(1L); // Asegúrate de que sea el mismo ID
            nuevaConfiguracion.setPuntosActivos(false); // Valores predeterminados
            nuevaConfiguracion.setCuponesActivos(false);
            return configuracionProgramaRepository.save(nuevaConfiguracion);
        });
    }

    @Transactional
    public void actualizarConfiguracion(boolean puntosActivos, boolean cuponesActivos) {
        ConfiguracionPrograma configuracion = obtenerConfiguracion();
        configuracion.setPuntosActivos(puntosActivos);
        configuracion.setCuponesActivos(cuponesActivos);
        configuracionProgramaRepository.save(configuracion);
    }
}
