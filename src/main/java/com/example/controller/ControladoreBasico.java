package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/polosalud")
public class ControladoreBasico {

    @GetMapping(path = {"/index"})
    public String Comienzo(Model model) {
        // Puedes añadir datos al modelo si es necesario
        model.addAttribute("mensaje", "Bienvenido al Polo de Salud");
        return "index";
    }

    @GetMapping(path = {"/login"})
    public String login(Model model) {
        // Puedes añadir datos al modelo si es necesario
        model.addAttribute("mensaje", "Inicia sesión para continuar");
        return "login";
    }
    @GetMapping(path = {"/nav"})
    public String nav(Model model) {
        // Puedes añadir datos al modelo si es necesario
        model.addAttribute("mensaje", "prueba del nav");
        return "nav";
    }
}