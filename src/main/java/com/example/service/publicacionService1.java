package com.example.service;



import com.example.model.publicacion;
import com.example.repositorio.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class publicacionService1 {


    @Autowired

    private PublicacionRepository publicacionRepository;

    public List<publicacion> obtenerPublicacionesOrdenadas() {
        return publicacionRepository.findAllByOrderByFechPublicacionDesc();
    }

    public publicacion guardarPublicacion(publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    public publicacion obtenerPublicacionPorId(Integer id) {
        return publicacionRepository.findById(id).orElse(null);
    }

    public void eliminarPublicacion(Integer id) {
        publicacionRepository.deleteById(id);
    }
    
}