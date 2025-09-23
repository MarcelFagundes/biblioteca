//package com.bibliotecalivrosemprestimos.demo.core.usecase;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import static org.assertj.core.api.Assertions.*;
//
//class SkuRulesTest {
//  boolean isValidSku(String sku){
//
//    // [\\d]
//    // [+0-9]
//    // [Aa-Zz]
//    return sku != null && sku.matches("[A-Z0-9]{3,10}");
//  }
//
//  @ParameterizedTest
//  @ValueSource(strings = {"ABC","A1B2C3","SKU123"})
//  void validos(String sku) {
//    assertThat(isValidSku(sku)).isTrue();
//  }
//
//  @ParameterizedTest
//  @ValueSource(strings = {"ab", "abc!", "", "aaaaaaaaaaa"})
//  void invalidos(String sku) {
//    assertThat(isValidSku(sku)).isFalse();
//  }
//}
