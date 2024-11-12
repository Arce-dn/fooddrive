package com.fooddrive.app.Ajustes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fooddrive.app.seguridad.Service.UserService;

@Controller
public class AjustesController {

    @Autowired
    private UserService userService;

    @GetMapping("/Ajustes")
    public String Configuraciones(Model model) {
        model.addAttribute("titulo", "Configuraciones del sistema");
        return "Ajustes/Ajustes"; // Retorna el nombre del archivo HTML sin la extensión
    }

    @GetMapping("/Usuarios")
    public String GestionarUsuarios(Model model) {
        model.addAttribute("titulo", "Asministración de Usuarios");
        model.addAttribute("usuarios", userService.getAllUsers()); // Agregar la lista de usuarios al modelo
        return "Ajustes/verUsuarios"; // Retorna el nombre del archivo HTML sin la extensión
    }
}
