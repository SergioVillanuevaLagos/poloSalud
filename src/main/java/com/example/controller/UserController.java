package com.example.controller;

import com.example.model.usuario;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testLoginUser_Success() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        usuario foundUser = new usuario();
        foundUser.setCorreoElectronico("test@example.com");

        // Configurar el comportamiento de los métodos mockeados
        when(userService.findByEmail(user.getCorreoElectronico())).thenReturn(foundUser);
        when(userService.validatePassword(user.getCorreoElectronico(), user.getContraseña())).thenReturn(true);

        ResponseEntity<Map<String, String>> response = userController.loginUser(user);

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("wrongpassword");

        // Configurar el comportamiento de los métodos mockeados
        when(userService.findByEmail(user.getCorreoElectronico())).thenReturn(null);

        ResponseEntity<Map<String, String>> response = userController.loginUser(user);

        // Verificar el resultado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
    }

    @Test
    public void testLogout() {
        ResponseEntity<Void> response = userController.logout();

        // Verificar el resultado
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("/polo/login", response.getHeaders().getLocation().toString());
    }

    @Test
    public void testListAllUsers() {
        List<usuario> users = List.of(new usuario(), new usuario());
        
        // Configurar el comportamiento del servicio
        when(userService.listAllUser()).thenReturn(users);

        ResponseEntity<List<usuario>> response = userController.listAllUsers();

        // Verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users.size(), response.getBody().size());
    }
}
