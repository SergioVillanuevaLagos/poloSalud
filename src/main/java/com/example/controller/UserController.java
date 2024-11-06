package com.example.controller;

import com.example.model.Rol;
import com.example.model.usuario;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polo")
public class UserController {

    @Autowired
    private UserService userService;

    // Método para obtener el HttpServletRequest desde el contexto
    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @GetMapping("/login")
    public ResponseEntity<String> showLoginPage() {
        return ResponseEntity.ok("login"); // Retorna la vista de login
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody usuario user) {
        HttpSession session = getCurrentRequest().getSession(); // Obtiene la sesión desde HttpServletRequest
        try {
            usuario foundUser = userService.findByEmail(user.getCorreoElectronico());
            Map<String, String> response = new HashMap<>();
            if (foundUser != null && userService.validatePassword(user.getCorreoElectronico(), user.getContraseña())) {
                session.setAttribute("user", foundUser);
                response.put("status", "success");
                response.put("message", "Inicio de sesión exitoso");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Credenciales inválidas");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody usuario user) {
        try {
            // Asignar rol por defecto y departamento nulo
            user.setRol(new Rol(2, "usuario")); // Suponiendo que el ID del rol de usuario es 2
            user.setIdDepartamento(null);

            userService.saveUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Usuario registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/index")
    public ResponseEntity<String> home() {
        HttpSession session = getCurrentRequest().getSession();
        usuario user = (usuario) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso no autorizado");
        }
        return ResponseEntity.ok("index"); // Retorna la vista de inicio después de iniciar sesión
    }
    //configuracion de la salida de seccion
    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        HttpSession session = getCurrentRequest().getSession();
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Sesión cerrada exitosamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<usuario>> listAllUsers() {
        List<usuario> users = userService.listAllUser();
        return ResponseEntity.ok(users);
    }
}