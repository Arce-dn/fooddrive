package com.fooddrive.app.seguridad.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddrive.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    void deleteById(long id);
 // Método para buscar por username
}

