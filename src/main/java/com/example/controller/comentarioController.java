package com.example.controller;

import com.example.model.comentario;
import com.example.service.ComentarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comentarios")
public class comentarioController {

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @PostMapping("/crear")
    public ResponseEntity<comentario> crearComentario(@RequestBody Map<String, String> params) {
        String contenido = params.get("contenido");
        int idPublicacion = Integer.parseInt(params.get("idPublicacion"));
        int idUsuario = Integer.parseInt(params.get("idUsuario"));

        comentario nuevoComentario = comentarioService.crearComentario(contenido, idPublicacion, idUsuario);
        return ResponseEntity.ok(nuevoComentario);
    }

   

    @GetMapping("/listar/{idPublicacion}")
    public ResponseEntity<List<Map<String, Object>>> obtenerComentariosPorPublicacion(@PathVariable int idPublicacion) {
        List<comentario> comentarios = comentarioService.obtenerComentariosPorPublicacion(idPublicacion);

        // Formatear la respuesta para incluir el nombre del usuario
        List<Map<String, Object>> comentariosFormato = comentarios.stream().map(comentario -> {
            Map<String, Object> comentarioMap = new HashMap<>();
            comentarioMap.put("id", comentario.getIdComenentario());
            comentarioMap.put("contenido", comentario.getComentario());
            comentarioMap.put("fecha", comentario.getCreaComentario().toString());
            comentarioMap.put("idPublicacion", comentario.getPublicacion().getIdPublicacion());
            comentarioMap.put("idUsuario", comentario.getUsuario().getIdUsuario());
            comentarioMap.put("usuario", comentario.getUsuario().getNombreUsuario()); // Incluir el nombre del usuario
            return comentarioMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(comentariosFormato);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable int id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }
}