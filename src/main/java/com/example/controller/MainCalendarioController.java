package com.example.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainCalendarioController {

    @GetMapping("/calendario")
    public String mostrarCalendario() {
        return "calendarioAdmin";
    }

    @GetMapping("/calendarioUsuario")
    public String mostrarCalendarioUsurio() {
        return "calendarioUsuario";
    }

}
