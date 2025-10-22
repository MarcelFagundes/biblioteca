package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.adapter.output.repository.UsuarioRepository;
import com.bibliotecalivrosemprestimos.core.UseCase.UsuarioUseCase;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioCaseConfig {

    private LivroOutputPort livroOutputPort;

    @Bean
    public UsuarioInputPort usuarioInputPort(UsuarioOutputPort usuarioOutputPort, LivroOutputPort livroOutputPort) {
        this.livroOutputPort = livroOutputPort;
        return new UsuarioUseCase(usuarioOutputPort);
    }
}