package com.samuel.l2challenge.Empacotamento.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Representação das 3 dimensões de um objeto.
 */
@Data
@NoArgsConstructor
public class Dimensions {

  @JsonProperty("altura")
  private BigDecimal heigh;

  @JsonProperty("largura")
  private BigDecimal width;

  @JsonProperty("comprimento")
  private BigDecimal length;

  @JsonIgnore
  private BigDecimal volume;

  public Dimensions(BigDecimal heigh, BigDecimal width, BigDecimal length) {
    this.heigh = heigh;
    this.width = width;
    this.length = length;
  }

  /**
   * Se o volume não foi calculado ainda, calcula e preenche o campo volume.
   * Caso já tenha sido calculado, apenas retorna o valor.
   * @return Retorna o volume do objeto.
   */
  public BigDecimal getVolume() {
    if (wasNotTheVolumeCalculated()) {
      volume = heigh.multiply(width).multiply(length);
    }
    return volume;
  }

  /**
   * Verifica se o volume ainda não foi calculado.
   */
  private boolean wasNotTheVolumeCalculated() {
    return volume == null && heigh != null && width != null && length != null;
  }
}

