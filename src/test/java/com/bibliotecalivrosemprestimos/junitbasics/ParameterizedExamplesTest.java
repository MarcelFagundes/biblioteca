package com.bibliotecalivrosemprestimos.demo.junitbasics;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class ParameterizedExamplesTest {

  boolean isValidSku(String sku){
    return sku != null && sku.matches("[A-Z0-9-]{3,10}");
  }

  @ParameterizedTest(name = "SKU válido: {0}")
  @ValueSource(strings = {"ABC","A1B2","SKU-10"})
  void validos(String sku){ assertTrue(isValidSku(sku)); }

  @ParameterizedTest(name = "{0} + {1} = {2}")
  @CsvSource({ "1,2,3", "10,5,15", "-1,1,0" })
  void somaCsv(int a, int b, int esperado){
    assertEquals(esperado, a + b);
  }
}
