package com.example.repositorio;

import com.example.model.publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<publicacion, Integer> {
    List<publicacion> findAllByOrderByFechPublicacionDesc();
}
