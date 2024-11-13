package com.fooddrive.app.Productos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fooddrive.app.Productos.Service.ProductoService;
import com.fooddrive.app.entity.Producto;



@Controller
public class ProductoController {

    
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto")
    public String listarProducto(Model model){
        List<Producto> ListadoProducto = productoService.listarTodos();

        model.addAttribute("titulo", "Lista de Producto");
        model.addAttribute("producto", ListadoProducto);
        return "/producto/listar";
    }
}
