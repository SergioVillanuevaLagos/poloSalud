package com.example.PoloSalud.service;

import com.example.model.publicacion;
import com.example.repositorio.PublicacionRepository;
import com.example.service.PublicacionService;
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
public class PublicacionServiceTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionService publicacionService;

    private publicacion publicacion1;
    private publicacion publicacion2;

    @BeforeEach
    void setUp() {
        LocalDate date1 = LocalDate.of(2023, 10, 1);
        LocalDate date2 = LocalDate.of(2023, 10, 2);

        publicacion1 = new publicacion();
        publicacion1.setIdPublicacion(1);
        publicacion1.setFechPublicacion(Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        publicacion2 = new publicacion();
        publicacion2.setIdPublicacion(2);
        publicacion2.setFechPublicacion(Date.from(date2.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    void testGetAllPublicaciones() {
        List<publicacion> publicaciones = Arrays.asList(publicacion1, publicacion2);
        when(publicacionRepository.findAll()).thenReturn(publicaciones);

        List<publicacion> result = publicacionService.getAllPublicaciones();

        assertEquals(2, result.size());
        assertEquals(publicacion1, result.get(0));
        assertEquals(publicacion2, result.get(1));
    }

    @Test
    void testGetPublicacionById() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.of(publicacion1));

        Optional<publicacion> result = publicacionService.getPublicacionById(1);

        assertTrue(result.isPresent());
        assertEquals(publicacion1, result.get());
    }

    @Test
    void testGetPublicacionByIdNotFound() {
        when(publicacionRepository.findById(1)).thenReturn(Optional.empty());

        Optional<publicacion> result = publicacionService.getPublicacionById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreatePublicacion() {
        when(publicacionRepository.save(publicacion1)).thenReturn(publicacion1);

        publicacion result = publicacionService.createPublicacion(publicacion1);

        assertNotNull(result);
        assertEquals(publicacion1, result);
    }

    @Test
    void testUpdatePublicacion() {
        publicacion updatedPublicacion = new publicacion();
        updatedPublicacion.setIdPublicacion(1);
        updatedPublicacion.setFechPublicacion(Date.from(LocalDate.of(2023, 10, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(publicacionRepository.existsById(1)).thenReturn(true);
        when(publicacionRepository.save(updatedPublicacion)).thenReturn(updatedPublicacion);

        publicacion result = publicacionService.updatePublicacion(1, updatedPublicacion);

        assertNotNull(result);
        assertEquals(updatedPublicacion, result);
    }

    @Test
    void testUpdatePublicacionNotFound() {
        publicacion updatedPublicacion = new publicacion();
        updatedPublicacion.setIdPublicacion(1);
        updatedPublicacion.setFechPublicacion(Date.from(LocalDate.of(2023, 10, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(publicacionRepository.existsById(1)).thenReturn(false);

        publicacion result = publicacionService.updatePublicacion(1, updatedPublicacion);

        assertNull(result);
    }

    @Test
    void testDeletePublicacion() {
        doNothing().when(publicacionRepository).deleteById(1);

        publicacionService.deletePublicacion(1);

        verify(publicacionRepository, times(1)).deleteById(1);
    }
}