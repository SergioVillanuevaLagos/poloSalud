package com.example.PoloSalud.service;


import com.example.model.publicacion;
import com.example.repositorio.PublicacionRepository;
import com.example.service.publicacionService1;
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
public class publicacionService1Test {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private publicacionService1 publicacionService;

    private publicacion publicacion1;
    private publicacion publicacion2;

    @BeforeEach
    void setUp() {
        LocalDate date1 = LocalDate.of(2023, 10, 1);
        LocalDate date2 = LocalDate.of(2023, 10, 2);

        publicacion1 = new publicacion();
        publicacion1.setIdPublicacion(1);
        publicacion1.setTitulo("Publicacion 1");
        publicacion1.setFechPublicacion(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        publicacion2 = new publicacion();
        publicacion2.setIdPublicacion(2);
        publicacion2.setTitulo("Publicacion 2");
        publicacion2.setFechPublicacion(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    void testObtenerPublicacionesOrdenadas() {
        List<publicacion> publicaciones = Arrays.asList(publicacion2, publicacion1);
        when(publicacionRepository.findAllByOrderByFechPublicacionDesc()).thenReturn(publicaciones);

        List<publicacion> result = publicacionService.obtenerPublicacionesOrdenadas();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(publicacion2, result.get(0));
        assertEquals(publicacion1, result.get(1));
    }

    @Test
    void testGuardarPublicacion() {
        when(publicacionRepository.save(any(publicacion.class))).thenReturn(publicacion1);

        publicacion result = publicacionService.guardarPublicacion(publicacion1);

        assertNotNull(result);
        assertEquals(publicacion1, result);
    }

    @Test
    void testObtenerPublicacionPorId() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(publicacion1));

        publicacion result = publicacionService.obtenerPublicacionPorId(1);

        assertNotNull(result);
        assertEquals(publicacion1, result);
    }

    @Test
    void testObtenerPublicacionPorIdNoEncontrada() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        publicacion result = publicacionService.obtenerPublicacionPorId(1);

        assertNull(result);
    }

    @Test
    void testEliminarPublicacion() {
        doNothing().when(publicacionRepository).deleteById(1);

        publicacionService.eliminarPublicacion(1);

        verify(publicacionRepository, times(1)).deleteById(1);
    }

    @Test
    void testGuardarPublicacionConDatosCompletos() {
        LocalDate date = LocalDate.of(2023, 10, 3);
        Date fechPublicacion = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        publicacion publicacion3 = new publicacion();
        publicacion3.setIdPublicacion(3);
        publicacion3.setTitulo("Publicacion 3");
        publicacion3.setSubtitulo("Subtitulo 3");
        publicacion3.setContenido("Contenido 3");
        publicacion3.setCategoria("Categoria 3");
        publicacion3.setArchivoAdjunto("archivo3.pdf".getBytes());
        publicacion3.setUrlPublicacion("http://example.com/publicacion3");
        publicacion3.setFechPublicacion(fechPublicacion);
        publicacion3.setIdAdmin(3);

        when(publicacionRepository.save(any(publicacion.class))).thenReturn(publicacion3);

        publicacion result = publicacionService.guardarPublicacion(publicacion3);

        assertNotNull(result);
        assertEquals("Publicacion 3", result.getTitulo());
        assertEquals("Subtitulo 3", result.getSubtitulo());
        assertEquals("Contenido 3", result.getContenido());
        assertEquals("Categoria 3", result.getCategoria());
        assertArrayEquals("archivo3.pdf".getBytes(), result.getArchivoAdjunto());
        assertEquals("http://example.com/publicacion3", result.getUrlPublicacion());
        assertEquals(fechPublicacion, result.getFechPublicacion());
        assertEquals(3, result.getIdAdmin());
    }

    @Test
    void testObtenerPublicacionesOrdenadasVacia() {
        when(publicacionRepository.findAllByOrderByFechPublicacionDesc()).thenReturn(Arrays.asList());

        List<publicacion> result = publicacionService.obtenerPublicacionesOrdenadas();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEliminarPublicacionNoExistente() {
        doNothing().when(publicacionRepository).deleteById(1);

        publicacionService.eliminarPublicacion(1);

        verify(publicacionRepository, times(1)).deleteById(1);
    }
}