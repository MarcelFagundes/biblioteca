//package com.bibliotecalivrosemprestimos.mockito;
//
//package com.bibliotecalivrosemprestimos.core.mockito;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//
///**
// * Classe base para testes com Mockito
// */
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//public abstract class MockitoTestBase {
//
//    @BeforeEach
//    void setUpBase() {
//        // Configuração comum para todos os testes com Mockito
//    }
//
//    protected <T> T any(Class<T> clazz) {
//        return org.mockito.ArgumentMatchers.any(clazz);
//    }
//
//    protected String anyString() {
//        return org.mockito.ArgumentMatchers.anyString();
//    }
//
//    protected Long anyLong() {
//        return org.mockito.ArgumentMatchers.anyLong();
//    }
//}