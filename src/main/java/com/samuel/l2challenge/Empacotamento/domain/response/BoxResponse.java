package com.samuel.l2challenge.Empacotamento.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoxResponse {

  @JsonProperty("caixa_id")
  private Long boxId;

  @JsonProperty("caixa_nome")
  private String boxName;

  @JsonProperty("produtos")
  private List<String> products;

}
