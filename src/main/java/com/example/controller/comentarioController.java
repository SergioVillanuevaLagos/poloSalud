package com.example.controller;

import com.example.model.comentario;
import com.example.model.usuario;
import com.example.service.ComentarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Map<String, Object>> crearComentario(@RequestBody Map<String, String> params, HttpSession session) {
        // Verificar si el usuario está autenticado
        usuario usuarioAutenticado = (usuario) session.getAttribute("user");
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String contenido = params.get("contenido");
        int idPublicacion = Integer.parseInt(params.get("idPublicacion"));
        int idUsuario = usuarioAutenticado.getIdUsuario(); // Usar el ID del usuario autenticado

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
    public ResponseEntity<Void> eliminarComentario(@PathVariable int id, HttpSession session) {
        // Verificar si el usuario está autenticado
        usuario usuarioAutenticado = (usuario) session.getAttribute("user");
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Verificar si el usuario tiene el rol de administrador (idRol == 1)
        if (usuarioAutenticado.getRol().getIdRol() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }
}