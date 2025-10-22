package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.adapter.output.repository.rowMapper.EmprestimoRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmprestimoRowMapperConfig {
    @Bean
    public EmprestimoRowMapper emprestimoRowMapper() {
         return new EmprestimoRowMapper();
    }
}