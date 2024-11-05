package com.fooddrive.app.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fooddrive.app.entity.Role;
import com.fooddrive.app.entity.User;
import com.fooddrive.app.seguridad.Repository.RoleRepository;
import com.fooddrive.app.seguridad.Service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("titulo", "Registrarse en Fooddrive");
        return "Seguridad/register"; // Asegúrate de que esta vista exista
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        Role defaultRole = roleRepository.findByName("Encargado del Menú").orElseThrow();
        user.getRoles().add(defaultRole);
        userService.save(user);
        return "redirect:/login"; // Redirige después del registro
    }
}
