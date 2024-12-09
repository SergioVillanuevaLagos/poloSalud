package com.example.controller;

import com.example.service.eventoService;
import com.example.controller.eventoController;
import com.example.model.evento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class EventoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private eventoService eventoService;

    @InjectMocks
    private eventoController eventoController;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(eventoController).build();
    }

    @Test
    void siInvocoListarEventosDebeDevolverStatusOkYListaDeEventos() throws Exception {
        // Given
        List<evento> eventos = new ArrayList<>();
        evento evento1 = new evento();
        evento1.setID_evento(1);
        evento1.setDescripcion("Evento 1");
        evento1.setFecha_evento(LocalDateTime.of(2023, 10, 10, 10, 0));
        evento1.setDireccion("Direccion 1");
        evento1.setNotificacion("Notificacion 1");
        eventos.add(evento1);

        given(eventoService.obtenerTodosLosEventos()).willReturn(eventos);

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/eventos/listar")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        List<Map<String, Object>> eventosFormato = eventos.stream().map(evento -> {
            Map<String, Object> eventoMap = new HashMap<>();
            eventoMap.put("id", evento.getID_evento());
            eventoMap.put("title", evento.getDescripcion());
            eventoMap.put("start", evento.getFecha_evento().toString());
            eventoMap.put("descripcion", evento.getDescripcion());
            eventoMap.put("direccion", evento.getDireccion());
            eventoMap.put("fechaEvento", evento.getFecha_evento().toString());
            eventoMap.put("notificacion", evento.getNotificacion());
            return eventoMap;
        }).collect(Collectors.toList());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(objectMapper.writeValueAsString(eventosFormato), response.getContentAsString());
    }

    @Test
    void siInvocoEliminarEventoPorIdDebeDevolverStatusNoContent() throws Exception {
        // Given
        doNothing().when(eventoService).eliminarEvento(1);

        // When
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/eventos/eliminar/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /*lol */

}