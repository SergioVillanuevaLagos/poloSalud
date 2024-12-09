package com.example.repositorio;

import com.example.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eventoRepository extends JpaRepository<evento, Integer> {
}