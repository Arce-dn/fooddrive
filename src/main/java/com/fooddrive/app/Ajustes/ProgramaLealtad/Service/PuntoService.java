package com.fooddrive.app.Ajustes.ProgramaLealtad.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Ajustes.ProgramaLealtad.Repository.PuntoRepository;
import com.fooddrive.app.entity.ConfiguracionPrograma;
import com.fooddrive.app.entity.Punto;
import com.fooddrive.app.entity.User;
import com.fooddrive.app.seguridad.Repository.UserRepository;

@Service
public class PuntoService {

    @Autowired
    private PuntoRepository puntoRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfiguracionProgramaService configuracionProgramaService;

    public void agregarPuntos(User user, int cantidad) {
        ConfiguracionPrograma config = configuracionProgramaService.obtenerConfiguracion();
        if (!config.isPuntosActivos()) {
            throw new RuntimeException("El programa de puntos está desactivado.");
        }

        Punto punto = new Punto();
        punto.setUser(user);
        punto.setCantidad(cantidad);
        punto.setFechaVencimiento(LocalDate.of(LocalDate.now().getYear(), 12, 31)); // Último día del año
        puntoRepository.save(punto);
    }

    public void asignarPuntosAUsuarios() {
        // Obtener todos los usuarios
        List<User> usuarios = userRepository.findAll();
        
        // Iterar sobre los usuarios
        for (User user : usuarios) {
            // Verificar si el usuario ya tiene un registro de puntos
            Optional<Punto> puntoExistente = puntoRepository.findByUser(user);

            if (!puntoExistente.isPresent()) {
                // Si no tiene puntos, crear un nuevo registro con 0 puntos
                Punto nuevoPunto = new Punto();
                nuevoPunto.setUser(user);
                nuevoPunto.setActivo(true);
                nuevoPunto.setCantidad(0); // Puntos iniciales
                nuevoPunto.setFechaVencimiento(LocalDate.of(user.getFechaRegistro().getYear() + 1, 12, 31)); // Puntos vencen el último día del año
                puntoRepository.save(nuevoPunto);
            }
        }
    }

    // Método para obtener el punto por su ID
    public Punto obtenerPuntoPorId(Long id) {
        return puntoRepository.findById(id).orElseThrow(() -> new RuntimeException("Punto no encontrado"));
    }

    // Método para actualizar la cantidad de puntos
    public void actualizarCantidadPuntos(Long puntoId, int cantidadPuntos) {
        Punto punto = obtenerPuntoPorId(puntoId);
        punto.setCantidad(cantidadPuntos);
        puntoRepository.save(punto); // Guardar los cambios en la base de datos
    }

    // Obtener todos los puntos activos
    public List<Punto> obtenerPuntosActivos() {
        return puntoRepository.findAll();
    }

    // Guardar un nuevo punto
    public void guardarPunto(Punto punto) {
        puntoRepository.save(punto);
    }

    // Eliminar un punto
    public void eliminarPunto(Long puntoId) {
        puntoRepository.deleteById(puntoId);
    }
}
