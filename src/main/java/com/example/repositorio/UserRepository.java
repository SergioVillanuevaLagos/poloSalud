package com.example.repositorio;

import com.example.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<usuario, Long> {
    usuario findByEmail(String email);
}