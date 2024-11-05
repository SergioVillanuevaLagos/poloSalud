package com.example.service;

import com.example.model.evento;

import java.time.LocalDate;
import java.util.List;

public interface eventoService {

    // Crear un evento
    evento crearEvento(String descripcion, String direccion, LocalDate fechaEvento, String notificacion, int idAdmin);



    // Obtener un evento por su ID
    evento obtenerEventoPorId(int idEvento);

    // Obtener todos los eventos
    List<evento> obtenerTodosLosEventos();

    // Eliminar un evento por su ID
    void eliminarEvento(int idEvento);
}
