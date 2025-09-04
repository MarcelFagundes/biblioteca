package com.bibliotecalivrosemprestimos.infrastructure.config;

import com.bibliotecalivrosemprestimos.core.UseCase.EmprestimoUseCase;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import com.bibliotecalivrosemprestimos.port.output.EmprestimoOutputPort;
import com.bibliotecalivrosemprestimos.port.output.LivroOutputPort;
import com.bibliotecalivrosemprestimos.port.output.UsuarioOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmprestimoCaseConfig {

    @Bean
    public EmprestimoInputPort emprestimoInputPort(
            EmprestimoOutputPort emprestimoOutputPort,
            LivroOutputPort livroOutputPort,
            UsuarioOutputPort usuarioOutputPort) {
            return new EmprestimoUseCase(emprestimoOutputPort, livroOutputPort, usuarioOutputPort);
    }
}