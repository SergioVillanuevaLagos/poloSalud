package com.example.PoloSalud;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hola")
public class ControladoreBasico {

    @GetMapping(path = {"/index"})
    public String Comienzo() {

        return "index";
}
}