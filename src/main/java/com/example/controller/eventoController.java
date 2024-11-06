package com.example.controller;

import com.example.model.evento;
import com.example.model.usuario;
import com.example.service.eventoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class eventoController {

    @Autowired
    private eventoService eventoService;

    // Crear un evento solo si el usuario autenticado tiene rol de administrador (id_rol = 1)
    @PostMapping("/crear")
    public ResponseEntity<Map<String, String>> crearEvento(@RequestBody Map<String, String> params, HttpSession session) {
        usuario user = (usuario) session.getAttribute("user"); // Obtener usuario de la sesión

        // Verificar que el usuario esté autenticado y que tenga rol de administrador
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Usuario no autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (user.getRol() == null || user.getRol().getIdRol() != 1) { // Verificar si el rol del usuario no es de administrador
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Acceso denegado. Solo los administradores pueden crear eventos.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Crear el evento ya que el usuario es administrador
        String descripcion = params.get("descripcion");
        String direccion = params.get("direccion");
        LocalDate fecha = LocalDate.parse(params.get("fechaEvento"));
        String notificacion = params.get("notificacion");

        // Usar el ID del usuario autenticado como idAdmin y asignar el estado a 1
        evento nuevoEvento = eventoService.crearEvento(descripcion, direccion, fecha, notificacion, user.getIdUsuario());

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Evento creado exitosamente");
        return ResponseEntity.ok(response);
    }

    // Listar todos los eventos
    @GetMapping("/listar")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosEventos() {
        List<evento> eventos = eventoService.obtenerTodosLosEventos();
        List<Map<String, Object>> eventosFormato = eventos.stream().map(evento -> {
            Map<String, Object> eventoMap = new HashMap<>();
            eventoMap.put("id", evento.getID_evento());
            eventoMap.put("descripcion", evento.getDescripcion());
            eventoMap.put("direccion", evento.getDireccion());
            eventoMap.put("fechaEvento", evento.getFecha_evento().toString());
            eventoMap.put("notificacion", evento.getNotificacion());
            return eventoMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(eventosFormato);
    }

    // Eliminar un evento solo si el usuario autenticado tiene rol de administrador (id_rol = 1)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarEvento(@PathVariable int id, HttpSession session) {
        usuario user = (usuario) session.getAttribute("user"); // Obtener usuario de la sesión

        // Verificar que el usuario esté autenticado y que tenga rol de administrador
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Usuario no autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (user.getRol() == null || user.getRol().getIdRol() != 1) { // Verificar si el rol del usuario no es de administrador
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Acceso denegado. Solo los administradores pueden eliminar eventos.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Eliminar el evento ya que el usuario es administrador
        eventoService.eliminarEvento(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Evento eliminado exitosamente");
        return ResponseEntity.ok(response);
    }
}
