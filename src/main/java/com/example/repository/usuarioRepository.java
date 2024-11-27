
package com.example.repository;

import com.example.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<usuario, Integer> {
   
}
