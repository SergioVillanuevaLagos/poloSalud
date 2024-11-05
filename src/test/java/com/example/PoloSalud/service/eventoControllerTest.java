package com.example.PoloSalud.service;

import com.example.model.Rol;
import com.example.model.evento;
import com.example.model.usuario;
import com.example.controller.eventoController;
import com.example.service.eventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class eventoControllerTest {

    @InjectMocks
    private eventoController eventoController;

    @Mock
    private eventoService eventoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEventoSuccess() {
        usuario user = new usuario();
        user.setIdUsuario(1);
        user.setRol(new Rol(1, "ADMIN"));

        Map<String, String> params = new HashMap<>();
        params.put("descripcion", "Evento de prueba");
        params.put("direccion", "Calle 123");
        params.put("fechaEvento", "2023-12-25");
        params.put("notificacion", "Notificación de prueba");

        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        evento eventoCreado = new evento();
        eventoCreado.setID_evento(1);
        eventoCreado.setDescripcion("Evento de prueba");
        eventoCreado.setDireccion("Calle 123");
        eventoCreado.setFecha_evento(LocalDate.parse("2023-12-25"));
        eventoCreado.setNotificacion("Notificación de prueba");

        when(eventoService.crearEvento(anyString(), anyString(), any(LocalDate.class), anyString(), anyInt())).thenReturn(eventoCreado);

        ResponseEntity<Map<String, String>> response = eventoController.crearEvento(params, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
        assertEquals("Evento creado exitosamente", response.getBody().get("message"));
    }

    @Test
    void testCrearEventoUnauthorized() {
        Map<String, String> params = new HashMap<>();
        params.put("descripcion", "Evento de prueba");
        params.put("direccion", "Calle 123");
        params.put("fechaEvento", "2023-12-25");
        params.put("notificacion", "Notificación de prueba");

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Map<String, String>> response = eventoController.crearEvento(params, request.getSession());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
        assertEquals("Usuario no autenticado", response.getBody().get("message"));
    }

    @Test
    void testCrearEventoForbidden() {
        usuario user = new usuario();
        user.setIdUsuario(2);
        user.setRol(new Rol(2, "USER"));

        Map<String, String> params = new HashMap<>();
        params.put("descripcion", "Evento de prueba");
        params.put("direccion", "Calle 123");
        params.put("fechaEvento", "2023-12-25");
        params.put("notificacion", "Notificación de prueba");

        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Map<String, String>> response = eventoController.crearEvento(params, session);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
        assertEquals("Acceso denegado. Solo los administradores pueden crear eventos.", response.getBody().get("message"));
    }

    @Test
    void testObtenerTodosLosEventos() {
        evento evento1 = new evento();
        evento1.setID_evento(1);
        evento1.setDescripcion("Evento 1");
        evento1.setFecha_evento(LocalDate.parse("2023-12-25"));

        evento evento2 = new evento();
        evento2.setID_evento(2);
        evento2.setDescripcion("Evento 2");
        evento2.setFecha_evento(LocalDate.parse("2023-12-30"));

        List<evento> eventos = Arrays.asList(evento1, evento2);

        when(eventoService.obtenerTodosLosEventos()).thenReturn(eventos);

        ResponseEntity<List<Map<String, Object>>> response = eventoController.obtenerTodosLosEventos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testEliminarEvento() {
        int eventoId = 1;

        doNothing().when(eventoService).eliminarEvento(eventoId);

        ResponseEntity<Void> response = eventoController.eliminarEvento(eventoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}