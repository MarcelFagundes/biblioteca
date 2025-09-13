package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.core.UseCase.LivroUseCase;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LivroCaseConfig {

    private UsuarioOutputPort usuarioOutputPort;

    @Bean
    public LivroInputPort livroInputPort(LivroOutputPort livroOutputPort, UsuarioOutputPort usuarioOutputPort) {
        this.usuarioOutputPort = usuarioOutputPort;
        return new LivroUseCase(livroOutputPort);
    }
}