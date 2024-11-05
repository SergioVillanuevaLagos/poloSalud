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

    // Obtener HttpServletRequest desde el contexto
    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody usuario user) {
        HttpSession session = getCurrentRequest().getSession();
        try {
            usuario foundUser = userService.findByEmail(user.getCorreoElectronico());
            Map<String, String> response = new HashMap<>();
            if (foundUser != null && userService.validatePassword(user.getCorreoElectronico(), user.getContraseña())) {
                session.setAttribute("user", foundUser);
                session.setMaxInactiveInterval(30 * 60); // Establece el tiempo de inactividad de sesión
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

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        HttpSession session = getCurrentRequest().getSession();
        session.invalidate();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/polo/login").build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<usuario>> listAllUsers() {
        List<usuario> users = userService.listAllUser();
        return ResponseEntity.ok(users);
    }
}
