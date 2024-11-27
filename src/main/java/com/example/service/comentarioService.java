package com.example.service;

import java.util.List;

import com.example.model.comentario;

public interface comentarioService {
    
    //crear comentario
    comentario crearComentario(String contenido, int idPublicacion, int idUsuario);

    //eliminar
    comentario eliminarComentario(int idComentario);

    //listar comentarios por publicacion
    List<comentario> obtenerComentariosPorPublicacion(int idPublicacion);

}
