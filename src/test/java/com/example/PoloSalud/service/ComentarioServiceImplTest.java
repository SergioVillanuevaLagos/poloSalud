package com.example.PoloSalud.service;


import com.example.model.comentario;
import com.example.model.publicacion;
import com.example.model.usuario;
import com.example.repositorio.comentarioRepository;
import com.example.repositorio.PublicacionRepository;
import com.example.service.ComentarioServiceImpl;
import com.example.repositorio.usuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceImplTest {

    @Mock
    private comentarioRepository comentarioRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private usuarioRepository usuarioRepository;

    @InjectMocks
    private ComentarioServiceImpl comentarioService;

    private publicacion publicacion;
    private usuario usuario;
    private comentario comentario1;
    private comentario comentario2;

    @BeforeEach
    void setUp() {
        publicacion = new publicacion();
        publicacion.setIdPublicacion(1);

        usuario = new usuario();
        usuario.setIdUsuario(1);

        comentario1 = new comentario();
        comentario1.setIdComenentario(1);
        comentario1.setComentario("Comentario 1");
        comentario1.setCreaComentario(LocalDateTime.now());
        comentario1.setPublicacion(publicacion);
        comentario1.setUsuario(usuario);

        comentario2 = new comentario();
        comentario2.setIdComenentario(2);
        comentario2.setComentario("Comentario 2");
        comentario2.setCreaComentario(LocalDateTime.now());
        comentario2.setPublicacion(publicacion);
        comentario2.setUsuario(usuario);
    }

    @Test
    void testCrearComentario() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(publicacion));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(comentarioRepository.save(any(comentario.class))).thenReturn(comentario1);

        comentario result = comentarioService.crearComentario("Comentario 1", 1, 1);

        assertNotNull(result);
        assertEquals("Comentario 1", result.getComentario());
        assertEquals(publicacion, result.getPublicacion());
        assertEquals(usuario, result.getUsuario());
    }

    @Test
    void testEliminarComentario() {
        when(comentarioRepository.findById(1)).thenReturn(Optional.of(comentario1));
        doNothing().when(comentarioRepository).delete(comentario1);

        comentario result = comentarioService.eliminarComentario(1);

        assertNotNull(result);
        assertEquals(comentario1, result);
    }

    @Test
    void testObtenerComentariosPorPublicacion() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(publicacion));
        when(comentarioRepository.findByPublicacion(publicacion)).thenReturn(Arrays.asList(comentario1, comentario2));

        List<comentario> result = comentarioService.obtenerComentariosPorPublicacion(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(comentario1, result.get(0));
        assertEquals(comentario2, result.get(1));
    }

    @Test
    void testCrearComentarioPublicacionNoEncontrada() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            comentarioService.crearComentario("Comentario 1", 1, 1);
        });
    }

    @Test
    void testCrearComentarioUsuarioNoEncontrado() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(publicacion));
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            comentarioService.crearComentario("Comentario 1", 1, 1);
        });
    }

    @Test
    void testEliminarComentarioNoEncontrado() {
        when(comentarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            comentarioService.eliminarComentario(1);
        });
    }

    @Test
    void testObtenerComentariosPorPublicacionNoEncontrada() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            comentarioService.obtenerComentariosPorPublicacion(1);
        });
    }
}