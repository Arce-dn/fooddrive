package com.fooddrive.app.Ajustes.ProgramaLealtad.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Ajustes.ProgramaLealtad.Repository.CuponRepository;
import com.fooddrive.app.entity.ConfiguracionPrograma;
import com.fooddrive.app.entity.Cupon;
import com.fooddrive.app.entity.User;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private ConfiguracionProgramaService configuracionProgramaService;

    public void agregarCupon(User user, String codigo, double descuento, LocalDate fechaVencimiento) {
        ConfiguracionPrograma config = configuracionProgramaService.obtenerConfiguracion();
        if (!config.isCuponesActivos()) {
            throw new RuntimeException("El programa de cupones está desactivado.");
        }

        Cupon cupon = new Cupon();
        cupon.setUser(user);
        cupon.setCodigo(codigo);
        cupon.setDescuento(descuento);
        cupon.setFechaVencimiento(fechaVencimiento);
        cupon.setActivo(true);
        cuponRepository.save(cupon);
    }

    // Obtener todos los cupones activos
    public List<Cupon> obtenerCuponesActivos() {
        return cuponRepository.findByActivoTrue();
    }

    // Guardar un nuevo cupón
    public void guardarCupon(Cupon cupon) {
        cuponRepository.save(cupon);
    }

    // Eliminar un cupón
    public void eliminarCupon(Long cuponId) {
        cuponRepository.deleteById(cuponId);
    }
}
