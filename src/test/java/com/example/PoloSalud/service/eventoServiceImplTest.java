package com.example.PoloSalud.service;

import com.example.model.evento;
import com.example.repositorio.eventoRepository;
import com.example.service.eventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class eventoServiceImplTest {

    @InjectMocks
    private eventoServiceImpl eventoService;

    @Mock
    private eventoRepository eventoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEventoSuccess() {
        String descripcion = "Evento de prueba";
        String direccion = "Calle 123";
        LocalDate fechaEvento = LocalDate.now();
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
    void testObtenerTodosLosEventosSuccess() {
        evento evento1 = new evento();
        evento evento2 = new evento();
        List<evento> eventos = Arrays.asList(evento1, evento2);

        when(eventoRepository.findAll()).thenReturn(eventos);

        List<evento> eventosObtenidos = eventoService.obtenerTodosLosEventos();

        assertNotNull(eventosObtenidos);
        assertEquals(2, eventosObtenidos.size());
    }

    @Test
    void testEliminarEventoSuccess() {
        int idEvento = 1;

        doNothing().when(eventoRepository).deleteById(idEvento);

        eventoService.eliminarEvento(idEvento);

        verify(eventoRepository, times(1)).deleteById(idEvento);
    }

    @Test
    void testCrearEventoNullDescripcion() {
        String descripcion = null;
        String direccion = "Calle 123";
        LocalDate fechaEvento = LocalDate.now();
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
        assertNull(eventoCreado.getDescripcion());
        assertEquals(direccion, eventoCreado.getDireccion());
        assertEquals(fechaEvento, eventoCreado.getFecha_evento());
        assertEquals(notificacion, eventoCreado.getNotificacion());
        assertEquals(idAdmin, eventoCreado.getIdAdmin());
    }

    @Test
    void testObtenerTodosLosEventosEmptyList() {
        when(eventoRepository.findAll()).thenReturn(Arrays.asList());

        List<evento> eventosObtenidos = eventoService.obtenerTodosLosEventos();

        assertNotNull(eventosObtenidos);
        assertEquals(0, eventosObtenidos.size());
    }

    @Test
    void testEliminarEventoNonExistent() {
        int idEvento = 1;

        doThrow(new RuntimeException("Evento no encontrado")).when(eventoRepository).deleteById(idEvento);

        assertThrows(RuntimeException.class, () -> {
            eventoService.eliminarEvento(idEvento);
        });
    }
}