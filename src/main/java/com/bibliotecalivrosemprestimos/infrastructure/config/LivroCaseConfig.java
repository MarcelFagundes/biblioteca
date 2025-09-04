package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.core.UseCase.LivroUseCase;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LivroCaseConfig {

    @Bean
    public LivroInputPort livroInputPort(LivroOutputPort livroOutputPort) {
            return new LivroUseCase(livroOutputPort);
    }
}