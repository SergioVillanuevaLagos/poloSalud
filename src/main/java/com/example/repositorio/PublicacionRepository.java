package com.example.repositorio;


import com.example.model.publicacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<publicacion, Integer> {
}
