package com.samuel.l2challenge.Empacotamento.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Resposta que representa como o pedido deve ser empacotado.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {

  @JsonProperty("pedido_id")
  private Long orderId;

  @JsonProperty("caixas")
  private List<BoxResponse> boxes;

}
