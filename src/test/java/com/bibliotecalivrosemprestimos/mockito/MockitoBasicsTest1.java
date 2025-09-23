//package com.bibliotecalivrosemprestimos.mockito;
//
//import com.seuorg.app.application.ProductService;
//import com.seuorg.app.domain.Product;
//import com.seuorg.app.ports.ProductRepositoryPort;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("3.3 Mockito - mocks, stubs, verify, exceptions, @Mock, @InjectMocks, @Captor")
//class MockitoBasicsTest {
//
//  @Mock ProductRepositoryPort repo; // dependência
//  @InjectMocks ProductService service; // SUT
//  @Captor ArgumentCaptor<Product> productCaptor;
//
//  @Test
//  @DisplayName("Stubbing: when/thenReturn + verificação de chamadas")
//  void create_ok() {
//    given(repo.existsBySku("XYZ")).willReturn(false);
//    given(repo.save(any(Product.class))).willAnswer(inv -> inv.getArgument(0));
//
//    Product p = Product.builder().name("Copo").sku("XYZ").price(new BigDecimal("10.0")).build();
//    Product created = service.create(p);
//
//    assertTrue(created.isActive());
//    then(repo).should(times(1)).existsBySku("XYZ");
//    then(repo).should().save(productCaptor.capture());
//    assertEquals("XYZ", productCaptor.getValue().getSku());
//    then(repo).shouldHaveNoMoreInteractions();
//  }
//
//  @Test
//  @DisplayName("Exceção: when/thenThrow")
//  void create_skuDuplicado() {
//    when(repo.existsBySku("DUP")).thenReturn(true);
//    Product p = Product.builder().name("Xícara").sku("DUP").price(new BigDecimal("20.0")).build();
//    assertThrows(IllegalArgumentException.class, () -> service.create(p));
//    verify(repo, never()).save(any());
//  }
//
//  @Test
//  @DisplayName("Void methods: doThrow para delete")
//  void delete_erro() {
//    doThrow(new RuntimeException("falha")).when(repo).deleteById("X");
//    assertThrows(RuntimeException.class, () -> service.delete("X"));
//    verify(repo).deleteById("X");
//  }
//
//  @Test
//  @DisplayName("doAnswer: lógica customizada no stub")
//  void findById_doAnswer() {
//    when(repo.findById("1")).thenAnswer(inv ->
//      Optional.of(Product.builder().id("1").name("Orig").sku("A1").price(new BigDecimal("1")).active(true).build())
//    );
//    Product p = service.get("1");
//    assertEquals("Orig", p.getName());
//    verify(repo).findById("1");
//  }
//}
