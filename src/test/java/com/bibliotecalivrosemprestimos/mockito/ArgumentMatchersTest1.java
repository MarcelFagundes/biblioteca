//package com.bibliotecalivrosemprestimos.mockito;
//
//import com.seuorg.app.application.ProductService;
//import com.seuorg.app.domain.Product;
//import com.seuorg.app.ports.ProductRepositoryPort;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ArgumentMatchersTest {
//
//  @Mock ProductRepositoryPort repo;
//  @InjectMocks ProductService service;
//
//  @Test
//  void usaArgThatParaValidarObjeto() {
//    when(repo.existsBySku(anyString())).thenReturn(false);
//    when(repo.save(argThat(p -> p.getPrice().compareTo(BigDecimal.ZERO) > 0)))
//        .thenAnswer(inv -> inv.getArgument(0));
//
//    Product p = Product.builder().name("Produto").sku("AAA").price(new BigDecimal("50")).build();
//    Product result = service.create(p);
//
//    assertNotNull(result);
//    verify(repo).save(argThat(prod -> prod.getName().equals("Produto") && prod.isActive()));
//  }
//}
