package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.LivroRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LivroRowMapperConfig {
    @Bean
    public LivroRowMapper livroRowMapper() {
         return new LivroRowMapper();
    }
}