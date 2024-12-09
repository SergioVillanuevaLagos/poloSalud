package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagnoticiasController {

    @GetMapping("/noticia")
    public String mostrarPagNoticias() {
        return "PagNoticias"; // Nombre del archivo HTML sin la extensión
    }
}