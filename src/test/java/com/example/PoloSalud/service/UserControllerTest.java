package com.example.PoloSalud.service;

import com.example.controller.UserController;
import com.example.model.Rol;
import com.example.model.usuario;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowLoginPage() {
        ResponseEntity<String> response = userController.showLoginPage();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("login", response.getBody());
    }

    @Test
    void testLoginUserSuccess() {
        String email = "test@example.com";
        String password = "password";
        usuario user = new usuario();
        user.setCorreoElectronico(email);
        user.setContraseña(password);

        when(userService.findByEmail(email)).thenReturn(user);
        when(userService.validatePassword(email, password)).thenReturn(true);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Map<String, String>> response = userController.loginUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
        assertEquals("Inicio de sesión exitoso", response.getBody().get("message"));
    }

    @Test
    void testLoginUserInvalidCredentials() {
        String email = "test@example.com";
        String password = "wrongpassword";
        usuario user = new usuario();
        user.setCorreoElectronico(email);
        user.setContraseña(password);

        when(userService.findByEmail(email)).thenReturn(user);
        when(userService.validatePassword(email, password)).thenReturn(false);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Map<String, String>> response = userController.loginUser(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
        assertEquals("Credenciales inválidas", response.getBody().get("message"));
    }

    @Test
    void testRegisterUserSuccess() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userService.saveUser(user)).thenReturn(user);

        ResponseEntity<Map<String, String>> response = userController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
        assertEquals("Usuario registrado exitosamente", response.getBody().get("message"));
    }

    @Test
    void testRegisterUserFailure() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userService.saveUser(user)).thenThrow(new RuntimeException("Error al registrar usuario"));

        ResponseEntity<Map<String, String>> response = userController.registerUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
        assertEquals("Error al registrar usuario", response.getBody().get("message"));
    }

    @Test
    void testHomeUnauthorized() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<String> response = userController.home();

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Acceso no autorizado", response.getBody());
    }

    @Test
    void testHomeAuthorized() {
        String email = "test@example.com";
        usuario user = new usuario();
        user.setCorreoElectronico(email);

        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<String> response = userController.home();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("index", response.getBody());
    }

    @Test
    void testLogout() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Void> response = userController.logout();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("/polo/login", response.getHeaders().get("Location").get(0));
    }

    @Test
    void testListAllUsers() {
        usuario user1 = new usuario();
        usuario user2 = new usuario();
        List<usuario> users = Arrays.asList(user1, user2);

        when(userService.listAllUser()).thenReturn(users);

        ResponseEntity<List<usuario>> response = userController.listAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}