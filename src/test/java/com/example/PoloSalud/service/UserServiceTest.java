package com.example.PoloSalud.service;

import com.example.model.usuario;
import com.example.repositorio.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserSuccess() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userRepository.existsByCorreoElectronico(user.getCorreoElectronico())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        usuario savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getCorreoElectronico());
    }

    @Test
    void testSaveUserEmailAlreadyExists() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userRepository.existsByCorreoElectronico(user.getCorreoElectronico())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            userService.saveUser(user);
        });
    }

    @Test
    void testFindByEmail() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userRepository.findByCorreoElectronico("test@example.com")).thenReturn(user);

        usuario foundUser = userService.findByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getCorreoElectronico());
    }

    @Test
    void testListAllUser() {
        usuario user1 = new usuario();
        user1.setCorreoElectronico("test1@example.com");
        user1.setContraseña("password1");

        usuario user2 = new usuario();
        user2.setCorreoElectronico("test2@example.com");
        user2.setContraseña("password2");

        List<usuario> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<usuario> usersObtenidos = userService.listAllUser();

        assertNotNull(usersObtenidos);
        assertEquals(2, usersObtenidos.size());
    }

    @Test
    void testValidatePasswordSuccess() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userRepository.findByCorreoElectronico("test@example.com")).thenReturn(user);

        boolean isValid = userService.validatePassword("test@example.com", "password");

        assertTrue(isValid);
    }

    @Test
    void testValidatePasswordWrongPassword() {
        usuario user = new usuario();
        user.setCorreoElectronico("test@example.com");
        user.setContraseña("password");

        when(userRepository.findByCorreoElectronico("test@example.com")).thenReturn(user);

        boolean isValid = userService.validatePassword("test@example.com", "wrongpassword");

        assertFalse(isValid);
    }

    @Test
    void testValidatePasswordUserNotFound() {
        when(userRepository.findByCorreoElectronico("test@example.com")).thenReturn(null);

        boolean isValid = userService.validatePassword("test@example.com", "password");

        assertFalse(isValid);
    }
}