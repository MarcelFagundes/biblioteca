//package com.bibliotecalivrosemprestimos.demo.core.usecase;
//import com.seuorg.app.domain.Product;
//import com.seuorg.app.factory.ProductFactoryBot;
//import com.seuorg.app.ports.ProductRepositoryPort;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//import static org.assertj.core.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class) // ativa integração com Spring fazendo com que os beans e demais componentes do Spring sejam carregados
////@SpringBootTest // sobe os contextos do Spring Boot
//class ProductServiceTest {
//
////  @Mock > nao precisa mockar com Mockito.mock(class)
//  ProductRepositoryPort repo = mock(ProductRepositoryPort.class);
//
////  @InjectMocks > nao precisa instanciar o ProductService
//  ProductService service = new ProductService(repo);
//
//
//
//  @Test
//  void cria_produto_sku_duplicado_erro(){
//    // given - dado
//    when(repo.existsBySku("ABC")).thenReturn(true);
//    Product p = ProductFactoryBot.buildCheapProduct();
//
//    // when - quando
//    assertThatThrownBy(() -> service.create(p)).isInstanceOf(IllegalArgumentException.class);
//    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> service.create(p));
//
//    assertEquals("SKU já cadastrado", illegalArgumentException.getMessage());
//
//    // then - então
//    verify(repo, never()).save(any());
//  }
//
//
//  @Test
//  void criaProdutoOk(){
//    // given - dado
//    when(repo.existsBySku("banana")).thenReturn(false);
//    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
//    Product p = Product.builder().name("Copo").sku("XYZ").price(new BigDecimal("10.0")).build();
//
//    // when - quando
//    Product product = service.create(p);
//
//    // then - então
//    assertThat(product.isActive()).isTrue();
//    verify(repo).save(any());
//  }
//}
