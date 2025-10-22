package com.bibliotecalivrosemprestimos.adapter.input;

import com.bibliotecalivrosemprestimos.adapter.input.controller.UsuarioController;
import com.bibliotecalivrosemprestimos.adapter.input.mapper.UsuarioMapper;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioComEmprestimosRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.UsuarioRequest;
import com.bibliotecalivrosemprestimos.adapter.input.request.validation.CriarUsuarioRequest;
import com.bibliotecalivrosemprestimos.port.input.UsuarioInputPort;
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

class UsuarioControllerTest {

    @Mock
    private UsuarioInputPort usuarioInputPort;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    private CriarUsuarioRequest criarUsuarioRequest;
    private UsuarioRequest usuarioRequest;
    private UsuarioComEmprestimosRequest usuarioComEmprestimosRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        criarUsuarioRequest = new CriarUsuarioRequest(
                "João Silva",
                "joao@email.com"
        );

        usuarioRequest = new UsuarioRequest(
                1L,
                "João Silva",
                "joao@email.com"
        );

        usuarioComEmprestimosRequest = new UsuarioComEmprestimosRequest();
        usuarioComEmprestimosRequest.setId(1L);
        usuarioComEmprestimosRequest.setNome("João Silva");
        usuarioComEmprestimosRequest.setEmail("joao@email.com");
    }

    @Test
    void deveCriarUsuario() {
        when(usuarioMapper.toRequest(criarUsuarioRequest)).thenReturn(criarUsuarioRequest);
        when(usuarioInputPort.criarUsuario(criarUsuarioRequest)).thenReturn(usuarioRequest);

        ResponseEntity<UsuarioRequest> response = usuarioController.criarUsuario(criarUsuarioRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuarioRequest, response.getBody());
        verify(usuarioInputPort, times(1)).criarUsuario(criarUsuarioRequest);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioInputPort.buscarPorId(1L)).thenReturn(usuarioRequest);

        ResponseEntity<UsuarioRequest> response = usuarioController.buscarUsuarioPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioRequest, response.getBody());
        verify(usuarioInputPort, times(1)).buscarPorId(1L);
    }

    @Test
    void deveListarUsuarios() {
        when(usuarioInputPort.listarTodos()).thenReturn(List.of(usuarioRequest));

        ResponseEntity<List<UsuarioRequest>> response = usuarioController.listarUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(usuarioRequest, response.getBody().get(0));
        verify(usuarioInputPort, times(1)).listarTodos();
    }

    @Test
    void deveListarUsuariosComEmprestimos() {
        when(usuarioInputPort.listarUsuariosComEmprestimos()).thenReturn(List.of(usuarioComEmprestimosRequest));

        ResponseEntity<List<UsuarioComEmprestimosRequest>> response =
                usuarioController.listarUsuariosComEmprestimos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(usuarioComEmprestimosRequest, response.getBody().get(0));
        verify(usuarioInputPort, times(1)).listarUsuariosComEmprestimos();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUsuarios() {
        when(usuarioInputPort.listarTodos()).thenReturn(List.of());

        ResponseEntity<List<UsuarioRequest>> response = usuarioController.listarUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUsuariosComEmprestimos() {
        when(usuarioInputPort.listarUsuariosComEmprestimos()).thenReturn(List.of());

        ResponseEntity<List<UsuarioComEmprestimosRequest>> response =
                usuarioController.listarUsuariosComEmprestimos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}