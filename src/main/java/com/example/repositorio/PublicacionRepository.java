package com.example.repositorio;


import com.example.model.evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<evento, Integer> {
}
