package com.samuel.l2challenge.Empacotamento.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Resposta que representa a caixa onde os produtos são empacotados.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoxResponse {

  @Schema(description = "Identificador da caixa. Pode ser nulo quando se trata " +
      "de uma caixa fictícia para produtos que não cabem em nenhuma caixa disponível.")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("caixa_id")
  private Long boxId;

  @Schema(description = "Nome da caixa ou mensagem informativa quando se trata de uma caixa fictícia")
  @JsonProperty("caixa_nome")
  private String boxName;

  @Schema(description = "Lista com os nomes dos produtos empacotados nesta caixa")
  @JsonProperty("produtos")
  private List<String> products;

}
