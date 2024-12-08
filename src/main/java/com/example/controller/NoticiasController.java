package com.example.controller;

import com.example.model.publicacion;
import com.example.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {

    @Autowired
    private NoticiaService noticiaService;

    @PostMapping("/crear")
    @ResponseBody
    public publicacion crearNoticia(
            @RequestParam("titulo") String titulo,
            @RequestParam("subtitulo") String subtitulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("categoria") String categoria,
            @RequestParam("archivoAdjunto") MultipartFile archivoAdjunto,
            @RequestParam("urlPublicacion") String urlPublicacion,
            @RequestParam("fechPublicacion") String fechPublicacionStr, // Recibimos la fecha como String
            @RequestParam("idAdmin") Integer idAdmin) {

        // Convertir el String de fecha a Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // El formato debe coincidir con el de entrada
        Date fechPublicacion = null;
        try {
            fechPublicacion = sdf.parse(fechPublicacionStr); // Convertir String a Date
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja el error de conversión
        }

        // Convertir archivo adjunto a byte[]
        byte[] archivoBytes = null;
        try {
            archivoBytes = archivoAdjunto.getBytes(); // Obtener los bytes del archivo
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear la noticia y guardarla
        return noticiaService.crearNoticia(
                titulo, subtitulo, contenido, categoria, archivoBytes, urlPublicacion, fechPublicacion, idAdmin
        );
    }
}
