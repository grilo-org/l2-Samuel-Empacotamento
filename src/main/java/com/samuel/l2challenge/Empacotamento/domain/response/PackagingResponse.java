package com.samuel.l2challenge.Empacotamento.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * É a resposta com o resultado de melhor empacotamento.
 * Representa os pedidos e seus respectivos empacotamentos.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackagingResponse {

  @JsonProperty("pedidos")
  private List<OrdersResponse> ordersResponse;

}
