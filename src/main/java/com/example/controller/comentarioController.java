package com.example.controller;

import com.example.model.comentario;
import com.example.service.ComentarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable int id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para responder un comentario
    @PostMapping("/responder/{id}")
    public ResponseEntity<?> responderComentario(@PathVariable Integer id,@RequestBody Map<String, String> respuestaJson) {
        try {
            // Extraer el campo "respuesta" del JSON
            String respuesta = respuestaJson.get("respuesta");

            // Llama al servicio para guardar la respuesta
            comentarioService.responderComentario(id, respuesta);
            return ResponseEntity.ok("Respuesta enviada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al responder");
        }
    }

}
