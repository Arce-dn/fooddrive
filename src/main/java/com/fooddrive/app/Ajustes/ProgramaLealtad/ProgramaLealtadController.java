package com.fooddrive.app.Ajustes.ProgramaLealtad;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddrive.app.Ajustes.ProgramaLealtad.Service.ConfiguracionProgramaService;
import com.fooddrive.app.Ajustes.ProgramaLealtad.Service.CuponService;
import com.fooddrive.app.Ajustes.ProgramaLealtad.Service.PuntoService;
import com.fooddrive.app.entity.ConfiguracionPrograma;
import com.fooddrive.app.entity.Cupon;
import com.fooddrive.app.entity.Punto;
import com.fooddrive.app.seguridad.Service.UserService;

@Controller
@RequestMapping("/ProgramaLealtad")

public class ProgramaLealtadController {

    @Autowired
    private ConfiguracionProgramaService configuracionProgramaService;
    @Autowired
    private PuntoService puntoService;
    @Autowired
    private UserService userService;
    @Autowired
    private CuponService cuponService;

    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public String verConfiguracion(Model model) {
        model.addAttribute("configuracion", configuracionProgramaService.obtenerConfiguracion());
        model.addAttribute("titulo", "Programa de Lealtad");
        return "Ajustes/ProgramaLealtad/configuracionesPrograma";
    }

    @PostMapping("/actualizar")
    public String actualizarConfiguracion(@RequestParam boolean puntosActivos, @RequestParam boolean cuponesActivos) {
        configuracionProgramaService.actualizarConfiguracion(puntosActivos, cuponesActivos);
        return "redirect:/ProgramaLealtad";
    }

    @GetMapping("/Puntos")
    public String verPuntos(Model model) {
        puntoService.asignarPuntosAUsuarios();
        model.addAttribute("puntos", puntoService.obtenerPuntosActivos());
        model.addAttribute("usuarios", userService.getAllUsers()); // Para asignar puntos a los usuarios
        model.addAttribute("titulo", "Gestión de puntos de lealtad");
        return "Ajustes/ProgramaLealtad/verPuntos";
    }

    @GetMapping("/Puntos/editar/{id}")
    public String editarPuntos(@PathVariable("id") Long id, Model model) {
        Punto punto = puntoService.obtenerPuntoPorId(id);
        model.addAttribute("punto", punto);
        return "editarPuntos"; // Nombre de la vista
    }

    @PostMapping("/Puntos/actualizar")
    public String actualizarPuntos(@RequestParam("puntoId") Long puntoId,
                                    @RequestParam("cantidadPuntos") int cantidadPuntos, RedirectAttributes redirectAttributes) {
        puntoService.actualizarCantidadPuntos(puntoId, cantidadPuntos);
        redirectAttributes.addFlashAttribute("success", "Puntos modificados correctamente.");
        return "redirect:/ProgramaLealtad/Puntos"; // Redirigir a la lista de puntos después de la actualización
    }

    @GetMapping("/Cupones")
    public String verCupones(Model model) {
        model.addAttribute("cupones", cuponService.obtenerCuponesActivos());
        model.addAttribute("usuarios", userService.getAllUsers()); // Para asignar cupones a los usuarios
        model.addAttribute("titulo", "Gestión de Cupones");
        return "Ajustes/ProgramaLealtad/cupones";
    }

        // Crear nuevo cupón
    @PostMapping("/Cupones/crear")
    public String crearCupon(@RequestParam Long userId, @RequestParam String codigo, @RequestParam Double descuento, @RequestParam String fechaVencimiento, RedirectAttributes redirectAttributes) {
        
        // Obtén la configuración del programa
        ConfiguracionPrograma configuracion = configuracionProgramaService.obtenerConfiguracion();

        // Verifica si la creacion de cupones están activa
        if (!configuracion.isCuponesActivos()) {
            redirectAttributes.addFlashAttribute("error", "La creación de cupones está deshabilitada.");
            return "redirect:/ProgramaLealtad/Cupones";
        }

        LocalDate fechaVenc = LocalDate.parse(fechaVencimiento);
        Cupon nuevoCupon = new Cupon();
        nuevoCupon.setUser(userService.getUserById(userId));
        nuevoCupon.setCodigo(codigo);
        nuevoCupon.setDescuento(descuento);
        nuevoCupon.setFechaVencimiento(fechaVenc);
        nuevoCupon.setActivo(true);
        cuponService.guardarCupon(nuevoCupon);
        redirectAttributes.addFlashAttribute("success","El cupón se ha acreditado éxitosamente.");
        return "redirect:/ProgramaLealtad/Cupones";
    }

    @PostMapping("/Cupones/eliminar")
    public String eliminarCupon(@RequestParam Long cuponId,RedirectAttributes redirectAttributes) {
        cuponService.eliminarCupon(cuponId);
        redirectAttributes.addFlashAttribute("error","El cupón se ha eliminado éxitosamente.");
        return "redirect:/ProgramaLealtad/Cupones";
    }
}
