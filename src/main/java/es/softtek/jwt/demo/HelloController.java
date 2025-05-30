package es.softtek.jwt.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hola")
    public String hola() {
        return "Hola, estás autenticado con JWT y BD!";
    }
}
