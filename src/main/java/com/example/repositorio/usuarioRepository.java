
package com.example.repositorio;

import com.example.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<usuario, Integer> {
   
}