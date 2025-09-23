package com.bibliotecalivrosemprestimos.demo.junitbasics;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exemplo simples de uso de @Tag para marcar testes.
 * Rode só uma tag com: mvn -Dgroups=unit test (ou via surefire config)
 */
@Tag("unit")
class TaggedExampleTest {
  @Test
  void ok() { assertTrue(true); }
}
