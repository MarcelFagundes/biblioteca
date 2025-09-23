package com.bibliotecalivrosemprestimos.adapter.input;

import com.bibliotecalivrosemprestimos.adapter.input.controller.EmprestimoController;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.core.UseCase.EmprestimoUseCase;
import com.bibliotecalivrosemprestimos.core.domain.model.Emprestimo;
import com.bibliotecalivrosemprestimos.core.domain.model.Livro;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoControllerTest {

    @Mock
    private EmprestimoUseCase service;

    @InjectMocks
    private EmprestimoController controller;

    @Test
    void criarEmprestimo_DeveRetornarEmprestimoCriado() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        EmprestimoRequest requestDTO = new EmprestimoRequest(null, 1L, "Dom Casmurro", 1L, "Jose Teste",now, now.plusDays(7), null);

        Usuario usuario = new Usuario("João Silva", "joao@email.com");
        Livro livro = new Livro( "Dom Casmurro", "Machado de Assis", "978-8535931231", 100);
        Emprestimo emprestimoSalvo = new Emprestimo(livro, usuario, now, now.plusDays(7), null);

        when(service.criarEmprestimo(any(Emprestimo.class))).thenReturn(emprestimoSalvo);

        // Act
        ResponseEntity<EmprestimoRequest> response = controller.criarEmprestimo(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().livroId());
        assertEquals("1", response.getBody().usuarioId());
        verify(service, times(1)).criarEmprestimo(any(Emprestimo.class));
    }

//    @Test
//    void buscarPorId_QuandoEmprestimoExiste_DeveRetornarEmprestimo() {
//        // Arrange
//        LocalDateTime now = LocalDateTime.now();
//        Usuario usuario = new Usuario("João Silva", "joao@email.com");
//        Livro livro = new Livro( "Dom Casmurro", "Machado de Assis", "978-8535931231",  200);
//        Emprestimo emprestimo = new Emprestimo(livro, usuario, now, now.plusDays(7), null);
//
//        when(service.buscarPorId(1L)).thenReturn(emprestimo);
//
//        // Act
//        ResponseEntity<EmprestimoRequest> response = controller.buscarPorId(1L);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("1", response.getBody().id());
//        assertEquals("1", response.getBody().livroId());
//    }

    @Test
    void listarEmprestimosAtivos_DeveRetornarListaDeEmprestimos() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Usuario usuario = new Usuario("João Silva", "joao@email.com");
        Livro livro = new Livro("978-8535931231", "Dom Casmurro", "Machado de Assis",  100);

        List<Emprestimo> emprestimos = Arrays.asList(
                new Emprestimo(livro, usuario, now, now.plusDays(7)),
                new Emprestimo(livro, usuario, now.minusDays(5), now.plusDays(2))
        );

        when(service.listarEmprestimos()).thenReturn(emprestimos);

        // Act
        ResponseEntity<List<EmprestimoRequest>> response = controller.listarEmprestimos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void devolverLivro_DeveRetornarEmprestimoAtualizado() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Usuario usuario = new Usuario("João Silva", "joao@email.com");
        Livro livro = new Livro("978-8535931231", "Dom Casmurro", "Machado de Assis",  100);
        Emprestimo emprestimoDevolvido = new Emprestimo(livro, usuario, now.minusDays(10), now.minusDays(3));

        when(service.registrarDevolucao(1L)).thenReturn(emprestimoDevolvido);

        // Act
        ResponseEntity<EmprestimoRequest> response = controller.registrarDevolucao(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().devolvidoEm());
    }
}