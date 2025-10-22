package com.bibliotecalivrosemprestimos.adapter.input;

import com.bibliotecalivrosemprestimos.adapter.input.controller.EmprestimoController;
import com.bibliotecalivrosemprestimos.adapter.input.mapper.EmprestimoMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.EmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.MultaRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarEmprestimoRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.DevolverLivroRequest;
import com.bibliotecalivrosemprestimos.port.input.EmprestimoInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmprestimoControllerTest {

    @Mock
    private EmprestimoInputPort emprestimoInputPort;

    @Mock
    private EmprestimoMapper emprestimoMapper;

    @InjectMocks
    private EmprestimoController emprestimoController;

    private CriarEmprestimoRequest criarEmprestimoRequest;
    private EmprestimoRequest emprestimoRequest;
    private DevolverLivroRequest devolverLivroRequest;
    private MultaRequest multaRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuração do CriarEmprestimoRequest
        criarEmprestimoRequest = new CriarEmprestimoRequest(
                1L,
                1L,
                LocalDateTime.parse("2025-09-15"),
                LocalDateTime.parse("2025-09-30"),
                null
        );


        // Configuração do EmprestimoRequest
        emprestimoRequest = new EmprestimoRequest(
                1L,
                1L,
                "Dom Casmurro",
                1L,
                "João Silva",
                LocalDateTime.parse("2025-09-15"),
                LocalDateTime.parse("2025-09-30"),
                null,
                true,
                false
        );

        // Configuração do DevolverLivroRequest
        devolverLivroRequest = new DevolverLivroRequest(
                1L,
                1L,
                "João Silva",
                "Dom Casmurro",
                1L,
                LocalDateTime.parse("2025-09-15"),
                LocalDateTime.parse("2025-09-30"),
                LocalDateTime.parse("2025-09-28"),
                true,
                false
        );

        // Configuração do MultaRequest
        multaRequest = new MultaRequest(
                2,
                5.0
        );
    }

    @Test
    void deveCriarEmprestimo() {
        when(emprestimoMapper.toRequest(criarEmprestimoRequest)).thenReturn(criarEmprestimoRequest);
        when(emprestimoInputPort.criarEmprestimo(criarEmprestimoRequest)).thenReturn(emprestimoRequest);

        ResponseEntity<EmprestimoRequest> response = emprestimoController.criarEmprestimo(criarEmprestimoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(emprestimoRequest, response.getBody());
        verify(emprestimoInputPort, times(1)).criarEmprestimo(criarEmprestimoRequest);
    }

    @Test
    void deveListarEmprestimosSemFiltros() {
        when(emprestimoInputPort.listarEmprestimos(null, null)).thenReturn(List.of(emprestimoRequest));

        ResponseEntity<List<EmprestimoRequest>> response = emprestimoController.listarEmprestimos(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(emprestimoRequest, response.getBody().get(0));
        verify(emprestimoInputPort, times(1)).listarEmprestimos(null, null);
    }

    @Test
    void deveListarEmprestimosComFiltroUsuarioId() {
        Long usuarioId = 1L;
        when(emprestimoInputPort.listarEmprestimos(usuarioId, null)).thenReturn(List.of(emprestimoRequest));

        ResponseEntity<List<EmprestimoRequest>> response = emprestimoController.listarEmprestimos(usuarioId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(emprestimoInputPort, times(1)).listarEmprestimos(usuarioId, null);
    }

    @Test
    void deveListarEmprestimosComFiltroAtivo() {
        Boolean ativo = true;
        when(emprestimoInputPort.listarEmprestimos(null, ativo)).thenReturn(List.of(emprestimoRequest));

        ResponseEntity<List<EmprestimoRequest>> response = emprestimoController.listarEmprestimos(null, ativo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(emprestimoInputPort, times(1)).listarEmprestimos(null, ativo);
    }

    @Test
    void deveListarEmprestimosComTodosFiltros() {
        Long usuarioId = 1L;
        Boolean ativo = true;
        when(emprestimoInputPort.listarEmprestimos(usuarioId, ativo)).thenReturn(List.of(emprestimoRequest));

        ResponseEntity<List<EmprestimoRequest>> response = emprestimoController.listarEmprestimos(usuarioId, ativo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(emprestimoInputPort, times(1)).listarEmprestimos(usuarioId, ativo);
    }

    @Test
    void deveRegistrarDevolucao() {
        Long emprestimoId = 1L;
        when(emprestimoMapper.toRequest(devolverLivroRequest)).thenReturn(devolverLivroRequest);
        when(emprestimoInputPort.registrarDevolucao(emprestimoId, devolverLivroRequest)).thenReturn(emprestimoRequest);

        ResponseEntity<EmprestimoRequest> response = emprestimoController.registrarDevolucao(emprestimoId, devolverLivroRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(emprestimoRequest, response.getBody());
        verify(emprestimoInputPort, times(1)).registrarDevolucao(emprestimoId, devolverLivroRequest);
    }

    @Test
    void deveCalcularMulta() {
        Long emprestimoId = 1L;
        when(emprestimoInputPort.calcularMulta(emprestimoId)).thenReturn(multaRequest);

        ResponseEntity<MultaRequest> response = emprestimoController.calcularMulta(emprestimoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(multaRequest, response.getBody());
        verify(emprestimoInputPort, times(1)).calcularMulta(emprestimoId);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverEmprestimos() {
        when(emprestimoInputPort.listarEmprestimos(null, null)).thenReturn(List.of());

        ResponseEntity<List<EmprestimoRequest>> response = emprestimoController.listarEmprestimos(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarMultaZeradaQuandoNaoHouverAtraso() {
        Long emprestimoId = 2L;
        MultaRequest multaSemAtraso = new MultaRequest(
                0,
                0.0
        );

        when(emprestimoInputPort.calcularMulta(emprestimoId)).thenReturn(multaSemAtraso);

        ResponseEntity<MultaRequest> response = emprestimoController.calcularMulta(emprestimoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().diasAtraso());
        assertEquals(0.0, response.getBody().valor());
    }
}
