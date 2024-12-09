package com.example.controller;

import com.example.service.publicacionService1;
import com.example.model.publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListaNoticiasController {

    @Autowired
    private publicacionService1 publicacionService1;

    @GetMapping("/polosalud/listanoticias")
    public String listaNoticias(Model model) {
        List<publicacion> publicaciones = publicacionService1.obtenerPublicacionesOrdenadas();
        model.addAttribute("publicaciones", publicaciones);
        return "listanoticias";
    }
}