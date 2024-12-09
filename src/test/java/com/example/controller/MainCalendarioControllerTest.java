package com.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MainCalendarioControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MainCalendarioController mainCalendarioController;

    @BeforeEach
    void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(mainCalendarioController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void siInvocoMostrarCalendarioDebeDevolverVistaCalendarioAdmin() throws Exception {
        mockMvc.perform(get("/calendario"))
                .andExpect(status().isOk())
                .andExpect(view().name("calendarioAdmin"));
    }

    @Test
    void siInvocoMostrarCalendarioUsuarioDebeDevolverVistaCalendarioUsuario() throws Exception {
        mockMvc.perform(get("/calendarioUsuario"))
                .andExpect(status().isOk())
                .andExpect(view().name("calendarioUsuario"));
    }

    @Test
    void siInvocoXdDebeDevolverVistaXd() throws Exception {
        mockMvc.perform(get("/xd"))
                .andExpect(status().isOk())
                .andExpect(view().name("xd"));
    }

    @Test
    void siInvocoRutaInexistenteDebeDevolverError404() throws Exception {
        mockMvc.perform(get("/rutaInexistente"))
                .andExpect(status().isNotFound());
    }

    @Test
    void siInvocoMostrarCalendarioConParametroDebeDevolverVistaCalendarioAdmin() throws Exception {
        mockMvc.perform(get("/calendario").param("param", "value"))
                .andExpect(status().isOk())
                .andExpect(view().name("calendarioAdmin"));
    }

    @Test
    void siInvocoMostrarCalendarioUsuarioConParametroDebeDevolverVistaCalendarioUsuario() throws Exception {
        mockMvc.perform(get("/calendarioUsuario").param("param", "value"))
                .andExpect(status().isOk())
                .andExpect(view().name("calendarioUsuario"));
    }

    @Test
    void siInvocoXdConParametroDebeDevolverVistaXd() throws Exception {
        mockMvc.perform(get("/xd").param("param", "value"))
                .andExpect(status().isOk())
                .andExpect(view().name("xd"));
    }

    @Test
    void siInvocoMostrarCalendarioConMetodoPostDebeDevolverError405() throws Exception {
        mockMvc.perform(post("/calendario"))
                .andExpect(status().isMethodNotAllowed());
    }
}