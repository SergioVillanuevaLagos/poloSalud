package com.example.controller;

import com.example.model.comentario;
import com.example.model.publicacion;
import com.example.model.usuario;
import com.example.service.ComentarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class comentarioControllerTest {

    @Mock
    private ComentarioServiceImpl comentarioService;

    @InjectMocks
    private comentarioController comentarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearComentario_Success() {
        // Datos de prueba
        Map<String, String> params = new HashMap<>();
        params.put("contenido", "Este es un comentario de prueba");
        params.put("idPublicacion", "1");
        params.put("idUsuario", "1");

        // Objeto comentario simulado
        comentario comentarioMock = new comentario();
        comentarioMock.setIdComenentario(1);
        comentarioMock.setComentario("Este es un comentario de prueba");
        comentarioMock.setCreaComentario(LocalDateTime.now());

        publicacion publicacionMock = new publicacion();
        publicacionMock.setIdPublicacion(1);
        comentarioMock.setPublicacion(publicacionMock);

        usuario usuarioMock = new usuario();
        usuarioMock.setIdUsuario(1);
        usuarioMock.setNombreUsuario("UsuarioPrueba");
        comentarioMock.setUsuario(usuarioMock);

        // Configurar el comportamiento del servicio mock
        when(comentarioService.crearComentario(anyString(), anyInt(), anyInt())).thenReturn(comentarioMock);

        // Llamar al método del controlador
        ResponseEntity<Map<String, Object>> response = comentarioController.crearComentario(params);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertEquals(1, responseBody.get("id"));
        assertEquals("Este es un comentario de prueba", responseBody.get("contenido"));
        assertEquals(1, responseBody.get("idPublicacion"));
        assertEquals(1, responseBody.get("idUsuario"));
        assertEquals("UsuarioPrueba", responseBody.get("usuario"));
    }


    @Test
    void testObtenerComentariosPorPublicacion_Success() {
        // Datos de prueba
        int idPublicacion = 1;

        // Lista de comentarios simulados
        comentario comentarioMock1 = new comentario();
        comentarioMock1.setIdComenentario(1);
        comentarioMock1.setComentario("Comentario 1");
        comentarioMock1.setCreaComentario(LocalDateTime.now());

        publicacion publicacionMock = new publicacion();
        publicacionMock.setIdPublicacion(1);
        comentarioMock1.setPublicacion(publicacionMock);

        usuario usuarioMock = new usuario();
        usuarioMock.setIdUsuario(1);
        usuarioMock.setNombreUsuario("UsuarioPrueba");
        comentarioMock1.setUsuario(usuarioMock);

        comentario comentarioMock2 = new comentario();
        comentarioMock2.setIdComenentario(2);
        comentarioMock2.setComentario("Comentario 2");
        comentarioMock2.setCreaComentario(LocalDateTime.now());
        comentarioMock2.setPublicacion(publicacionMock);
        comentarioMock2.setUsuario(usuarioMock);

        List<comentario> comentariosMock = Arrays.asList(comentarioMock1, comentarioMock2);

        // Configurar el comportamiento del servicio mock
        when(comentarioService.obtenerComentariosPorPublicacion(idPublicacion)).thenReturn(comentariosMock);

        // Llamar al método del controlador
        ResponseEntity<List<Map<String, Object>>> response = comentarioController.obtenerComentariosPorPublicacion(idPublicacion);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Map<String, Object>> responseBody = response.getBody();
        assertEquals(2, responseBody.size());
        assertEquals(1, responseBody.get(0).get("id"));
        assertEquals("Comentario 1", responseBody.get(0).get("contenido"));
        assertEquals(2, responseBody.get(1).get("id"));
        assertEquals("Comentario 2", responseBody.get(1).get("contenido"));
    }

    @Test
    void testObtenerComentariosPorPublicacion_NoComments() {
        // Datos de prueba
        int idPublicacion = 1;

        // Configurar el comportamiento del servicio mock para devolver una lista vacía
        when(comentarioService.obtenerComentariosPorPublicacion(idPublicacion)).thenReturn(Arrays.asList());

        // Llamar al método del controlador
        ResponseEntity<List<Map<String, Object>>> response = comentarioController.obtenerComentariosPorPublicacion(idPublicacion);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Map<String, Object>> responseBody = response.getBody();
        assertEquals(0, responseBody.size());
    }

    @Test
    void testEliminarComentario_Success() {
        // Datos de prueba
        int idComentario = 1;

        // Llamar al método del controlador
        ResponseEntity<Void> response = comentarioController.eliminarComentario(idComentario);

        // Verificar el resultado
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(comentarioService, times(1)).eliminarComentario(idComentario);
    }




}