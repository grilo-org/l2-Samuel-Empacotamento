package com.samuel.l2challenge.Empacotamento.resource;

import com.samuel.l2challenge.Empacotamento.domain.request.OrdersRequest;
import com.samuel.l2challenge.Empacotamento.domain.response.PackagingResponse;
import com.samuel.l2challenge.Empacotamento.resource.docs.PackagingResourceDocs;
import com.samuel.l2challenge.Empacotamento.service.PackagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource de empacotamento.
 */
@RestController
public class PackagingResource implements PackagingResourceDocs {

  @Autowired
  private PackagingService packagingService;

  /**
   * Endpoint que recebe a solicitação de cálculo de melhor empacotamento.
   *
   * @param request Lista de pedidos com produtos.
   * @return Retorna objeto com o resultado de melhor empacotamento.
   */
  @PostMapping("/packaging")
  public ResponseEntity<PackagingResponse> calculatePackaging(@RequestBody OrdersRequest request) {
    var bestPackaging = packagingService.calculateBestPackaging(request);
    return ResponseEntity.ok().body(bestPackaging);
  }

}
