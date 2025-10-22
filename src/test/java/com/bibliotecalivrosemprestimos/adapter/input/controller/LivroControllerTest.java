package com.bibliotecalivrosemprestimos.adapter.input;

import com.bibliotecalivrosemprestimos.adapter.input.controller.LivroController;
import com.bibliotecalivrosemprestimos.adapter.input.mapper.LivroMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroComEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.LivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.AtualizarLivroRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarLivroRequest;
import com.bibliotecalivrosemprestimos.port.input.LivroInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroControllerTest {

    @Mock
    private LivroInputPort livroInputPort;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private LivroController livroController;

    private LivroRequest livroRequest;
    private CriarLivroRequest criarLivroRequest;
    private AtualizarLivroRequest atualizarLivroRequest;
    private LivroComEmprestimoRequest livroComEmprestimoRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        livroRequest = new LivroRequest(
                1L,
                "978-85-123-4567-8",
                "Dom Casmurro",
                "Machado de Assis",
                10,
                true
        );

        criarLivroRequest = new CriarLivroRequest(
                "978-85-123-4567-8",
                "Dom Casmurro",
                "Machado de Assis",
                10
        );

        atualizarLivroRequest = new AtualizarLivroRequest(
                "Dom Casmurro - Edição Especial",
                "Machado de Assis",
                "978-85-123-4567-8",
                10,
                true
        );

        livroComEmprestimoRequest = new LivroComEmprestimoRequest(
                1L,
                "Dom Casmurro",
                "Machado de Assis",
                "João Silva",
                null,
                null,
                null
        );
    }

    @Test
    void deveListarLivrosSemFiltros() {
        when(livroInputPort.listarLivros(null, null)).thenReturn(List.of(livroRequest));

        ResponseEntity<List<LivroRequest>> response = livroController.listarLivros(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(livroRequest, response.getBody().get(0));
        verify(livroInputPort, times(1)).listarLivros(null, null);
    }

    @Test
    void deveListarLivrosComFiltroTitulo() {
        String titulo = "Dom";
        when(livroInputPort.listarLivros(titulo, null)).thenReturn(List.of(livroRequest));

        ResponseEntity<List<LivroRequest>> response = livroController.listarLivros(titulo, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(livroInputPort, times(1)).listarLivros(titulo, null);
    }

    @Test
    void deveListarLivrosComFiltroAtivo() {
        Boolean ativo = true;
        when(livroInputPort.listarLivros(null, ativo)).thenReturn(List.of(livroRequest));

        ResponseEntity<List<LivroRequest>> response = livroController.listarLivros(null, ativo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(livroInputPort, times(1)).listarLivros(null, ativo);
    }

    @Test
    void deveCriarLivro() {
        when(livroMapper.toRequest(livroRequest)).thenReturn(criarLivroRequest);
        when(livroInputPort.criarLivro(criarLivroRequest)).thenReturn(livroRequest);

        ResponseEntity<LivroRequest> response = livroController.criarLivro(livroRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(livroRequest, response.getBody());
        verify(livroInputPort, times(1)).criarLivro(criarLivroRequest);
    }

    @Test
    void deveBuscarLivroPorId() {
        Long livroId = 1L;
        when(livroInputPort.buscarPorId(livroId)).thenReturn(livroRequest);

        ResponseEntity<LivroRequest> response = livroController.buscarLivroPorId(livroId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livroRequest, response.getBody());
        verify(livroInputPort, times(1)).buscarPorId(livroId);
    }

    @Test
    void deveAtualizarLivro() {
        Long livroId = 1L;
        when(livroMapper.toRequest(atualizarLivroRequest)).thenReturn(atualizarLivroRequest);
        when(livroInputPort.atualizarLivro(livroId, atualizarLivroRequest)).thenReturn(livroRequest);

        ResponseEntity<LivroRequest> response = livroController.atualizarLivro(livroId, atualizarLivroRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(livroRequest, response.getBody());
        verify(livroInputPort, times(1)).atualizarLivro(livroId, atualizarLivroRequest);
    }

    @Test
    void deveDesativarLivro() {
        Long livroId = 1L;

        ResponseEntity<Void> response = livroController.desativarLivro(livroId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(livroInputPort, times(1)).desativarLivro(livroId);
    }

    @Test
    void deveListarLivrosEmprestados() {
        when(livroInputPort.listarLivrosEmprestados()).thenReturn(List.of(livroComEmprestimoRequest));

        ResponseEntity<List<LivroComEmprestimoRequest>> response = livroController.listarLivrosEmprestados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(livroComEmprestimoRequest, response.getBody().get(0));
        verify(livroInputPort, times(1)).listarLivrosEmprestados();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverLivros() {
        when(livroInputPort.listarLivros(null, null)).thenReturn(List.of());

        ResponseEntity<List<LivroRequest>> response = livroController.listarLivros(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverLivrosEmprestados() {
        when(livroInputPort.listarLivrosEmprestados()).thenReturn(List.of());

        ResponseEntity<List<LivroComEmprestimoRequest>> response = livroController.listarLivrosEmprestados();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}