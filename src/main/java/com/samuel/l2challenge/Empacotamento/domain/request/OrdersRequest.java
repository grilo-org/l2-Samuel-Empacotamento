package com.samuel.l2challenge.Empacotamento.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Lista de pedidos a serem processados.
 * É o objeto recebido no endpoint /packaging
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersRequest {

  @Schema(description = "Lista de pedidos a serem processados para empacotamento")
  @JsonProperty("pedidos")
  private List<OrderRequest> orders;

}
