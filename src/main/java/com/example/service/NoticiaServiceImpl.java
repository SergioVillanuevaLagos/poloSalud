package com.example.service;

import com.example.model.publicacion;
import com.example.repositorio.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    private static final Logger logger = LoggerFactory.getLogger(NoticiaServiceImpl.class);

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public publicacion crearNoticia(String titulo, String subtitulo, String contenido, String categoria, byte[] archivoAdjunto, String urlPublicacion, Date fechPublicacion, Integer idAdmin) {
        publicacion nuevaNoticia = new publicacion();
        nuevaNoticia.setTitulo(titulo);
        nuevaNoticia.setSubtitulo(subtitulo); // Asegúrate de asignar el subtitulo aquí
        nuevaNoticia.setContenido(contenido);
        nuevaNoticia.setCategoria(categoria);
        nuevaNoticia.setArchivoAdjunto(archivoAdjunto); // Almacenando el archivo como un BLOB (byte[])
        nuevaNoticia.setUrlPublicacion(urlPublicacion);
        nuevaNoticia.setFechPublicacion(fechPublicacion);
        nuevaNoticia.setIdAdmin(idAdmin);
        logger.debug("Guardando nueva noticia: {}", nuevaNoticia);
        return publicacionRepository.save(nuevaNoticia);
    }

    @Override
    public List<publicacion> obtenerTodasLasNoticias() {
        return publicacionRepository.findAll();
    }

    @Override
    public publicacion obtenerNoticiaPorId(Integer id) {
        return publicacionRepository.findById(id).orElse(null);
    }

    @Override
    public publicacion actualizarNoticia(Integer id, String titulo, String contenido, String categoria, byte[] archivoAdjunto, String urlPublicacion, Date fechPublicacion, Integer idAdmin) {
        publicacion noticiaExistente = publicacionRepository.findById(id).orElse(null);
        if (noticiaExistente != null) {
            noticiaExistente.setTitulo(titulo);
            noticiaExistente.setContenido(contenido);
            noticiaExistente.setCategoria(categoria);
            noticiaExistente.setArchivoAdjunto(archivoAdjunto); // Actualizando el archivo adjunto como BLOB
            noticiaExistente.setUrlPublicacion(urlPublicacion);
            noticiaExistente.setFechPublicacion(fechPublicacion);
            noticiaExistente.setIdAdmin(idAdmin);
            logger.debug("Actualizando noticia existente: {}", noticiaExistente);
            return publicacionRepository.save(noticiaExistente);
        }
        return null;
    }

    @Override
    public publicacion actualizarTitulo(Integer id, String titulo) {
        publicacion noticiaExistente = publicacionRepository.findById(id).orElse(null);
        if (noticiaExistente != null) {
            noticiaExistente.setTitulo(titulo);
            logger.debug("Actualizando título de la noticia: {}", noticiaExistente);
            return publicacionRepository.save(noticiaExistente);
        }
        return null;
    }

    @Override
    public publicacion actualizarSubtituloYContenido(Integer id, String subtitulo, String contenido) {
        publicacion noticiaExistente = publicacionRepository.findById(id).orElse(null);
        if (noticiaExistente != null) {
            noticiaExistente.setSubtitulo(subtitulo);
            noticiaExistente.setContenido(contenido);
            logger.debug("Actualizando subtítulo y contenido de la noticia: {}", noticiaExistente);
            return publicacionRepository.save(noticiaExistente);
        }
        return null;
    }

    @Override
    public void eliminarNoticia(Integer id) {
        logger.debug("Eliminando noticia con ID: {}", id);
        publicacionRepository.deleteById(id);
    }
}
