package com.example.controller;

import com.example.model.comentario;
import com.example.service.ComentarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comentarios")
public class comentarioController {

    @Autowired
    private ComentarioServiceImpl comentarioService;

    // Endpoint para crear un comentario
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crearComentario(@RequestBody Map<String, String> params) {
        String contenido = params.get("contenido");
        int idPublicacion = Integer.parseInt(params.get("idPublicacion"));
        int idUsuario = Integer.parseInt(params.get("idUsuario"));

        comentario nuevoComentario = comentarioService.crearComentario(contenido, idPublicacion, idUsuario);

        // Formatear la respuesta
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Map<String, Object> comentarioMap = new HashMap<>();
        comentarioMap.put("id", nuevoComentario.getIdComenentario());
        comentarioMap.put("contenido", nuevoComentario.getComentario());
        comentarioMap.put("fecha", nuevoComentario.getCreaComentario().format(formatter));
        comentarioMap.put("idPublicacion", nuevoComentario.getPublicacion().getIdPublicacion());
        comentarioMap.put("idUsuario", nuevoComentario.getUsuario().getIdUsuario());
        comentarioMap.put("usuario", nuevoComentario.getUsuario().getNombreUsuario());

        return ResponseEntity.ok(comentarioMap);
    }

    // Endpoint para listar comentarios por publicación
    @GetMapping("/listar/{idPublicacion}")
    public ResponseEntity<List<Map<String, Object>>> obtenerComentariosPorPublicacion(@PathVariable int idPublicacion) {
        List<comentario> comentarios = comentarioService.obtenerComentariosPorPublicacion(idPublicacion);

        // Formatear la respuesta
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<Map<String, Object>> comentariosFormato = comentarios.stream().map(comentario -> {
            Map<String, Object> comentarioMap = new HashMap<>();
            comentarioMap.put("id", comentario.getIdComenentario());
            comentarioMap.put("contenido", comentario.getComentario());
            comentarioMap.put("fecha", comentario.getCreaComentario().format(formatter));
            comentarioMap.put("idPublicacion", comentario.getPublicacion().getIdPublicacion());
            comentarioMap.put("idUsuario", comentario.getUsuario().getIdUsuario());
            comentarioMap.put("usuario", comentario.getUsuario().getNombreUsuario());
            return comentarioMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(comentariosFormato);
    }

    // Endpoint para eliminar un comentario
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable int id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }
}
