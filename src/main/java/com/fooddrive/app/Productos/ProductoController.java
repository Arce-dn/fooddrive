package com.fooddrive.app.Productos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fooddrive.app.Productos.Service.CategoriaService;
import com.fooddrive.app.Productos.Service.ProductoService;
import com.fooddrive.app.entity.Categoria;
import com.fooddrive.app.entity.Producto;

@Controller
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/producto")
    public String listarProducto(Model model){
        // Listado de productos para mostrar en la tabla
        List<Producto> listadoProducto = productoService.listarTodos();
        model.addAttribute("titulo", "Gestión de Productos");
        model.addAttribute("producto", listadoProducto);

        // Creación de un nuevo producto para el formulario
        Producto nuevoProducto = new Producto();
        model.addAttribute("nuevoProducto", nuevoProducto);

        // Lista de categorías para el campo de selección en el formulario
        List<Categoria> listaCategoria = categoriaService.ListaCategoria();
        model.addAttribute("categoria", listaCategoria);

        
        return "/producto/listar";
    }

   @PostMapping("/producto/save")
    public String guardar(Producto producto) {
        productoService.guardar(producto);
        return "redirect:/producto";  // Redirigir a la página de listado después de guardar
    }

    @GetMapping("/producto/edit/{id_producto}") 
    public String editar(@PathVariable("id_producto") Long idProducto, Model model) {
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
        return "/producto/listar";  // Mostrar la misma vista con el formulario de edición
    }
    @GetMapping("/producto/delete/{id_producto}") 
    public String eliminar(@PathVariable("id_producto") Long idProducto, Model model) {
        productoService.eliminar(idProducto);
        return "redirect:/producto";  // Mostrar la misma vista con el formulario de edición
    }

    // @GetMapping("/producto")
    // public String listarProducto(Model model){
    //     List<Producto> ListadoProducto = productoService.listarTodos();
    //     model.addAttribute("titulo", "Lista de Producto");
    //     model.addAttribute("producto", ListadoProducto);

    //     Producto productoo = new Producto();
    //     List<Categoria> listaCategoria = categoriaService.ListaCategoria();
    //     model.addAttribute("titulo", "Formulario: nuevo categoria" );
    //     model.addAttribute("productoo", productoo);
    //     model.addAttribute("categoria", listaCategoria);

    //     return "/producto/listar";
    // }

    //  @PostMapping("/producto/{id_producto}") 
    //  public String editar(@PathVariable("id_producto") Long idProducto, Model model){

    //      Producto editarProducto = productoService.buscarPorId(idProducto);
    //      List<Categoria> listaCategoria = categoriaService.ListaCategoria();

    //      model.addAttribute("titulo", "Formulario: editar producto" );
    //      model.addAttribute("editarProducto", editarProducto);
    //      model.addAttribute("categoria", listaCategoria);
    //      return "redirect:/producto";
    //  }
}
