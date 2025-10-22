package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.UsuarioRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioRowMapperConfig {
    @Bean
    public UsuarioRowMapper usuarioRowMapper() {
         return new UsuarioRowMapper();
    }
}