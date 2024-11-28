 package com.fooddrive.app.entity;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;

 @Entity
 @Table(name = "producto")
 public class Producto {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id_producto;
     
     private byte[] imagen;
     private String nombre;
     private Double precio;
     private int cantidad;
     private String descripcion;
     private boolean disponibilidad;

     public Producto(){}

     @Autowired
     @ManyToOne
     @JoinColumn(name="id_categoria")
     private Categoria categoria;

     
     public Long getId_producto() {
         return id_producto;
     }
     
     public void setId_producto(Long id_producto) {
         this.id_producto = id_producto;
     }
     
     public byte[] getImagen() {
         return imagen;
     }
     public void setImagen(byte[] imagen) {
         this.imagen = imagen;
     }
     public String getNombre() {
         return nombre;
     }
     public void setNombre(String nombre) {
         this.nombre = nombre;
     }
     public Double getPrecio() {
         return precio;
     }
     public void setPrecio(Double precio) {
         this.precio = precio;
     }
     public int getCantidad() {
         return cantidad;
     }
     public void setCantidad(int cantidad) {
         this.cantidad = cantidad;
     }
     public String getDescripcion() {
         return descripcion;
     }
     public void setDescripcion(String descripcion) {
         this.descripcion = descripcion;
     }
     public boolean isDisponibilidad() {
         return disponibilidad;
     }
     public void setDisponibilidad(boolean disponibilidad) {
         this.disponibilidad = disponibilidad;
     }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Getters y setters
    public String getImagenBase64() {
        if (imagen == null || imagen.length == 0) {
            return null; // Devuelve null si no hay datos
        }
        return Base64.getEncoder().encodeToString(imagen);
    }


    @Override
    public String toString() {
        return "Producto [id_producto=" + id_producto + ", imagen=" + (imagen != null ? "tamaño=" + imagen.length : "sin imagen") 
            + ", nombre=" + nombre + ", precio=" + precio + ", cantidad=" + cantidad 
            + ", descripcion=" + descripcion + ", disponibilidad=" + disponibilidad 
            + ", categoria=" + categoria + "]";
    }

 }
