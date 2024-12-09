package com.example.controller;


import com.example.model.publicacion;
import com.example.service.publicacionService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private publicacionService1 publicacionService;
    @GetMapping("/")
    public String index(Model model) {
        List<publicacion> publicaciones = publicacionService.obtenerPublicacionesOrdenadas();
        model.addAttribute("publicaciones", publicaciones);
        return "index";
    }

    @GetMapping("/polosalud/index")
    public String polosaludIndex(Model model) {
        List<publicacion> publicaciones = publicacionService.obtenerPublicacionesOrdenadas();
        model.addAttribute("publicaciones", publicaciones);
        return "index";
    }

    @GetMapping
    public String listarPublicaciones(Model model) {
        List<publicacion> publicaciones = publicacionService.obtenerPublicacionesOrdenadas();
        model.addAttribute("publicaciones", publicaciones);
        return "listarPublicaciones"; // Asegúrate de tener un archivo listarPublicaciones.html en la carpeta templates
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaPublicacion(Model model) {
        model.addAttribute("publicacion", new publicacion());
        return "formularioPublicacion"; // Asegúrate de tener un archivo formularioPublicacion.html en la carpeta templates
    }

    @PostMapping("/guardar")
    public String guardarPublicacion(@ModelAttribute publicacion publicacion) {
        publicacionService.guardarPublicacion(publicacion);
        return "redirect:/publicaciones";
    }

    @GetMapping("/{id}")
    public String verPublicacion(@PathVariable Integer id, Model model) {
        publicacion publicacion = publicacionService.obtenerPublicacionPorId(id);
        model.addAttribute("publicacion", publicacion);
        return "verPublicacion"; // Asegúrate de tener un archivo verPublicacion.html en la carpeta templates
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPublicacion(@PathVariable Integer id) {
        publicacionService.eliminarPublicacion(id);
        return "redirect:/publicaciones";
    }
}