package com.bibliotecalivrosemprestimos.demo.junitbasics;

import com.bibliotecalivrosemprestimos.core.domain.model.Usuario;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

@Tag("unit")
class TaggedAndFixturesTest {

  Usuario defaultUsuario;

  @BeforeEach
  void setup() {
    // Fixture: instância padrão para reutilizar em vários testes
    defaultUsuario = Usuario.builder()
        .id("X1")
        .name("Produto Base")
        .sku("SKU123")
        .price(new java.math.BigDecimal("19.90"))
        .active(true)
        .build();
  }

  @Test
  @Tag("fast")
  void produtoAtivoPorDefeito() {
    assertThat(defaultProduct.isActive()).isTrue();
  }

  @Test
  @Tag("validation")
  void produtoTemSkuValido() {
    assertThat(defaultProduct.getSku()).matches("[A-Z0-9]{3,10}");
  }
}
