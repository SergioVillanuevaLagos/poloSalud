package com.example.PoloSalud.service;


import com.example.model.publicacion;
import com.example.repositorio.PublicacionRepository;
import com.example.service.NoticiaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoticiaServiceImplTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private NoticiaServiceImpl noticiaService;

    private publicacion noticia1;
    private publicacion noticia2;

    @BeforeEach
    void setUp() {
        LocalDate date1 = LocalDate.of(2023, 10, 1);
        LocalDate date2 = LocalDate.of(2023, 10, 2);

        noticia1 = new publicacion();
        noticia1.setIdPublicacion(1);
        noticia1.setTitulo("Noticia 1");
        noticia1.setSubtitulo("Subtitulo 1");
        noticia1.setContenido("Contenido 1");
        noticia1.setCategoria("Categoria 1");
        noticia1.setArchivoAdjunto("archivo1.pdf".getBytes());
        noticia1.setUrlPublicacion("http://example.com/noticia1");
        noticia1.setFechPublicacion(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        noticia1.setIdAdmin(1);

        noticia2 = new publicacion();
        noticia2.setIdPublicacion(2);
        noticia2.setTitulo("Noticia 2");
        noticia2.setSubtitulo("Subtitulo 2");
        noticia2.setContenido("Contenido 2");
        noticia2.setCategoria("Categoria 2");
        noticia2.setArchivoAdjunto("archivo2.pdf".getBytes());
        noticia2.setUrlPublicacion("http://example.com/noticia2");
        noticia2.setFechPublicacion(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        noticia2.setIdAdmin(2);
    }

    @Test
    void testCrearNoticia() {
        LocalDate date = LocalDate.of(2023, 10, 1); // Cambia la fecha a la que esperas
        Date fechPublicacion = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        when(publicacionRepository.save(any(publicacion.class))).thenReturn(noticia1);

        publicacion result = noticiaService.crearNoticia("Noticia 1", "Subtitulo 1", "Contenido 1", "Categoria 1", "archivo1.pdf".getBytes(), "http://example.com/noticia1", fechPublicacion, 1);

        assertNotNull(result);
        assertEquals("Noticia 1", result.getTitulo());
        assertEquals("Subtitulo 1", result.getSubtitulo());
        assertEquals("Contenido 1", result.getContenido());
        assertEquals("Categoria 1", result.getCategoria());
        assertArrayEquals("archivo1.pdf".getBytes(), result.getArchivoAdjunto());
        assertEquals("http://example.com/noticia1", result.getUrlPublicacion());
        assertEquals(fechPublicacion, result.getFechPublicacion()); // Asegúrate de que la fecha sea la correcta
        assertEquals(1, result.getIdAdmin());
    }

    @Test
    void testObtenerTodasLasNoticias() {
        List<publicacion> noticias = Arrays.asList(noticia1, noticia2);
        when(publicacionRepository.findAll()).thenReturn(noticias);

        List<publicacion> result = noticiaService.obtenerTodasLasNoticias();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(noticia1, result.get(0));
        assertEquals(noticia2, result.get(1));
    }

    @Test
    void testObtenerNoticiaPorId() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(noticia1));

        publicacion result = noticiaService.obtenerNoticiaPorId(1);

        assertNotNull(result);
        assertEquals(noticia1, result);
    }

    @Test
    void testObtenerNoticiaPorIdNoEncontrada() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        publicacion result = noticiaService.obtenerNoticiaPorId(1);

        assertNull(result);
    }

    @Test
    void testActualizarNoticia() {
        LocalDate date = LocalDate.of(2023, 10, 3);
        Date fechPublicacion = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        when(publicacionRepository.findById(1)).thenReturn(Optional.of(noticia1));
        when(publicacionRepository.save(any(publicacion.class))).thenReturn(noticia1);

        publicacion result = noticiaService.actualizarNoticia(1, "Noticia 1 Actualizada", "Contenido 1 Actualizado", "Categoria 1 Actualizada", "archivo1_actualizado.pdf".getBytes(), "http://example.com/noticia1_actualizada", fechPublicacion, 1);

        assertNotNull(result);
        assertEquals("Noticia 1 Actualizada", result.getTitulo());
        assertEquals("Contenido 1 Actualizado", result.getContenido());
        assertEquals("Categoria 1 Actualizada", result.getCategoria());
        assertArrayEquals("archivo1_actualizado.pdf".getBytes(), result.getArchivoAdjunto());
        assertEquals("http://example.com/noticia1_actualizada", result.getUrlPublicacion());
        assertEquals(fechPublicacion, result.getFechPublicacion());
        assertEquals(1, result.getIdAdmin());
    }

    @Test
    void testActualizarNoticiaNoEncontrada() {
        LocalDate date = LocalDate.of(2023, 10, 3);
        Date fechPublicacion = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        publicacion result = noticiaService.actualizarNoticia(1, "Noticia 1 Actualizada", "Contenido 1 Actualizado", "Categoria 1 Actualizada", "archivo1_actualizado.pdf".getBytes(), "http://example.com/noticia1_actualizada", fechPublicacion, 1);

        assertNull(result);
    }

    @Test
    void testActualizarTitulo() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(noticia1));
        when(publicacionRepository.save(any(publicacion.class))).thenReturn(noticia1);

        publicacion result = noticiaService.actualizarTitulo(1, "Noticia 1 Actualizada");

        assertNotNull(result);
        assertEquals("Noticia 1 Actualizada", result.getTitulo());
    }

    @Test
    void testActualizarTituloNoEncontrada() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        publicacion result = noticiaService.actualizarTitulo(1, "Noticia 1 Actualizada");

        assertNull(result);
    }

    @Test
    void testActualizarSubtituloYContenido() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(noticia1));
        when(publicacionRepository.save(any(publicacion.class))).thenReturn(noticia1);

        publicacion result = noticiaService.actualizarSubtituloYContenido(1, "Subtitulo 1 Actualizado", "Contenido 1 Actualizado");

        assertNotNull(result);
        assertEquals("Subtitulo 1 Actualizado", result.getSubtitulo());
        assertEquals("Contenido 1 Actualizado", result.getContenido());
    }

    @Test
    void testActualizarSubtituloYContenidoNoEncontrada() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        publicacion result = noticiaService.actualizarSubtituloYContenido(1, "Subtitulo 1 Actualizado", "Contenido 1 Actualizado");

        assertNull(result);
    }

    @Test
    void testEliminarNoticia() {
        doNothing().when(publicacionRepository).deleteById(1);

        noticiaService.eliminarNoticia(1);

        verify(publicacionRepository, times(1)).deleteById(1);
    }
}