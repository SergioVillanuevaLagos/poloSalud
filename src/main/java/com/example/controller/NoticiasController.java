package com.example.controller;

import com.example.model.publicacion;
import com.example.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiasController {

    @Autowired
    private NoticiaService noticiaService;

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarNoticia(@PathVariable("id") Integer id) {
        System.out.println("ID recibido en el backend: " + id); // Depuración
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID de noticia no válido.");
        }
        noticiaService.eliminarNoticia(id);
        return ResponseEntity.ok("Noticia eliminada con éxito.");
    }

    // Resto de métodos (sin cambios)
    @GetMapping("/todas")
    public List<publicacion> obtenerTodasLasNoticias() {
        return noticiaService.obtenerTodasLasNoticias();
    }

    @PostMapping("/crear")
    public publicacion crearNoticia(
            @RequestParam("titulo") String titulo,
            @RequestParam("subtitulo") String subtitulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("categoria") String categoria,
            @RequestParam("archivoAdjunto") MultipartFile archivoAdjunto,
            @RequestParam("urlPublicacion") String urlPublicacion,
            @RequestParam("fechPublicacion") String fechPublicacionStr,
            @RequestParam("idAdmin") Integer idAdmin) {

        // Convertir fecha de String a Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechPublicacion = null;
        try {
            fechPublicacion = sdf.parse(fechPublicacionStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] archivoBytes = null;
        try {
            archivoBytes = archivoAdjunto.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return noticiaService.crearNoticia(
                titulo, subtitulo, contenido, categoria, archivoBytes, urlPublicacion, fechPublicacion, idAdmin
        );
    }
}
