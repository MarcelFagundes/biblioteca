package com.bibliotecalivrosemprestimos.adapter.input;

import com.bibliotecalivrosemprestimos.adapter.input.controller.UsuarioController;
import com.bibliotecalivrosemprestimos.adapter.input.web.dto.UsuarioDTO;
import com.bibliotecalivrosemprestimos.core.UseCase.UsuarioUseCase;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LivroControllerTest {

    @Mock
    private UsuarioUseCase service;

    @InjectMocks
    private UsuarioController controller;

    @Test
    void criarUsuario_DeveRetornarUsuarioCriado() {
        // Arrange
        UsuarioDTO requestDTO = new UsuarioDTO(null, "João Silva", "joao@email.com");
        Usuario usuarioSalvo = new Usuario("1", "João Silva", "joao@email.com");

        when(service.criarUsuario(any(Usuario.class))).thenReturn(usuarioSalvo);

        // Act
        ResponseEntity<UsuarioDTO> response = controller.criar(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("joao@email.com", response.getBody().getEmail());
        verify(service, times(1)).criar(any(Usuario.class));
    }

    @Test
    void buscarPorId_QuandoUsuarioExiste_DeveRetornarUsuario() {
        // Arrange
        Usuario usuario = new Usuario("1", "Maria Santos", "maria@email.com", "98765432100");
        when(service.buscarPorId("1")).thenReturn(usuario);

        // Act
        ResponseEntity<UsuarioDTO> response = controller.buscarPorId("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Maria Santos", response.getBody().getNome());
        assertEquals("maria@email.com", response.getBody().getEmail());
    }

    @Test
    void buscarPorId_QuandoUsuarioNaoExiste_DeveRetornarNotFound() {
        // Arrange
        when(service.buscarPorId("999")).thenReturn(null);

        // Act
        ResponseEntity<UsuarioDTO> response = controller.buscarPorId("999");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void listarTodos_DeveRetornarListaDeUsuarios() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(
                new Usuario("1", "João Silva", "joao@email.com", "12345678900"),
                new Usuario("2", "Maria Santos", "maria@email.com", "98765432100")
        );
        when(service.listarTodos()).thenReturn(usuarios);

        // Act
        ResponseEntity<List<UsuarioDTO>> response = controller.listarTodos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNome());
    }

    @Test
    void atualizarUsuario_DeveRetornarUsuarioAtualizado() {
        // Arrange
        UsuarioDTO requestDTO = new UsuarioDTO("1", "João Silva Atualizado", "joao.novo@email.com", "12345678900");
        Usuario usuarioAtualizado = new Usuario("1", "João Silva Atualizado", "joao.novo@email.com", "12345678900");

        when(service.atualizar(eq("1"), any(Usuario.class))).thenReturn(usuarioAtualizado);

        // Act
        ResponseEntity<UsuarioDTO> response = controller.atualizar("1", requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva Atualizado", response.getBody().getNome());
        assertEquals("joao.novo@email.com", response.getBody().getEmail());
    }

    @Test
    void deletarUsuario_DeveRetornarNoContent() {
        // Arrange
        doNothing().when(service).deletar("1");

        // Act
        ResponseEntity<Void> response = controller.deletar("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deletar("1");
    }
}