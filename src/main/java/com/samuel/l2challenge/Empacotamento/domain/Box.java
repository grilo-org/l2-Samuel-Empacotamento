package com.samuel.l2challenge.Empacotamento.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Representação simples de uma caixa.
 */
public record Box(Long id, String name, Dimensions dimensions) {

  public static final String BOX_1_NAME = "Caixa 1";
  public static final String BOX_2_NAME = "Caixa 2";
  public static final String BOX_3_NAME = "Caixa 3";
  public static final String NON_FIT_PRODUCTS_BOX_NAME = "Produtos que não cabem em nenhuma caixa disponível";

  /**
   * Para este exercício, só existem esses 3 tipos de caixa.
   */
  public static List<Box> getAvailableBoxes() {
    return List.of(
        new Box(1L, BOX_1_NAME, new Dimensions(BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(80))),
        new Box(2L, BOX_2_NAME, new Dimensions(BigDecimal.valueOf(80), BigDecimal.valueOf(50), BigDecimal.valueOf(40))),
        new Box(3L, BOX_3_NAME, new Dimensions(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(60)))
    );
  }

}
