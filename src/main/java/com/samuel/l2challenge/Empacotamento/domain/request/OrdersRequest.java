package com.samuel.l2challenge.Empacotamento.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Lista de pedidos a serem processados.
 * É o objeto recebido no endpoint /packaging
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersRequest {

  @JsonProperty("pedidos")
  private List<OrderRequest> orders;

}
