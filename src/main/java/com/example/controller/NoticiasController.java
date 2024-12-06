package com.example.controller;

import com.example.model.publicacion;
import com.example.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {

    private static final Logger logger = LoggerFactory.getLogger(NoticiasController.class);

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping
    public String mostrarNoticias() {
        return "PagNoticias";
    }

    @PostMapping("/crear")
    @ResponseBody
    public publicacion crearNoticia(@RequestBody publicacion noticia) {
        logger.debug("Recibiendo solicitud para crear noticia: {}", noticia);
        return noticiaService.crearNoticia(noticia.getTitulo(), noticia.getContenido(), noticia.getCategoria(), noticia.getArchivoAdjunto(), noticia.getUrlPublicacion(), noticia.getFechPublicacion(), noticia.getIdAdmin());
    }

    @GetMapping("/listar")
    @ResponseBody
    public List<publicacion> obtenerTodasLasNoticias() {
        return noticiaService.obtenerTodasLasNoticias();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public publicacion obtenerNoticiaPorId(@PathVariable Integer id) {
        return noticiaService.obtenerNoticiaPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseBody
    public publicacion actualizarNoticia(@PathVariable Integer id, @RequestBody publicacion noticia) {
        return noticiaService.actualizarNoticia(id, noticia.getTitulo(), noticia.getContenido(), noticia.getCategoria(), noticia.getArchivoAdjunto(), noticia.getUrlPublicacion(), noticia.getFechPublicacion(), noticia.getIdAdmin());
    }

    @PutMapping("/actualizarTitulo/{id}")
    @ResponseBody
    public publicacion actualizarTitulo(@PathVariable Integer id, @RequestBody String titulo) {
        return noticiaService.actualizarTitulo(id, titulo);
    }

    @PutMapping("/actualizarSubtituloYContenido/{id}")
    @ResponseBody
    public publicacion actualizarSubtituloYContenido(@PathVariable Integer id, @RequestBody publicacion noticia) {
        return noticiaService.actualizarSubtituloYContenido(id, noticia.getSubtitulo(), noticia.getContenido());
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public void eliminarNoticia(@PathVariable Integer id) {
        noticiaService.eliminarNoticia(id);
    }
}