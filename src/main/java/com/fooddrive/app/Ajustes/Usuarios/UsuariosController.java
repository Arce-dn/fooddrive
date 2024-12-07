package com.fooddrive.app.Ajustes.Usuarios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddrive.app.entity.Cupon;
import com.fooddrive.app.entity.Punto;
import com.fooddrive.app.entity.Role;
import com.fooddrive.app.entity.User;
import com.fooddrive.app.seguridad.Service.RoleService;
import com.fooddrive.app.seguridad.Service.UserService;

@Controller
public class UsuariosController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/Usuarios")
    public String GestionarUsuarios(Model model) {
        // Obtiene todos los usuarios
        List<User> allUsers = userService.getAllUsers();
        
        // Filtra los usuarios que NO tienen el rol "Cliente"
        List<User> Administrativos = allUsers.stream()
                                                .filter(usuario -> usuario.getRoles().stream()
                                                                        .noneMatch(role -> "Cliente".equals(role.getName())))
                                                .collect(Collectors.toList());

        model.addAttribute("titulo", "Administración de Usuarios");
        model.addAttribute("usuarios", Administrativos); // Agregar la lista de usuarios al modelo
        model.addAttribute("usuario", new User()); // Objeto vacío para el formulario
        model.addAttribute("roles", roleService.getAllRoles()); // Lista de roles para el select
        return "Ajustes/Usuarios/verUsuarios"; // Retorna el nombre del archivo HTML sin la extensión
    }

    @GetMapping("/Usuarios/Clientes")
    public String GestionarClientes(Model model) {
        // Obtiene todos los usuarios
        List<User> allUsers = userService.getAllUsers();
        
        // Filtra los usuarios que tienen el rol "Cliente"
        List<User> clientes = allUsers.stream()
                                    .filter(usuario -> usuario.getRoles().stream()
                                                                .anyMatch(role -> "Cliente".equals(role.getName())))
                                    .collect(Collectors.toList());
        model.addAttribute("titulo", "Administración de Clientes");
        model.addAttribute("usuarios", clientes); // Agregar la lista de usuarios al modelo
        model.addAttribute("usuario", new User()); // Objeto vacío para el formulario
        model.addAttribute("roles", roleService.getAllRoles()); // Lista de roles para el select
        return "Ajustes/Usuarios/verClientes"; // Retorna el nombre del archivo HTML sin la extensión
    }

    @GetMapping("/Usuarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        User usuario = userService.getUserById(id);
        if (usuario == null) {
            // Manejar el caso donde el usuario no exista
            model.addAttribute("error", "El usuario no existe.");
            return "redirect:/Usuarios";
        }
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("usuario", usuario); // Usuario existente para el formulario
        model.addAttribute("roles", roleService.getAllRoles()); // Lista de roles
        return "Ajustes/Usuarios/editarUsuario";
    }

    @PostMapping("/Usuarios/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, @ModelAttribute("usuario") User usuarioActualizado) {
        User usuarioExistente = userService.getUserById(id);
        if (usuarioExistente == null) {
            return "redirect:/Usuarios?error=Usuario no encontrado";
        }

        // Actualizar campos si se reciben valores válidos (no nulos ni vacíos)
        if (usuarioActualizado.getUsername() != null && !usuarioActualizado.getUsername().isEmpty()) {
            usuarioExistente.setUsername(usuarioActualizado.getUsername());
        }

        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuarioExistente.setPassword(usuarioActualizado.getPassword());
        }

        if (usuarioActualizado.getNombreCompleto() != null && !usuarioActualizado.getNombreCompleto().isEmpty()) {
            usuarioExistente.setNombreCompleto(usuarioActualizado.getNombreCompleto());
        }

        if (usuarioActualizado.getEmail() != null && !usuarioActualizado.getEmail().isEmpty()) {
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
        }

        if (usuarioActualizado.getTelefono() != null && !usuarioActualizado.getTelefono().isEmpty()) {
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        }

        if (usuarioActualizado.getDireccionPrincipal() != null && !usuarioActualizado.getDireccionPrincipal().isEmpty()) {
            usuarioExistente.setDireccionPrincipal(usuarioActualizado.getDireccionPrincipal());
        }

        if (usuarioActualizado.getDireccionAlternativa() != null && !usuarioActualizado.getDireccionAlternativa().isEmpty()) {
            usuarioExistente.setDireccionAlternativa(usuarioActualizado.getDireccionAlternativa());
        }

        if (usuarioActualizado.getDisponibilidad() != null && !usuarioActualizado.getDisponibilidad().isEmpty()) {
            usuarioExistente.setDisponibilidad(usuarioActualizado.getDisponibilidad());
        }

        if (usuarioActualizado.getEstado() != null && !usuarioActualizado.getEstado().isEmpty()) {
            usuarioExistente.setEstado(usuarioActualizado.getEstado());
        }

        if (usuarioActualizado.getUltimaFechaAcceso() != null) {
            usuarioExistente.setUltimaFechaAcceso(usuarioActualizado.getUltimaFechaAcceso());
        }

        // Actualizar roles si se reciben
        if (usuarioActualizado.getRoles() != null && !usuarioActualizado.getRoles().isEmpty()) {
            usuarioExistente.setRoles(usuarioActualizado.getRoles());
        }

        // Guardar los cambios en la base de datos
        userService.updateUser(usuarioExistente);
        return "redirect:/Usuarios?success=Usuario actualizado correctamente";
    }

    @PostMapping("/Usuarios/editarDireccion/{id}")
    public String editarDireccion(@PathVariable("id") Long id, @ModelAttribute("usuario") User usuarioActualizado, RedirectAttributes redirectAttributes) {
        User usuarioExistente = userService.getUserById(id);
        String username = usuarioExistente.getUsername();

        // Actualizar campos si se reciben valores válidos (no nulos ni vacíos)
        if (usuarioActualizado.getUsername() != null && !usuarioActualizado.getUsername().isEmpty()) {
            usuarioExistente.setUsername(usuarioActualizado.getUsername());
        }

        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuarioExistente.setPassword(usuarioActualizado.getPassword());
        }

        if (usuarioActualizado.getNombreCompleto() != null && !usuarioActualizado.getNombreCompleto().isEmpty()) {
            usuarioExistente.setNombreCompleto(usuarioActualizado.getNombreCompleto());
        }

        if (usuarioActualizado.getEmail() != null && !usuarioActualizado.getEmail().isEmpty()) {
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
        }

        if (usuarioActualizado.getTelefono() != null && !usuarioActualizado.getTelefono().isEmpty()) {
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        }

        if (usuarioActualizado.getDireccionPrincipal() != null && !usuarioActualizado.getDireccionPrincipal().isEmpty()) {
            usuarioExistente.setDireccionPrincipal(usuarioActualizado.getDireccionPrincipal());
        }

        if (usuarioActualizado.getDireccionAlternativa() != null && !usuarioActualizado.getDireccionAlternativa().isEmpty()) {
            usuarioExistente.setDireccionAlternativa(usuarioActualizado.getDireccionAlternativa());
        }

        if (usuarioActualizado.getDisponibilidad() != null && !usuarioActualizado.getDisponibilidad().isEmpty()) {
            usuarioExistente.setDisponibilidad(usuarioActualizado.getDisponibilidad());
        }

        if (usuarioActualizado.getEstado() != null && !usuarioActualizado.getEstado().isEmpty()) {
            usuarioExistente.setEstado(usuarioActualizado.getEstado());
        }

        if (usuarioActualizado.getUltimaFechaAcceso() != null) {
            usuarioExistente.setUltimaFechaAcceso(usuarioActualizado.getUltimaFechaAcceso());
        }

        // Actualizar roles si se reciben
        if (usuarioActualizado.getRoles() != null && !usuarioActualizado.getRoles().isEmpty()) {
            usuarioExistente.setRoles(usuarioActualizado.getRoles());
        }

        // Guardar los cambios en la base de datos
        userService.updateUser(usuarioExistente);
        redirectAttributes.addFlashAttribute("success", "Información de contacto actualizada.");

        // Verificar si el usuario tiene el rol de cliente
        if (usuarioExistente.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("Cliente"))) {
            redirectAttributes.addFlashAttribute("success", "Información de contacto actualizada.");
            return "redirect:/Usuarios/informacionCliente/" + username;
        }
        
        return "redirect:/Usuarios/informacion/" + username;
    }

    @PostMapping("/Usuarios/editarEstado/{id}")
    public String editarEstadoUsuario(@PathVariable("id") Long id, @RequestParam("estado") String estado) {
        User usuario = userService.getUserById(id);
        if (usuario == null) {
            return "redirect:/Usuarios?error=Usuario no encontrado";
        }

        // Actualiza el estado del usuario
        usuario.setEstado(estado);
        userService.updateUser(usuario); // Método para guardar los cambios

        return "redirect:/Usuarios/editar/{id}";
    }

    @GetMapping("/Usuarios/informacion/{username}")
    public String mostrarInformacion(@PathVariable("username") String username, Model model) {
        // Buscar el usuario por username
        Optional<User> optionalUsuario = userService.getUserByUsername(username);
        
        // Verificar si el usuario existe
        if (optionalUsuario.isEmpty()) {
            // Manejar el caso donde el usuario no exista
            model.addAttribute("error", "El usuario no existe.");
            return "redirect:/Usuarios";
        }
        
        User usuario = optionalUsuario.get(); // Obtener el usuario del Optional
        
        // Filtrar los puntos activos y no vencidos
        int totalPuntos = usuario.getPuntos().stream()
                                .filter(punto -> punto.isActivo() && punto.getFechaVencimiento().isAfter(LocalDate.now()))  // Filtra puntos activos y no vencidos
                                .mapToInt(Punto::getCantidad)  // Suma la cantidad de puntos
                                .sum();

                                // Filtrar los cupones activos y no vencidos
        List<Cupon> cuponesActivos = usuario.getCupones().stream()
                                        .filter(cupon -> cupon.isActivo() && cupon.getFechaVencimiento().isAfter(LocalDate.now())) // Filtra cupones activos y no vencidos
                                        .collect(Collectors.toList());

        model.addAttribute("titulo", "Información de Usuario");
        model.addAttribute("totalPuntos", totalPuntos); 
        model.addAttribute("usuario", usuario); // Usuario existente para el formulario
        model.addAttribute("cupones", cuponesActivos);
        model.addAttribute("roles", roleService.getAllRoles()); // Lista de roles
        return "Ajustes/Usuarios/verInformacion";
    }
    @GetMapping("/Usuarios/informacionCliente/{username}")
    public String mostrarInformacionCliente(@PathVariable("username") String username, Model model) {
        // Buscar el usuario por username
        Optional<User> optionalUsuario = userService.getUserByUsername(username);
        
        // Verificar si el usuario existe
        if (optionalUsuario.isEmpty()) {
            // Manejar el caso donde el usuario no exista
            model.addAttribute("error", "El usuario no existe.");
            return "redirect:/Usuarios";
        }
        
        User usuario = optionalUsuario.get(); // Obtener el usuario del Optional
        
        // Filtrar los puntos activos y no vencidos
        int totalPuntos = usuario.getPuntos().stream()
                                .filter(punto -> punto.isActivo() && punto.getFechaVencimiento().isAfter(LocalDate.now()))  // Filtra puntos activos y no vencidos
                                .mapToInt(Punto::getCantidad)  // Suma la cantidad de puntos
                                .sum();

                                // Filtrar los cupones activos y no vencidos
        List<Cupon> cuponesActivos = usuario.getCupones().stream()
                                        .filter(cupon -> cupon.isActivo() && cupon.getFechaVencimiento().isAfter(LocalDate.now())) // Filtra cupones activos y no vencidos
                                        .collect(Collectors.toList());

        model.addAttribute("titulo", "Información de Usuario");
        model.addAttribute("totalPuntos", totalPuntos); 
        model.addAttribute("usuario", usuario); // Usuario existente para el formulario
        model.addAttribute("cupones", cuponesActivos);
        model.addAttribute("roles", roleService.getAllRoles()); // Lista de roles
        return "Ajustes/Usuarios/verInformacionCliente";
    }

    @PostMapping("/guardarUsuario")
    public String guardarNuevoUsuario(@ModelAttribute("usuario") User usuario) {
        usuario.setPassword("default"); // Contraseña predeterminada
        usuario.setFechaRegistro(LocalDate.now());
        userService.save(usuario); // Guardar usuario con el servicio
        return "redirect:/Usuarios"; // Redirige a la lista de usuarios después de guardar
    }
    
    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        // Llamada al servicio para eliminar el usuario
        userService.eliminarUsuario(id);
        return "redirect:/Usuarios"; // Redirige a la lista de usuarios
    }

    @GetMapping("/Roles")
    public String GestionarRoles(Model model) {
        model.addAttribute("titulo", "Gestionar Roles");
        model.addAttribute("roles", roleService.getAllRoles()); // Agregar la lista de usuarios al modelo
        model.addAttribute("newRole", new Role());
        return "Ajustes/Usuarios/verRoles"; // Retorna el nombre del archivo HTML sin la extensión
    }

    @PostMapping("/añadirRole")
    public String añadirRole(@ModelAttribute("newRole") Role role, RedirectAttributes redirectAttributes){
        roleService.guardarRole(role);
        redirectAttributes.addFlashAttribute("success", "Rol agregado éxitosamente.");
        return "redirect:/Roles";
    }
    
    // Guardar cambios en el rol
    @PostMapping("/update")
    public String updateRole(@ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        roleService.guardarRole(role);
        redirectAttributes.addFlashAttribute("success", "El rol se ha modificado con éxito.");
        return "redirect:/Roles"; // Redirigir a la lista de roles
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            roleService.eliminarRol(id);  // Elimina el rol
            return "redirect:/Roles";  // Redirige a la lista de roles si la eliminación es exitosa
        } catch (Exception e) {
            // Si ocurre un error, se captura y se agrega un mensaje flash de error
            redirectAttributes.addFlashAttribute("error", "Verifique que no haya usuarios con este rol antes de eliminar.");
            return "redirect:/Roles";  // Redirige nuevamente a la lista de roles
        }
    }
}
