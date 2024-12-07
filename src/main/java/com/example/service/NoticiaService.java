package com.example.service;

import com.example.model.publicacion;
import java.util.Date;
import java.util.List;

public interface NoticiaService {

    public publicacion crearNoticia(String titulo, String subtitulo, String contenido, String categoria, String archivoAdjunto, String urlPublicacion, Date fechPublicacion, Integer idAdmin);
    List<publicacion> obtenerTodasLasNoticias();
    publicacion obtenerNoticiaPorId(Integer id);
    publicacion actualizarNoticia(Integer id, String titulo, String contenido, String categoria, String archivoAdjunto, String urlPublicacion, Date fechPublicacion, Integer idAdmin);
    publicacion actualizarTitulo(Integer id, String titulo);
    publicacion actualizarSubtituloYContenido(Integer id, String subtitulo, String contenido);
    void eliminarNoticia(Integer id);
}