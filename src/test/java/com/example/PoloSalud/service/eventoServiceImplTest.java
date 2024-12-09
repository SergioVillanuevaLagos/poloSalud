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

        //prueba crear evento con exito
        void testCrearEventoSuccess() {
        String descripcion = "Evento de prueba";
        String direccion = "Calle 123";
        LocalDate fechaEvento = LocalDate.now();
        String notificacion = "Notificación de prueba";
        int idAdmin = 1;

        //crea un nuevo evento con los valores de prueba
        evento nuevoEvento = new evento();
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setDireccion(direccion);
        nuevoEvento.setFecha_evento(fechaEvento);
        nuevoEvento.setNotificacion(notificacion);
        nuevoEvento.setIdAdmin(idAdmin);

        //define que el metodo save del repositorio devolvera el nuevo evento
        when(eventoRepository.save(any(evento.class))).thenReturn(nuevoEvento);

        //llama al metodo del servicio para crear el evento
        evento eventoCreado = eventoService.crearEvento(descripcion, direccion, fechaEvento, notificacion, idAdmin);

        //revisamos que se creo correcto y que no tenga nulos
        assertNotNull(eventoCreado);
        assertEquals(descripcion, eventoCreado.getDescripcion());
        assertEquals(direccion, eventoCreado.getDireccion());
        assertEquals(fechaEvento, eventoCreado.getFecha_evento());
        assertEquals(notificacion, eventoCreado.getNotificacion());
        assertEquals(idAdmin, eventoCreado.getIdAdmin());
    }

    @Test
        //prueba obtener los eventos con exito
    void testObtenerTodosLosEventosSuccess() {
        evento evento1 = new evento();
        evento evento2 = new evento();
        List<evento> eventos = Arrays.asList(evento1, evento2);

        //define que el repositorio devuelva una lista dos eventos
        when(eventoRepository.findAll()).thenReturn(eventos);

        //llama al metodo del servicio para obtener todos los eventos
        List<evento> eventosObtenidos = eventoService.obtenerTodosLosEventos();

        //verificamos que la lista no sea nula y que contenga dos elementos
        assertNotNull(eventosObtenidos);
        assertEquals(2, eventosObtenidos.size());
    }

    @Test
        // prueba elimina un evento con exito
    void testEliminarEventoSuccess() {
        int idEvento = 1;

        //simula una eliminacion
        doNothing().when(eventoRepository).deleteById(idEvento);

        //llama al metodo del servicio para eliminar el evento
        eventoService.eliminarEvento(idEvento);

        //verifica que el metodo del deleById haya sido llamado una sola vez
        verify(eventoRepository, times(1)).deleteById(idEvento);
    }

    @Test
    //prueba crear un evento con descripcion nula
    void testCrearEventoNullDescripcion() {
        String descripcion = null;
        String direccion = "Calle 123";
        LocalDate fechaEvento = LocalDate.now();
        String notificacion = "Notificación de prueba";
        int idAdmin = 1;

        //crea un nuevo evento con la descripcion nula
        evento nuevoEvento = new evento();
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setDireccion(direccion);
        nuevoEvento.setFecha_evento(fechaEvento);
        nuevoEvento.setNotificacion(notificacion);
        nuevoEvento.setIdAdmin(idAdmin);

        // Define que el repositorio devolverá el nuevo evento
        when(eventoRepository.save(any(evento.class))).thenReturn(nuevoEvento);

        //llamas al metood del servicio para crear el evento
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