package com.fooddrive.app.seguridad.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddrive.app.entity.Role;
import com.fooddrive.app.seguridad.Repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    //Obtener el listado de todos los roles
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    //Método para registrar nuevos roles
    public Role guardarRole(Role role){
        return roleRepository.save(role);
    }
    
    public void eliminarRol(Long id) {
        roleRepository.deleteById(id);  // Eliminar el rol por ID
    }
}
