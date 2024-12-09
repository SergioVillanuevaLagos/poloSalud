package com.example.PoloSalud.service;


import com.example.model.evento;
import com.example.repositorio.eventoRepository;
import com.example.service.eventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventoServiceImplTest {

    @InjectMocks
    private eventoServiceImpl eventoService;

    @Mock
    private eventoRepository eventoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEvento() {
        String descripcion = "Evento de prueba";
        String direccion = "Calle 123";
        LocalDateTime fechaEvento = LocalDateTime.now();
        String notificacion = "Notificación de prueba";
        int idAdmin = 1;

        evento nuevoEvento = new evento();
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setDireccion(direccion);
        nuevoEvento.setFecha_evento(fechaEvento);
        nuevoEvento.setNotificacion(notificacion);
        nuevoEvento.setIdAdmin(idAdmin);

        when(eventoRepository.save(any(evento.class))).thenReturn(nuevoEvento);

        evento eventoCreado = eventoService.crearEvento(descripcion, direccion, fechaEvento, notificacion, idAdmin);

        assertNotNull(eventoCreado);
        assertEquals(descripcion, eventoCreado.getDescripcion());
        assertEquals(direccion, eventoCreado.getDireccion());
        assertEquals(fechaEvento, eventoCreado.getFecha_evento());
        assertEquals(notificacion, eventoCreado.getNotificacion());
        assertEquals(idAdmin, eventoCreado.getIdAdmin());
    }

    @Test
    void testObtenerEventoPorIdExistente() {
        int idEvento = 1;
        evento eventoExistente = new evento();
        eventoExistente.setID_evento(idEvento);
        eventoExistente.setDescripcion("Evento de prueba");

        when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(eventoExistente));

        evento eventoObtenido = eventoService.obtenerEventoPorId(idEvento);

        assertNotNull(eventoObtenido);
        assertEquals(idEvento, eventoObtenido.getID_evento());
        assertEquals("Evento de prueba", eventoObtenido.getDescripcion());
    }

    @Test
    void testObtenerEventoPorIdNoExistente() {
        int idEvento = 1;

        when(eventoRepository.findById(idEvento)).thenReturn(Optional.empty());

        evento eventoObtenido = eventoService.obtenerEventoPorId(idEvento);

        assertNull(eventoObtenido);
    }

    @Test
    void testObtenerTodosLosEventos() {
        evento evento1 = new evento();
        evento1.setID_evento(1);
        evento1.setDescripcion("Evento 1");

        evento evento2 = new evento();
        evento2.setID_evento(2);
        evento2.setDescripcion("Evento 2");

        List<evento> eventos = Arrays.asList(evento1, evento2);

        when(eventoRepository.findAll()).thenReturn(eventos);

        List<evento> eventosObtenidos = eventoService.obtenerTodosLosEventos();

        assertNotNull(eventosObtenidos);
        assertEquals(2, eventosObtenidos.size());
    }

    @Test
    void testEliminarEvento() {
        int idEvento = 1;

        doNothing().when(eventoRepository).deleteById(idEvento);

        eventoService.eliminarEvento(idEvento);

        verify(eventoRepository, times(1)).deleteById(idEvento);
    }

    @Test
    void testCrearEventoConDatosCompletos() {
        String descripcion = "Evento de prueba";
        String direccion = "Calle 123";
        LocalDateTime fechaEvento = LocalDateTime.now();
        String notificacion = "Notificación de prueba";
        int idAdmin = 1;

        evento nuevoEvento = new evento();
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setDireccion(direccion);
        nuevoEvento.setFecha_evento(fechaEvento);
        nuevoEvento.setNotificacion(notificacion);
        nuevoEvento.setIdAdmin(idAdmin);

        when(eventoRepository.save(any(evento.class))).thenReturn(nuevoEvento);

        evento eventoCreado = eventoService.crearEvento(descripcion, direccion, fechaEvento, notificacion, idAdmin);

        assertNotNull(eventoCreado);
        assertEquals(descripcion, eventoCreado.getDescripcion());
        assertEquals(direccion, eventoCreado.getDireccion());
        assertEquals(fechaEvento, eventoCreado.getFecha_evento());
        assertEquals(notificacion, eventoCreado.getNotificacion());
        assertEquals(idAdmin, eventoCreado.getIdAdmin());
    }

    @Test
    void testObtenerTodosLosEventosVacio() {
        when(eventoRepository.findAll()).thenReturn(Arrays.asList());

        List<evento> eventosObtenidos = eventoService.obtenerTodosLosEventos();

        assertNotNull(eventosObtenidos);
        assertEquals(0, eventosObtenidos.size());
    }

    @Test
    void testEliminarEventoNoExistente() {
        int idEvento = 1;

        doThrow(new RuntimeException("Evento no encontrado")).when(eventoRepository).deleteById(idEvento);

        assertThrows(RuntimeException.class, () -> {
            eventoService.eliminarEvento(idEvento);
        });
    }
}