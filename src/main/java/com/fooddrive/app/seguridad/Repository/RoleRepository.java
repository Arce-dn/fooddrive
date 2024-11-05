package com.fooddrive.app.seguridad.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddrive.app.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}