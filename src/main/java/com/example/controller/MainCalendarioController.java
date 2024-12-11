package com.example.controller;

import jakarta.servlet.http.HttpSession;
import com.example.model.usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainCalendarioController {

    @GetMapping("/calendario")
    public String mostrarCalendario(HttpSession session) {
        // Verificar si el usuario está autenticado
        usuario user = (usuario) session.getAttribute("user");

        if (user == null) {
            // Si no está autenticado, mostrar el calendario normal
            return "calendarioUsuario";
        } else {
            // Si está autenticado, verificar el rol por su ID
            if (user.getRol().getIdRol() == 1) { // Verificar si el ID del rol es 1 (administrador)
                // Si es administrador, mostrar el calendario de administrador
                return "calendarioAdmin";
            } else {
                // Si no es administrador, mostrar el calendario normal
                return "calendarioUsuario";
            }
        }
    }

    @GetMapping("/xd")
    public String xd() {
        return "xd";
    }
}