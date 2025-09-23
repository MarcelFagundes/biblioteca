//package com.bibliotecalivrosemprestimos.mockito;
//
//import com.bibliotecalivrosemprestimos.adapter.output.repository.UsuarioRepository;
//import com.bibliotecalivrosemprestimos.core.UseCase.UsuarioUseCase;
//import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
//import com.seuorg.app.application.ProductService;
//import com.seuorg.app.domain.Product;
//import com.seuorg.app.ports.ProductRepositoryPort;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatcher;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
///**
// * Exemplos de ArgumentMatchers: argThat, eq, any, same, refEq.
// */
//@ExtendWith(MockitoExtension.class)
//@Tag("unit")
//class ArgumentMatchersAdvancedTest {
//
//  @Mock
//  UsuarioRepository repo;
//  @InjectMocks
//  UsuarioUseCase service;
//
//  @Test
//  void argThat_validaProdutoAntesDeSalvar() {
//    when(repo.existsByEmail(anyString())).thenReturn(false);
//    when(repo.save(argThat(usuarioIsValid()))).thenAnswer(inv -> inv.getArgument(0));
//
//    Usuario criarUsuario = service.criarUsuario(Usuario.builder())
//        .nome("Teste")
//        .email("teste@teste.com")
//        .build());
//
//    // Verifica que o objeto passado para save atende o matcher (active = true setado pelo service)
//    verify(repo).save(argThat(u -> u.isActive() && u.getEmail().equals("teste@teste.com")));
//  }
//
//  private ArgumentMatcher<Usuario> usuarioIsValid() {
//    return u -> u != null && u.getNome() != null && u.getEmail() != null;
//  }
//
//  @Test
//  void refEq_para_comparar_por_campo() {
//    when(repo.existsByEmail((anyString()))).thenReturn(false);
//    when(repo.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));
//
//    Usuario expected = new Usuario("Teste", "email");
//    Usuario input = new Usuario("Teste", "email");
//
//    service.criarUsuario(input); // o service liga active=true
//
//    // refEq compara por campos (ignora campos nulos adicionais se configurado)
//    verify(repo).save(argThat(u -> u.getNome().equals(expected.getNome())
//        && u.getEmail().equals(expected.getEmail())
//        && u.isActive())); // active deve ser true após service
//  }
//
////  @Test
////  void stub_findById_com_argThat() {
////    when(repo.findById(argThat(id -> id != null && id.length() == 1)))
////        .thenReturn(Optional.of(new Product("9", "Prato", "P9", new BigDecimal("9.99"), true)));
////
////    assertEquals("Prato", service.get("9").getName());
////  }
//}
