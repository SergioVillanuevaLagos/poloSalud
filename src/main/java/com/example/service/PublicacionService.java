package com.example.service;

import com.example.model.publicacion;
import com.example.repositorio.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Obtener todas las publicaciones
    public List<publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

    // Obtener una publicación por ID
    public Optional<publicacion> getPublicacionById(Integer id) {
        return publicacionRepository.findById(id);
    }

    // Crear una nueva publicación
    public publicacion createPublicacion(publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    // Actualizar una publicación existente
    public publicacion updatePublicacion(Integer id, publicacion publicacionDetails) {
        if (publicacionRepository.existsById(id)) {
            publicacionDetails.setIdPublicacion(id);
            return publicacionRepository.save(publicacionDetails);
        }
        return null;
    }

    // Eliminar una publicación por ID
    public void deletePublicacion(Integer id) {
        publicacionRepository.deleteById(id);
    }
}