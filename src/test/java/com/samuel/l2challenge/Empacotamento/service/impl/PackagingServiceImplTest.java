package com.samuel.l2challenge.Empacotamento.service.impl;

import com.samuel.l2challenge.Empacotamento.domain.Box;
import com.samuel.l2challenge.Empacotamento.domain.Dimensions;
import com.samuel.l2challenge.Empacotamento.domain.request.OrdersRequest;
import com.samuel.l2challenge.Empacotamento.domain.request.OrderRequest;
import com.samuel.l2challenge.Empacotamento.domain.request.ProductRequest;
import com.samuel.l2challenge.Empacotamento.domain.response.PackagingResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackagingServiceImplTest {

  private final PackagingServiceImpl service = new PackagingServiceImpl();

  @Test
  void testCalculateBestPackaging_withOneOrderAndFitProducts_shouldUseSmallestBoxPossible() {
    ProductRequest product1 = ProductRequest.builder()
        .productName("Controle")
        .dimensions(new Dimensions(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(10)))
        .build();

    ProductRequest product2 = ProductRequest.builder()
        .productName("Cabo HDMI")
        .dimensions(new Dimensions(BigDecimal.valueOf(5), BigDecimal.valueOf(5), BigDecimal.valueOf(5)))
        .build();

    OrderRequest order = OrderRequest.builder()
        .orderId(1L)
        .products(List.of(product1, product2))
        .build();

    OrdersRequest request = OrdersRequest.builder()
        .orders(List.of(order))
        .build();

    PackagingResponse response = service.calculateBestPackaging(request);

    assertNotNull(response);
    assertEquals(1, response.getOrdersResponse().size());
    assertEquals(1L, response.getOrdersResponse().getFirst().getOrderId());

    var boxes = response.getOrdersResponse().getFirst().getBoxes();
    assertEquals(1, boxes.size());
    assertTrue(boxes.getFirst().getProducts().contains("Controle"));
    assertTrue(boxes.getFirst().getProducts().contains("Cabo HDMI"));
  }

  @Test
  void testCalculateBestPackaging_withProductTooBig_shouldGoToNonFitBox() {
    ProductRequest bigProduct = ProductRequest.builder()
        .productName("TV 90 Polegadas")
        .dimensions(new Dimensions(BigDecimal.valueOf(200), BigDecimal.valueOf(200), BigDecimal.valueOf(200)))
        .build();

    OrderRequest order = OrderRequest.builder()
        .orderId(2L)
        .products(List.of(bigProduct))
        .build();

    OrdersRequest request = OrdersRequest.builder()
        .orders(List.of(order))
        .build();

    PackagingResponse response = service.calculateBestPackaging(request);

    assertNotNull(response);
    assertEquals(1, response.getOrdersResponse().size());
    assertEquals(2L, response.getOrdersResponse().getFirst().getOrderId());

    var boxes = response.getOrdersResponse().getFirst().getBoxes();
    assertEquals(1, boxes.size());
    assertEquals(Box.NON_FIT_PRODUCTS_BOX_NAME, boxes.getFirst().getBoxName());
    assertTrue(boxes.getFirst().getProducts().contains("TV 90 Polegadas"));
  }

  @Test
  void testCalculateBestPackaging_withEmptyOrderList_shouldReturnEmptyResponse() {
    OrdersRequest request = OrdersRequest.builder()
        .orders(List.of())
        .build();

    PackagingResponse response = service.calculateBestPackaging(request);

    assertNotNull(response);
    assertTrue(response.getOrdersResponse().isEmpty());
  }

  @Test
  void testProductDoesNotFitInAnyUsedBoxButFitsInNewBox() {
    // Produto pequeno que cabe na menor caixa
    ProductRequest product1 = new ProductRequest(1L, "Produto 1", new Dimensions(
        BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(10)));

    // Produto médio que não cabe na mesma caixa já usada, mas cabe numa nova
    ProductRequest product2 = new ProductRequest(2L, "Produto 2", new Dimensions(
        BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(79.99))); // quase limite da menor caixa

    // Produto que não cabe em nenhuma caixa, vai pra fictícia
    ProductRequest product3 = new ProductRequest(3L, "Produto 3", new Dimensions(
        BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000)));

    OrderRequest order = new OrderRequest(1L, List.of(product1, product2, product3));
    OrdersRequest request = new OrdersRequest(List.of(order));

    PackagingResponse response = service.calculateBestPackaging(request);

    assertEquals(1, response.getOrdersResponse().size());
    var boxes = response.getOrdersResponse().getFirst().getBoxes();

    // Esperado: 3 caixas (duas reais + uma fictícia)
    assertEquals(3, boxes.size());

    // Verifica que a caixa fictícia existe e contém o produto 3
    var nonFitBox = boxes.stream()
        .filter(b -> b.getBoxName().equals(Box.NON_FIT_PRODUCTS_BOX_NAME))
        .findFirst()
        .orElseThrow();

    assertTrue(nonFitBox.getProducts().contains("Produto 3"));
  }



}
