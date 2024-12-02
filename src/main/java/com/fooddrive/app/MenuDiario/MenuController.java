package com.fooddrive.app.MenuDiario;

import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.fooddrive.app.MenuDiario.Service.IDetalleMenuService;
import com.fooddrive.app.MenuDiario.Service.IMenuService;
import com.fooddrive.app.Productos.Service.CategoriaService;
import com.fooddrive.app.Productos.Service.ProductoService;
import com.fooddrive.app.entity.Categoria;
import com.fooddrive.app.entity.Cupon;
import com.fooddrive.app.entity.DetalleMenu;
import com.fooddrive.app.entity.Menu;
import com.fooddrive.app.entity.Producto;
import com.fooddrive.app.entity.Punto;
import com.fooddrive.app.entity.User;
import com.fooddrive.app.seguridad.Service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MenuController {
    
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IDetalleMenuService detalleMenuService;
    @Autowired
    private UserService userService;

    @GetMapping("/MenuDiario")
    public String gestionarMenuDiario(Authentication authentication, Model model){
        // Listado de productos en inventario
        List<Producto> listadoProducto = productoService.listarTodos();
        
        // Lista de categorías para mostrar secciones en menu diario 
        List<Categoria> listaCategoria = categoriaService.ListaCategoria();

        //Buscar menu con la fecha de hoy, si no existe lo crea
        Menu menu = null;
        menu = menuService.buscarPorFecha(LocalDate.now()); //Lo busca
        if(menu == null){//Si es nulo, lo crea
            
            menu = new Menu();
            String nombre = authentication.getName();
            Optional<User> optionalUsuario = userService.getUserByUsername(nombre); //Captura el usuario que inicia el nuevo menu
            User usuario = optionalUsuario.get();

            menu = new Menu(LocalDate.now(), usuario);
            
            menuService.guardar(menu);
        }
        //Busca que detallesMenu corresponden al menu de este dia
        List<DetalleMenu> detallesMenu = detalleMenuService.listarPorMenu(menu);

        //Filtra que productos no tienen un detalle Menu
        List<Producto> productosFiltrados = listadoProducto.stream()
        .filter(producto -> detallesMenu.stream()
                                         .noneMatch(detalle -> detalle.getProducto().getId_producto().equals(producto.getId_producto())))
        .collect(Collectors.toList());


        //ATRIBUTOS Y LISTAS ENVIADAS AL FRONT
        model.addAttribute("titulo", "Gestión de Menu Diario");
        model.addAttribute("productos", productosFiltrados);
        model.addAttribute("categoria", listaCategoria);
        model.addAttribute("productosMenu", detallesMenu);
        model.addAttribute("menuId", menu.getId());
        return "/MenuDiario/menuDiario";
    }

    //Se encarga de crear el detalle menu (MOstrar en menu diario)
    @PostMapping("/MenuDiario/agregar/{idMenu}/{id}")
    public String agregarDetalleMenu(@PathVariable("idMenu") Long idMenu, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Menu menu = menuService.buscarPorId(idMenu);
        Producto producto = productoService.buscarPorId(id);
        DetalleMenu detalleMenu = null;
        detalleMenu = new DetalleMenu(menu, producto, producto.getCantidad());
        detalleMenuService.guardar(detalleMenu); //Guarda DetalleMenu en la DB
        redirectAttributes.addFlashAttribute("success", "El producto se ha agregado al menu con éxito.");

        return "redirect:/MenuDiario";
    }

    //Se encarga de quitar el detalle menu (Quitar de menu diario)
    @PostMapping("/MenuDiario/quitar/{idDetalle}")
    public String quitarDetalleMenu(@PathVariable("idDetalle")Long idDetalle, RedirectAttributes redirectAttributes) {
        
        detalleMenuService.eliminar(idDetalle); //Elimina DetalleMenu de la DB
        redirectAttributes.addFlashAttribute("error", "El producto se ha quitado del menu con éxito.");
        return "redirect:/MenuDiario";
    }

    //Vista Menu diario desde cliente
    @GetMapping("/Menu/{username}")
    public String verMenu(Model model,@PathVariable("username") String username ) {
        Menu menu = null;
        menu = menuService.buscarPorFecha(LocalDate.now()); //Si no encuentra con la fecha de hoy sera nulo
        if(menu == null){
            //Si es nulo procede a mostrar mensaje de no disponible
            model.addAttribute("mensaje", "Menu no dispobnile, favor intenta mas tarde");
            return "/MenuDiario/verMenuCliente";
        }

        // LIsta el Menu del dia, previamente gestionado por el encargado
        List<DetalleMenu> detallesMenu = detalleMenuService.listarPorMenu(menu);

        // Lista de categorías para mostrar secciones en menu diario 
        List<Categoria> listaCategoria = categoriaService.ListaCategoria();

        List<Categoria> categoriasFiltradas = listaCategoria.stream()
        .filter(categoria -> detallesMenu.stream()
                                         .anyMatch(detalle -> detalle.getProducto().getCategoria().getId_categoria().equals(categoria.getId_categoria())))
        .collect(Collectors.toList());


        // Buscar el usuario por username
        Optional<User> optionalUsuario = userService.getUserByUsername(username);
        
        // Verificar si el usuario existe
        if (optionalUsuario.isEmpty()) {
            // Manejar el caso donde el usuario no exista
            model.addAttribute("error", "El usuario no existe.");
            return "redirect:/Usuarios";
        }
        User usuario = optionalUsuario.get(); // Obtener el usuario del Optional
        List<Cupon> cuponesActivos = usuario.getCupones().stream()
                                        .filter(cupon -> cupon.isActivo() && cupon.getFechaVencimiento().isAfter(LocalDate.now())) // Filtra cupones activos y no vencidos
                                        .collect(Collectors.toList());

                                        String direccionUsuario = usuario.getDireccionPrincipal();
                                        if (direccionUsuario == null || direccionUsuario.isEmpty()) {
                                            direccionUsuario = "";
                                        }
        // Filtrar los puntos activos y no vencidos
        int totalPuntos = usuario.getPuntos().stream()
                                .filter(punto -> punto.isActivo() && punto.getFechaVencimiento().isAfter(LocalDate.now()))  // Filtra puntos activos y no vencidos
                                .mapToInt(Punto::getCantidad)  // Suma la cantidad de puntos
                                .sum();
        //Atributos a enviar al modelo
        model.addAttribute("cupones", cuponesActivos);
        model.addAttribute("categoria", categoriasFiltradas);
        model.addAttribute("detallesMenu", detallesMenu);
        model.addAttribute("menuId", menu.getId());
        model.addAttribute("mensaje", "");
        model.addAttribute("direccion", direccionUsuario);
        model.addAttribute("totalPuntos", totalPuntos); 
        model.addAttribute("titulo", "Menú diario");
        return "/MenuDiario/verMenuCliente";
    }
    
}
