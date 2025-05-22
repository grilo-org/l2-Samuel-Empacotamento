package com.samuel.l2challenge.Empacotamento.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samuel.l2challenge.Empacotamento.domain.Dimensions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa o produto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  @JsonProperty("produto_id")
  private Long productId;

  @JsonProperty("produto_nome")
  private String productName;

  @JsonProperty("dimensoes")
  private Dimensions dimensions;

}
