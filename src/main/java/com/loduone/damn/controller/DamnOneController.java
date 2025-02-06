package com.loduone.damn.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DamnOneController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the Spring Boot Web Server!";
    }
    @GetMapping("/endpdint1")
    public String endpintone() {
        return "Welcome to the endpoint1!";
    }
}
