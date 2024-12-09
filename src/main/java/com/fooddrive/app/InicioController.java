package com.fooddrive.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping({"/Inicio", "/"})
    public String index(Model model){
        model.addAttribute("titulo", "FOOD-DRIVE");
        return "home";
    }

    @GetMapping({"/publico"})
    public String inicioPublico(Model model){
        model.addAttribute("titulo", "FOOD-DRIVE");
        return "inicioPublico";
    }

    @GetMapping("/login") // Ruta para la página de inicio de sesión
    public String login(Model model) {
        model.addAttribute("titulo", "Iniciar Sesión");
        return "Seguridad/login"; // Retorna el nombre del archivo HTML sin la extensión
    }
}