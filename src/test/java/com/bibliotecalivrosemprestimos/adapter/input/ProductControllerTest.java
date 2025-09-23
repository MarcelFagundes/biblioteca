//package com.bibliotecalivrosemprestimos.demo.adapter.input;
//
//import com.seuorg.app.adapters.in.web.ProductController;
//import com.seuorg.app.adapters.in.web.dto.ProductDTO;
//import com.seuorg.app.application.ProductService;
//import com.seuorg.app.domain.Product;
//import org.junit.jupiter.api.Test;
//import org.junit.jupier.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//class ProductControllerTest {
//
//  @InjectMocks
//  private ProductController controller;
//
//  @Mock
//  private ProductService service;
//
//
//  @Test
//  void get_ok() {
//
//    Product product = new Product("1", "Caneca", "ABC", new BigDecimal("39.90"), true);
//
//    when(service.get(Mockito.any())).thenReturn(product);
//
//    ProductDTO productDTO = controller.get("1");
//
//    assertEquals(productDTO.getName(), product.getName());
//
//  }
//}
