package com.bibliotecalivrosemprestimos.demo.core.usecase;

import com.bibliotecalivrosemprestimos.adapter.output.repository.UsuarioRepository;
import com.bibliotecalivrosemprestimos.core.UseCase.UsuarioUseCase;
import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.bibliotecalivrosemprestimos.demo.factory.UsuarioFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest // sobe os contextos do Spring Boot
class UsuarioUseCaseTest {

  //  @Mock > nao precisa mockar com Mockito.mock(class)
  UsuarioRepository repo = mock(UsuarioRepository.class);

    ////  @InjectMocks > nao precisa instanciar o ProductService
  UsuarioUseCase service = new UsuarioUseCase(repo);

  @Test
  void cria_usuario_email_duplicado_erro(){
    // given - dado
    when(repo.existsByEmail("teste@teste.com")).thenReturn(true);
    Usuario u = UsuarioFactory.buildCheapUsuario();

    // when - quando
    assertThatThrownBy(() -> service.criarUsuario((u)).isInstanceOf(IllegalArgumentException.class);
    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> service.criarUsuario(u));

    assertEquals("SKU já cadastrado", illegalArgumentException.getMessage());

    // then - então
    verify(repo, never()).save(any());
  }

  @Test
  void criaUsuarioOk(){
    // given - dado
    when(repo.existsByEmail("teste@teste.com")).thenReturn(false);
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    Usuario u = Usuario.builder().name("Teste").email("teste@teste.com").build();

    // when - quando
    Usuario usuario = service.criarUsuario(u);

    // then - então
    assertThat(usuario.isActive()).isTrue();
    verify(repo).save(any());
  }
}