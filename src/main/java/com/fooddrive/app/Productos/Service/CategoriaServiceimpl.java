package com.fooddrive.app.Productos.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.Productos.Repository.CategoriaRepository;
import com.fooddrive.app.entity.Categoria;

@Service
public class CategoriaServiceimpl implements CategoriaService{
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> ListaCategoria() {
        
        return (List<Categoria>) categoriaRepository.findAll();
    }
}
