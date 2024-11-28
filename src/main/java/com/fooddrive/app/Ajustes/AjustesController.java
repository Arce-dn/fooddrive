package com.fooddrive.app.Ajustes;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAuthority('Administrador')")
public class AjustesController {

    @GetMapping("/Ajustes")
    public String Configuraciones(Model model) {
        model.addAttribute("titulo", "Configuraciones del sistema");
        return "Ajustes/Ajustes"; // Retorna el nombre del archivo HTML sin la extensión
    }


}
