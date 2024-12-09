package com.example.controller;

import com.example.model.publicacion;
import com.example.service.publicacionService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/polosalud")
public class ControladoreBasico {

    @Autowired
    private publicacionService1 publicacionService1;

    @GetMapping(path = {"/index"})
    public String Comienzo(Model model) {
        List<publicacion> publicaciones = publicacionService1.obtenerPublicacionesOrdenadas();
        model.addAttribute("publicaciones", publicaciones);
        model.addAttribute("mensaje", "Bienvenido al Polo de Salud");
        return "index";
    }

    @GetMapping(path = {"/login"})
    public String login(Model model) {
        model.addAttribute("mensaje", "Inicia sesión para continuar");
        return "login";
    }

    @GetMapping(path = {"/nav"})
    public String nav(Model model) {
        model.addAttribute("mensaje", "prueba del nav");
        return "nav";
    }

    @GetMapping(path = {"/ini"})
    public String ini(Model model) {
        model.addAttribute("mensaje", "prueba del nav");
        return "inicio";
    }

    @GetMapping(path = {"/colab"})
    public String colab(Model model) {
        model.addAttribute("mensaje", "prueba del nav");
        return "componentes";
    }
    @GetMapping(path = {"/contac"})
    public String contac(Model model) {
        model.addAttribute("mensaje", "prueba del contactanos");
        return "contacto";
    }

    @GetMapping(path = {"/publicaciones/{id}"})
    public String verPublicacion(@PathVariable Integer id, Model model) {
        publicacion publicacion = publicacionService1.obtenerPublicacionPorId(id);
        model.addAttribute("publicacion", publicacion);
        return "verPublicacion"; // Asegúrate de tener un archivo verPublicacion.html en la carpeta templates
    }
}