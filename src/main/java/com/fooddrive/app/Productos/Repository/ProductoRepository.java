package com.fooddrive.app.Productos.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddrive.app.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    // Optional<Producto> findByName(String nombre);
}
