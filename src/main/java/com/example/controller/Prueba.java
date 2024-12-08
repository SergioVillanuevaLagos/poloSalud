package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Prueba {

    @GetMapping("/index1")
    public String Prueba1() {
        return "index1";
    }
}
