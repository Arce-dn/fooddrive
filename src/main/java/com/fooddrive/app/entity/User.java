package com.fooddrive.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // Nuevos atributos
    private String nombreCompleto;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private String estado;
    private String direccionPrincipal;
    private LocalDate ultimaFechaAcceso;
    private String direccionAlternativa;
    private String disponibilidad;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Punto> puntos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cupon> cupones = new ArrayList<>();
    
    // Constructor vacío
    public User() {
    }

    // Constructor con parámetros
    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public User(String nombreCompleto, String email) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
    }

    //Constructor completo si tiene todos los datos
    public User(String username, String password, String nombreCompleto, String email, String telefono, 
            LocalDate fechaRegistro, String estado, String direccionPrincipal, LocalDate ultimaFechaAcceso, 
            String direccionAlternativa, String disponibilidad) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.direccionPrincipal = direccionPrincipal;
        this.ultimaFechaAcceso = ultimaFechaAcceso;
        this.direccionAlternativa = direccionAlternativa;
        this.disponibilidad = disponibilidad;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Punto> getPuntos() {
        return puntos;
    }
    public void setPuntos(List<Punto> puntos) {
        this.puntos = puntos;
    }
    public List<Cupon> getCupones() {
        return cupones;
    }
    public void setCupones(List<Cupon> cupones) {
        this.cupones = cupones;
    }

    // Getters y Setters de los nuevos atributos
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDireccionPrincipal() { return direccionPrincipal; }
    public void setDireccionPrincipal(String direccionPrincipal) { this.direccionPrincipal = direccionPrincipal; }

    public LocalDate getUltimaFechaAcceso() { return ultimaFechaAcceso; }
    public void setUltimaFechaAcceso(LocalDate ultimaFechaAcceso) { this.ultimaFechaAcceso = ultimaFechaAcceso; }

    public String getDireccionAlternativa() { return direccionAlternativa; }
    public void setDireccionAlternativa(String direccionAlternativa) { this.direccionAlternativa = direccionAlternativa; }

    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String disponibilidad) { this.disponibilidad = disponibilidad; }

    // Método helper para añadir un rol al usuario
    public void addRole(Role role) {
        this.roles.add(role);
    }

    
}
