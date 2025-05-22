package com.samuel.l2challenge.Empacotamento.resource.docs;

import com.samuel.l2challenge.Empacotamento.domain.request.OrdersRequest;
import com.samuel.l2challenge.Empacotamento.domain.response.PackagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

/**
 * Interface para customizar a documentação Swagger, dando exemplos de request e response para o endpoint.
 */
public interface PackagingResourceDocs {

  @Operation(
      summary = "Calcula o melhor empacotamento dos produtos em caixas disponíveis",
      description = "Recebe uma lista de pedidos com produtos e calcula a melhor forma de empacotá-los " +
          "nas caixas disponíveis, minimizando o número de caixas utilizadas."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Empacotamento realizado com sucesso",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = PackagingResponse.class),
              examples = @ExampleObject(value = """
                  {
                    "pedidos": [
                      {
                        "pedido_id": 123,
                        "caixas": [
                          {
                            "caixa_id": 1,
                            "caixa_nome": "Caixa 1",
                            "produtos": ["Produto A", "Produto B"]
                          },
                          {
                            "caixa_nome": "Produtos que não cabem em nenhuma caixa disponível.",
                            "produtos": ["Produto Z"]
                          }
                        ]
                      }
                    ]
                  }
                  """)
          )
      )
  })
  @RequestBody(
      required = true,
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = OrdersRequest.class),
          examples = @ExampleObject(value = """
              {
                "pedidos": [
                  {
                    "pedido_id": 123,
                    "produtos": [
                      {
                        "produto_nome": "Produto A",
                        "dimensoes": {
                          "altura": 10,
                          "largura": 20,
                          "comprimento": 30
                        }
                      },
                      {
                        "produto_nome": "Produto Z",
                        "dimensoes": {
                          "altura": 90,
                          "largura": 90,
                          "comprimento": 90
                        }
                      }
                    ]
                  }
                ]
              }
              """)
      )
  )
  ResponseEntity<PackagingResponse> calculatePackaging(OrdersRequest request);
}

