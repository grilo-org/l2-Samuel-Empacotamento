package com.samuel.l2challenge.Empacotamento.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Representa o pedido de um cliente.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

  @Schema(description = "Identificador do pedido")
  @JsonProperty("pedido_id")
  private Long orderId;

  @Schema(description = "Lista de produtos pertencentes ao pedido")
  @JsonProperty("produtos")
  private List<ProductRequest> products;

}
