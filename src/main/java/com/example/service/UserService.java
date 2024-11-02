package com.example.service;

import com.example.model.usuario;
import com.example.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public usuario saveUser(usuario user) {
        if (userRepository.existsByCorreoElectronico(user.getCorreoElectronico())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        return userRepository.save(user);
    }

    public usuario findByEmail(String email) {
        return userRepository.findByCorreoElectronico(email);
    }

    public List<usuario> listAllUser() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public usuario updateUser(usuario user) {
        return userRepository.save(user);
    }

    public boolean validatePassword(String email, String password) {
        usuario user = userRepository.findByCorreoElectronico(email);
        if (user != null) {
            return user.getContraseña().equals(password);
        }
        return false;
    }
}