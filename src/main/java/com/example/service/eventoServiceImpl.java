package com.example.service;

import com.example.model.evento;
import com.example.repositorio.eventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class eventoServiceImpl implements eventoService {

    @Autowired
    private eventoRepository eventoRepository;

    @Override
    public evento crearEvento(String descripcion, String direccion, LocalDate fechaEvento, String notificacion, int idAdmin) {
        evento nuevoEvento = new evento();
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setDireccion(direccion);
        nuevoEvento.setFecha_evento(fechaEvento);
        nuevoEvento.setNotificacion(notificacion);
        nuevoEvento.setIdAdmin(idAdmin);
        return eventoRepository.save(nuevoEvento);
    }
    @Override
    public List<evento> obtenerTodosLosEventos() {
        return eventoRepository.findAll();
    }

    @Override
    public void eliminarEvento(int idEvento) {
        eventoRepository.deleteById(idEvento);
    }
}
