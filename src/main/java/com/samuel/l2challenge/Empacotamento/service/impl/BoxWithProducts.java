package com.samuel.l2challenge.Empacotamento.service.impl;

import com.samuel.l2challenge.Empacotamento.domain.Box;
import com.samuel.l2challenge.Empacotamento.domain.request.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Classe auxiliar, utilizada para processar o empacotamento.
 * Representa um dos tipos de caixa, e uma lista de produtos que estão dentro da caixa.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoxWithProducts {
  private Box box;
  private List<ProductRequest> productsInsideTheBox = new ArrayList<>();

  public BoxWithProducts(Box box) {
    this.box = box;
  }

  /**
   * Valida se um produto cabe dentro da caixa.
   * Calcula o volume atual ocupado, com base no volume total da caixa,
   * o volume já preenchido pelos produtos que já estão na caixa, e o volume do produto a ser inserido.
   */
  public boolean doesTheProductFitInTheBox(ProductRequest product) {
    BigDecimal boxTotalVolume = box.dimensions().getVolume();
    BigDecimal boxFilledVolume = productsInsideTheBox.stream()
        .map(p -> p.getDimensions().getVolume())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal productVolume = product.getDimensions().getVolume();

    return fitsInRemainingBoxVolume(boxFilledVolume, productVolume, boxTotalVolume)
        && doesTheProductFitPhysicallyInTheBox(product, box);
  }

  private static boolean fitsInRemainingBoxVolume(
      BigDecimal boxFilledVolume, BigDecimal productVolume, BigDecimal boxTotalVolume) {
    return boxFilledVolume.add(productVolume).compareTo(boxTotalVolume) <= 0;
  }

  /**
   * Valida se as dimensões do produto cabem numa caixa.
   * Ordena as dimensões do produto e caixa de forma crescente.
   * Compara cada uma das 3 dimensões.
   * Se as dimensões do produto forem menores ou iguais às da caixa, então o produto cabe.
   */
  private boolean doesTheProductFitPhysicallyInTheBox(ProductRequest p, Box c) {
    BigDecimal[] prodDims = {
        p.getDimensions().getHeigh(),
        p.getDimensions().getWidth(),
        p.getDimensions().getLength()
    };
    BigDecimal[] caixaDims = {
        c.dimensions().getHeigh(),
        c.dimensions().getWidth(),
        c.dimensions().getLength()
    };

    Arrays.sort(prodDims);
    Arrays.sort(caixaDims);

    return areTheProductsDimensionsSmallerThanBoxDimensions(prodDims, caixaDims);
  }

  /**
   * Valida se as dimensões do produto são menores que as da caixa.
   */
  private static boolean areTheProductsDimensionsSmallerThanBoxDimensions(BigDecimal[] prodDims, BigDecimal[] caixaDims) {
    return prodDims[0].compareTo(caixaDims[0]) <= 0 &&
        prodDims[1].compareTo(caixaDims[1]) <= 0 &&
        prodDims[2].compareTo(caixaDims[2]) <= 0;
  }

  /**
   * Adiciona um produto na caixa.
   */
  public void addProduct(ProductRequest produto) {
    productsInsideTheBox.add(produto);
  }

}

