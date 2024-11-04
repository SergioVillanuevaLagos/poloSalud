package com.example.PoloSalud.service;

import com.example.model.usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        usuario user = new usuario();
        user.setIdUsuario(1);
        user.setCorreoElectronico("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        usuario savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getIdUsuario(), savedUser.getIdUsuario());
        assertEquals(user.getCorreoElectronico(), savedUser.getCorreoElectronico());
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        usuario user = new usuario();
        user.setIdUsuario(1);
        user.setCorreoElectronico(email);

        when(userRepository.findByCorreoElectronico(email)).thenReturn(user);

        usuario foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(user.getIdUsuario(), foundUser.getIdUsuario());
        assertEquals(user.getCorreoElectronico(), foundUser.getCorreoElectronico());
    }

    @Test
    public void testListAllUser() {
        usuario user1 = new usuario();
        user1.setIdUsuario(1);
        user1.setCorreoElectronico("test1@example.com");

        usuario user2 = new usuario();
        user2.setIdUsuario(2);
        user2.setCorreoElectronico("test2@example.com");

        List<usuario> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<usuario> allUsers = userService.listAllUser();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals(user1.getIdUsuario(), allUsers.get(0).getIdUsuario());
        assertEquals(user2.getIdUsuario(), allUsers.get(1).getIdUsuario());
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 1;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testUpdateUser() {
        usuario user = new usuario();
        user.setIdUsuario(1);
        user.setCorreoElectronico("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        usuario updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(user.getIdUsuario(), updatedUser.getIdUsuario());
        assertEquals(user.getCorreoElectronico(), updatedUser.getCorreoElectronico());
    }
}