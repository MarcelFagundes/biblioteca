package com.bibliotecalivrosemprestimos.demo.junitbasics;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import com.seuorg.app.domain.Product;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstra @BeforeEach criando fixtures reutilizáveis.
 * Também usa @Tag para classificar os testes.
 */
@Tag("unit")
class FixturesWithBeforeEachTest {

  Usuario.UsuarioBuilder builder;

  @BeforeEach
  void setUp(TestInfo info) {
    // Fixture base que pode ser ajustada por cada teste
    builder = Usuario.builder()
        .name("Produto Base")
        .sku("BASE-01")
        .price(new BigDecimal("10.00"))
        .active(true);

    System.out.println("Rodando: " + info.getDisplayName());
  }

  @Test
  @DisplayName("Constrói produto padrão da fixture")
  void buildDefault() {
    Product p = builder.build();
    assertEquals("Produto Base", p.getName());
    assertEquals("BASE-01", p.getSku());
    assertTrue(p.isActive());
  }

  @Test
  @DisplayName("Altera campos a partir da fixture")
  void overrideSomeFields() {
    Product p = builder.name("Outro").price(new BigDecimal("99.90")).build();
    assertEquals("Outro", p.getName());
    assertEquals(new BigDecimal("99.90"), p.getPrice());
    // Mantém demais campos do setup
    assertEquals("BASE-01", p.getSku());
  }
}
