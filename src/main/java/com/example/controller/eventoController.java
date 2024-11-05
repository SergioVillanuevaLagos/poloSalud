package com.example.controller;

import com.example.model.evento;
import com.example.service.eventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
public class eventoController {

    @Autowired
    private eventoService eventoService;

    @PostMapping("/crear")
    public ResponseEntity<evento> crearEvento(@RequestBody Map<String, String> params) {
        String descripcion = params.get("descripcion");
        String direccion = params.get("direccion");
        LocalDate fecha = LocalDate.parse(params.get("fechaEvento"));
        String notificacion = params.get("notificacion");
        int idAdmin = Integer.parseInt(params.get("idAdmin"));

        evento nuevoEvento = eventoService.crearEvento(descripcion, direccion, fecha, notificacion, idAdmin);
        return ResponseEntity.ok(nuevoEvento);
    }

    // Modificar la fecha de un evento existente
    @PutMapping("/modificarFecha/{id}")
    public ResponseEntity<evento> modificarFechaEvento(@PathVariable int id, @RequestBody LocalDate nuevaFecha) {
        evento eventoModificado = eventoService.modificarFechaEvento(id, nuevaFecha);
        if (eventoModificado != null) {
            return ResponseEntity.ok(eventoModificado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Obtener un evento por su ID
    @GetMapping("/obtener/{id}")
    public ResponseEntity<evento> obtenerEventoPorId(@PathVariable int id) {
        evento evento = eventoService.obtenerEventoPorId(id);
        if (evento != null) {
            return ResponseEntity.ok(evento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todos los eventos
    @GetMapping("/listar")
    public ResponseEntity<List<evento>> obtenerTodosLosEventos() {
        List<evento> eventos = eventoService.obtenerTodosLosEventos();
        return ResponseEntity.ok(eventos);
    }

    // Eliminar un evento por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable int id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }
}
