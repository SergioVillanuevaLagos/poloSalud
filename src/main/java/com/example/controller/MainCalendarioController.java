package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainCalendarioController {

    @GetMapping("/comentario")
    public String mostrarCalendario() {
        return "comentario";
    }
}