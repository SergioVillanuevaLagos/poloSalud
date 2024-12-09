package com.example.service;

import com.example.model.evento;
import com.example.repository.eventoRepository;
import com.example.service.eventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class eventoServiceImpl implements eventoService {

    @Autowired
    private eventoRepository eventoRepository;

    @Override
    public evento crearEvento(String descripcion, String direccion, LocalDateTime fechaEvento, String notificacion, int idAdmin) {
        evento nuevoEvento = new evento();
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setDireccion(direccion);
        nuevoEvento.setFecha_evento(fechaEvento);
        nuevoEvento.setNotificacion(notificacion);
        nuevoEvento.setIdAdmin(idAdmin);
        return eventoRepository.save(nuevoEvento);
    }


    @Override
    public evento obtenerEventoPorId(int idEvento) {
        return eventoRepository.findById(idEvento).orElse(null);
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
