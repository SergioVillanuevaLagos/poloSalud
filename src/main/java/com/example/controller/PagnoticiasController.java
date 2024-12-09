package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.noticia;
import com.example.repositorio.NoticiaRepository;

@Controller
public class PagnoticiasController {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @GetMapping("/noticia")
    public String mostrarPagNoticias(@RequestParam("id") Integer id, Model model) {
        noticia noticia = noticiaRepository.findById(id).orElse(null);
        if (noticia != null) {
            model.addAttribute("noticia", noticia);
        }
        return "PagNoticias";
    }
}