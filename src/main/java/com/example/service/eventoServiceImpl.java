package com.example.service;

import com.example.model.evento;
import com.example.repository.eventoRepository;
import com.example.service.eventoService;
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
        nuevoEvento.setEstado(true); // Puedes establecer el estado inicial a 'true' si es necesario
        return eventoRepository.save(nuevoEvento);
    }

    @Override
    public evento modificarFechaEvento(int idEvento, LocalDate nuevaFecha) {
        Optional<evento> eventoOptional = eventoRepository.findById(idEvento);
        if (eventoOptional.isPresent()) {
            evento eventoExistente = eventoOptional.get();
            eventoExistente.setFecha_evento(nuevaFecha);
            return eventoRepository.save(eventoExistente);
        } else {
            return null; // O puedes lanzar una excepción si prefieres
        }
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
