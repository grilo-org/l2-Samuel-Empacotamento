package com.samuel.l2challenge.Empacotamento.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samuel.l2challenge.Empacotamento.domain.Dimensions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa o produto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  @Schema(description = "Identificador do produto")
  @JsonProperty("produto_id")
  private Long productId;

  @Schema(description = "Nome descritivo do produto")
  @JsonProperty("produto_nome")
  private String productName;

  @Schema(description = "Dimensões físicas do produto (altura, largura, comprimento)")
  @JsonProperty("dimensoes")
  private Dimensions dimensions;

}
