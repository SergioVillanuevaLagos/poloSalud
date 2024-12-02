package com.example.service;

import com.example.model.comentario;
import com.example.model.publicacion;
import com.example.model.usuario;
import com.example.repository.comentarioRepository;
import com.example.repository.publicacionRepository;
import com.example.repository.usuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioServiceImpl implements comentarioService {

    @Autowired
    private comentarioRepository comentarioRepository;

    @Autowired
    private publicacionRepository publicacionRepository;

    @Autowired
    private usuarioRepository usuarioRepository;

    // Crear comentario
    @Override
    public comentario crearComentario(String contenido, int idPublicacion, int idUsuario) {
        // Validar existencia de la publicación
        publicacion publi = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada"));

        // Validar existencia del usuario
        usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Crear el comentario
        comentario nuevoComentario = new comentario();
        nuevoComentario.setComentario(contenido);
        nuevoComentario.setCreaComentario(LocalDateTime.now());
        nuevoComentario.setPublicacion(publi);
        nuevoComentario.setUsuario(user);

        return comentarioRepository.save(nuevoComentario);
    }

    // Eliminar comentario
    @Override
    public comentario eliminarComentario(int idComentario) {
        // Buscar el comentario
        comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));

        // Eliminar el comentario
        comentarioRepository.delete(comentario);
        return comentario; // Retornar el comentario eliminado
    }

    // Listar comentarios por publicación
    @Override
    public List<comentario> obtenerComentariosPorPublicacion(int idPublicacion) {
        // Validar existencia de la publicación
        publicacion publi = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada"));

        // Retornar los comentarios asociados a la publicación
        return comentarioRepository.findByPublicacion(publi);
    }
}
