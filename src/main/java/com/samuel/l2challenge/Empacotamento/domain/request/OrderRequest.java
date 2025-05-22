package com.samuel.l2challenge.Empacotamento.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Representa o pedido de um cliente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

  @JsonProperty("pedido_id")
  private Long orderId;

  @JsonProperty("produtos")
  private List<ProductRequest> products;

}
