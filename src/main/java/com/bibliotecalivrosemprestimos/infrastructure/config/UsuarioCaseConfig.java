package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.core.UseCase.UsuarioUseCase;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioCaseConfig {

    @Bean
    public UsuarioInputPort usuarioInputPort(UsuarioOutputPort usuarioOutputPort, UsuarioMapper usuarioMapper) {
            return new UsuarioUseCase(usuarioOutputPort, usuarioMapper);
    }
}