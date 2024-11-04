package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControladoreBasico {


    @GetMapping(path = {"/nav"})
    public String login(Model model) {
        // Puedes añadir datos al modelo si es necesario
        model.addAttribute("mensaje", "Inicia sesión para continuar");
        return "nav";
    }

}