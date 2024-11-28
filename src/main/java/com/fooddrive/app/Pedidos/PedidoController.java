package com.fooddrive.app.Pedidos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fooddrive.app.MenuDiario.Service.DetalleMenuServiceImpl;
import com.fooddrive.app.MenuDiario.Service.MenuServiceImpl;
import com.fooddrive.app.Pedidos.Services.DetallePedidoService;
import com.fooddrive.app.Pedidos.Services.PedidoService;
import com.fooddrive.app.Productos.Service.ProductoService;
import com.fooddrive.app.entity.DetalleMenu;
import com.fooddrive.app.entity.DetallePedido;
import com.fooddrive.app.entity.Menu;
import com.fooddrive.app.entity.Pedido;
import com.fooddrive.app.entity.Producto;
import com.fooddrive.app.entity.User;
import com.fooddrive.app.seguridad.Service.UserService;



@Controller
@RequestMapping("/pedidos")
public class PedidoController {
@Autowired
    private ProductoService productoService;

    @Autowired
    private DetalleMenuServiceImpl detalleMenuService;

    @Autowired
    private MenuServiceImpl menuService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UserService userService;
    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping("/crear")
    public String mostrarFormularioPedido(Authentication authentication, Model model) {
        // Obtener el menú diario
        Menu menu = menuService.buscarPorFecha(LocalDate.now());
        if (menu == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay menú disponible para el día de hoy");
        }

        // Productos del menú diario
        List<DetalleMenu> detallesMenu = detalleMenuService.listarPorMenu(menu);

        // Atributos para el formulario
        model.addAttribute("titulo", "Registrar Pedido");
        model.addAttribute("productosMenu", detallesMenu);
        model.addAttribute("pedido", new Pedido());
        return "/Pedidos/crearPedido";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute Pedido pedido,
                                 @RequestParam("productos") List<Long> productoIds,
                                 @RequestParam("cantidades") List<Integer> cantidades,
                                 @RequestParam("direccionEntrega") String direccionEntrega,
                                 Authentication authentication) {
        // Obtener el cliente autenticado
        String nombreUsuario = authentication.getName();
        User cliente = userService.getUserByUsername(nombreUsuario).orElseThrow(
                () -> new RuntimeException("Cliente no encontrado"));

        // Configurar información básica del pedido
        pedido.setCliente(cliente);
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstado("Pendiente");
        pedido.setDireccionEntrega(direccionEntrega);

        // Calcular totales y asociar detalles
        double total = 0.0;
        for (int i = 0; i < productoIds.size(); i++) {
            Long productoId = productoIds.get(i);
            int cantidad = cantidades.get(i);

            Producto producto = productoService.buscarPorId(productoId);
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado");
            }

            double subtotal = producto.getPrecio() * cantidad;
            total += subtotal;

            // Crear detalle de pedido
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(subtotal);

            pedido.getDetalles().add(detalle);
        }

        pedido.setTotal(total);

        // Guardar pedido
        pedidoService.guardar(pedido);

        return "redirect:/pedidos/lista";
    }

    @GetMapping("/lista")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.listarTodos();
        model.addAttribute("titulo", "Listado de Pedidos");
        model.addAttribute("pedidos", pedidos);
        return "/Pedidos/listaPedidos";
    }

    // Método para ver el detalle de un pedido
    @GetMapping("/detalle/{id}")
    public String verDetallePedido(@PathVariable("id") Long idPedido, Model model) {
        // Obtener el pedido por su ID
        Pedido pedido = pedidoService.buscarPorId(idPedido);
            if (pedido == null) {
                throw new RuntimeException("Pedido no encontrado");
            }

        // Obtener los detalles del pedido
        List<DetallePedido> detalles = detallePedidoService.listarPorPedido(pedido);

        // Enviar el pedido y sus detalles a la vista
        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detalles);
        
        return "Pedidos/detallePedido"; // Nombre de la vista Thymeleaf
    }
}
