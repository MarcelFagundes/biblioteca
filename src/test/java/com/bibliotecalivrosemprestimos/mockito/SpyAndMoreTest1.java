//package com.bibliotecalivrosemprestimos.mockito;
//
//import org.junit.jupiter.api.Test;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class SpyAndMoreTest {
//
//  @Test
//  void spy_exemplo() {
//    List<String> listaReal = new ArrayList<>();
//    List<String> spy = spy(listaReal);
//
//    spy.add("a");
//    spy.add("b");
//
//    assertEquals(2, spy.size());
//    verify(spy, times(2)).add(anyString());
//
//    when(spy.size()).thenReturn(99);
//    assertEquals(99, spy.size());
//  }
//}
