package com.bibliotecalivrosemprestimos.adapter.input.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String health() {
        return "API está funcionando!";
    }
}