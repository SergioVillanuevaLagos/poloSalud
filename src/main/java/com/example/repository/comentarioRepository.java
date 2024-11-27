package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.comentario;
import com.example.model.publicacion;

public interface comentarioRepository extends JpaRepository<comentario, Integer>  {

    List<comentario> findByPublicacion(publicacion publi);

    
    
}
