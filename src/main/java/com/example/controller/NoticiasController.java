package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticiasController {

    @GetMapping("/noticias")
    public String mostrarNoticias() {
        return "PagNoticias";
    }
}