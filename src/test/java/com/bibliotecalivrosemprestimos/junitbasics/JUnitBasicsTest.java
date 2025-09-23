package com.bibliotecalivrosemprestimos.demo.junitbasics;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayName("3.2 Introdução ao JUnit - Exemplos completos")
class JUnitBasicsTest {

  @BeforeAll
  static void beforeAll() { System.out.println("Antes de TODOS os testes"); }

  @AfterAll
  static void afterAll() { System.out.println("Depois de TODOS os testes"); }

  @BeforeEach
  void beforeEach() { System.out.println("Antes de cada teste"); }

  @AfterEach
  void afterEach() { System.out.println("Depois de cada teste"); }

  @Test
  @DisplayName("@Test básico + assertivas")
  void testSoma() {
    int soma = 2 + 3;
    assertEquals(5, soma);
    assertAll(
      () -> assertNotNull(soma),
      () -> assertTrue(soma > 0)
    );
  }

  @Test
  @DisplayName("Assertiva de exceção com assertThrows")
  void testExcecao() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
      () -> { throw new IllegalArgumentException("mensagem"); });
    assertEquals("mensagem", ex.getMessage());
  }

  @Test @Disabled("Exemplo de teste desabilitado temporariamente")
  void testeDesabilitado() { fail("não deve rodar"); }

  @Test
  @DisplayName("Assumptions - pular teste se condição não atender")
  void testAssumptions() { assumeTrue(true, "Se false, o teste é pulado"); }

  @RepeatedTest(3) @DisplayName("Teste repetido")
  void testeRepetido() { assertTrue(Math.random() >= 0.0); }

  @Nested @DisplayName("Bloco aninhado de testes")
  class NestedBlock {
    @Test void testeAninhado() { assertNotEquals(1, 2); }
  }
}
