package com.fooddrive.app.Productos;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fooddrive.app.entity.Producto;
import com.fooddrive.app.seguridad.Service.ProductoService;



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
