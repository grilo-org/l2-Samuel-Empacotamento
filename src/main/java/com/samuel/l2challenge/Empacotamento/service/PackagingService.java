package com.samuel.l2challenge.Empacotamento.service;

import com.samuel.l2challenge.Empacotamento.domain.request.OrdersRequest;
import com.samuel.l2challenge.Empacotamento.domain.response.PackagingResponse;

public interface PackagingService {

  PackagingResponse calculateBestPackaging(OrdersRequest request);

}
