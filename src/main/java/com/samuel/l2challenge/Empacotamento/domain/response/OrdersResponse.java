package com.samuel.l2challenge.Empacotamento.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(description = "Identificador do pedido")
  @JsonProperty("pedido_id")
  private Long orderId;

  @Schema(description = "Lista de caixas utilizadas para empacotar os produtos deste pedido")
  @JsonProperty("caixas")
  private List<BoxResponse> boxes;

}
