package com.example.controller;

import com.example.model.usuario;
import com.example.model.Rol;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polo")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("login"); // Retorna la vista de login
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody usuario user) {
        // Asignar rol por defecto y departamento nulo
        user.setRol(new Rol(2, "usuario")); // Suponiendo que el ID del rol de usuario es 2
        user.setIdDepartamento(null);

        userService.saveUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Usuario registrado exitosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Redirige a la página de inicio después de registrar
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody usuario user, HttpSession session) {
        usuario foundUser = userService.findByEmail(user.getCorreoElectronico());
        Map<String, String> response = new HashMap<>();
        if (foundUser != null && foundUser.getContraseña().equals(user.getContraseña())) {
            session.setAttribute("user", foundUser);
            response.put("status", "success");
            response.put("message", "Inicio de sesión exitoso");
            return ResponseEntity.ok(response); // Redirige a la página de inicio después de iniciar sesión
        } else {
            response.put("status", "error");
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/index")
    public ResponseEntity<String> home(HttpSession session) {
        usuario user = (usuario) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso no autorizado");
        }
        return ResponseEntity.ok("index"); // Retorna la vista de inicio después de iniciar sesión
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }

    @GetMapping("/users")
    public ResponseEntity<List<usuario>> listAllUsers() {
        List<usuario> users = userService.listAllUser();
        return ResponseEntity.ok(users);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + ex.getMessage());
    }
}