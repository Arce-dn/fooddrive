package com.fooddrive.app.Productos;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddrive.app.MenuDiario.Service.IDetalleMenuService;
import com.fooddrive.app.MenuDiario.Service.IMenuService;
import com.fooddrive.app.Productos.Service.CategoriaService;
import com.fooddrive.app.Productos.Service.ProductoService;
import com.fooddrive.app.entity.Categoria;
import com.fooddrive.app.entity.DetalleMenu;
import com.fooddrive.app.entity.Menu;
import com.fooddrive.app.entity.Producto;

@Controller
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/producto")
    public String listarProducto(Model model) {
        // Listado de productos para mostrar en la tabla, incluyendo imágenes en Base64
        List<Producto> listadoProducto = productoService.listarTodos();
    
        // Añadimos los datos al modelo
        model.addAttribute("titulo", "Gestión de Productos");
        model.addAttribute("producto", listadoProducto);
    
        // Creación de un nuevo producto para el formulario
        Producto nuevoProducto = new Producto();
        model.addAttribute("nuevoProducto", nuevoProducto);
    
        // Creación de una nueva categoría para el formulario
        Categoria nuevaCategoria = new Categoria();
        model.addAttribute("nuevaCategoria", nuevaCategoria);
    
        // Lista de categorías para el campo de selección en el formulario
        List<Categoria> listaCategoria = categoriaService.ListaCategoria();
        model.addAttribute("categoria", listaCategoria);
    
        return "/producto/listar";
    }

    @PostMapping("/producto/save")
    public String guardar(@ModelAttribute Producto producto,
                        @RequestParam("imagenBase64") String imagenBase64, 
                        RedirectAttributes redirectAttributes) {
        try {
            if (imagenBase64 != null && !imagenBase64.isEmpty()) {
                // Convertir la cadena base64 a un arreglo de bytes
                byte[] imagenBytes = Base64.getDecoder().decode(imagenBase64);
                producto.setImagen(imagenBytes);
            }

            // Guardar el producto
            productoService.guardar(producto);
            
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
            return "redirect:/producto";
        } catch (Exception e) {
            // Manejar excepciones
            redirectAttributes.addFlashAttribute("error", "Error al procesar la imagen");
            return "redirect:/producto";
        }
    }
    @PostMapping("/producto/categoria/save")
    public String guardar(Categoria categoria){
        categoriaService.guardar(categoria);
        return "redirect:/producto";
    }

    @GetMapping("/producto/editar/{id_producto}") 
    public String editar(@PathVariable("id_producto") Long idProducto, Model model) {

        if (productoService == null) {
            // Manejar el caso donde el usuario no exista
            model.addAttribute("error", "El usuario no existe.");
            return "redirect:/producto";
        }
        // Obtener el producto por ID y cargar la lista de categorías
        Producto editarProducto = productoService.buscarPorId(idProducto);
        
        // Agregar los datos necesarios en el modelo para que la tabla y el formulario se mantengan visibles
        List<Producto> listadoProducto = productoService.listarTodos();
        List<Categoria> listaCategoria = categoriaService.ListaCategoria();
        

        // Pasar los datos al modelo para el formulario de edición
        model.addAttribute("titulo", "Formulario: editar producto");
        model.addAttribute("nuevoProducto", editarProducto);  // Se usa el mismo nombre del formulario
        model.addAttribute("producto", listadoProducto);
        model.addAttribute("categoria", listaCategoria);
        return "producto/editarProducto";  // Mostrar la misma vista con el formulario de edición
    }
    @GetMapping("/producto/delete/{id_producto}") 
    public String eliminar(@PathVariable("id_producto") Long idProducto, Model model) {
        productoService.eliminar(idProducto);
        return "redirect:/producto";  // Mostrar la misma vista con el formulario de edición
    }

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IDetalleMenuService detalleMenuService;


    @GetMapping("/productos/buscar/{id_producto}")
    @ResponseBody
    public Producto buscarProductoPorId(@PathVariable Long id_producto) {
        // Obtener el menú de hoy usando la fecha del sistema
        Menu menuDiario = menuService.buscarPorFecha(LocalDate.now());

        // Si no se encuentra el menú de hoy, devolver null
        if (menuDiario == null) {
            return null;
        }

        // Obtener la lista de productos en el menú de hoy
        List<DetalleMenu> detallesMenu = detalleMenuService.listarPorMenu(menuDiario);

        // Buscar el producto por ID en los productos del menú de hoy
        for (DetalleMenu detalle : detallesMenu) {
            if (detalle.getProducto().getId_producto().equals(id_producto)) {
                // Si el producto se encuentra en el menú de hoy, devolver el producto
                return detalle.getProducto();
            }
        }

        // Si el producto no está en el menú de hoy, devolver null
        return null;
    }
}
