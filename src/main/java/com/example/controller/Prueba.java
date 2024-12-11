package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prueba {

    @GetMapping("/index1")
    public String Prueba1(HttpSession session) {
        // Verificar si el usuario está autenticado
        if (session.getAttribute("user") == null) {
            // Si no está autenticado, redirigir a la página de inicio de sesión
            return "redirect:/polosalud/login"; // Cambia la ruta según tu configuración
        }
        // Si está autenticado, permitir el acceso a la vista index1
        return "index1";
    }
}