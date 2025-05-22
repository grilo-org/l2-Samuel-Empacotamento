package com.samuel.l2challenge.Empacotamento.service.impl;

import com.samuel.l2challenge.Empacotamento.domain.Box;
import com.samuel.l2challenge.Empacotamento.domain.request.OrdersRequest;
import com.samuel.l2challenge.Empacotamento.domain.request.ProductRequest;
import com.samuel.l2challenge.Empacotamento.domain.response.BoxResponse;
import com.samuel.l2challenge.Empacotamento.domain.response.OrdersResponse;
import com.samuel.l2challenge.Empacotamento.domain.response.PackagingResponse;
import com.samuel.l2challenge.Empacotamento.service.PackagingService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Serviço para processamento de pedidos.
 */
@Service
public class PackagingServiceImpl implements PackagingService {

  /**
   * Processa os pedidos e devolve o melhor empacotamento.
   * Resgata os tipos de caixa existentes e ordena por volume crescente.
   * Faz um mapeamento de empacotamentos com base nos pedidos, seus respectivos produtos e os tipos de caixa.
   * Por fim transforma o processamento feito na reposta a ser devolvida.
   *
   * @param request Lista de pedidos com produtos.
   * @return Retorna objeto com o resultado de melhor empacotamento.
   */
  @Override
  public PackagingResponse calculateBestPackaging(OrdersRequest request) {
    Map<Long, List<BoxWithProducts>> packaging = new HashMap<>();

    var availableBoxes = Box.getAvailableBoxes().stream()
        .sorted(Comparator.comparing(box -> box.dimensions().getVolume()))
        .toList();

    putTheProductsInTheBoxes(request, availableBoxes, packaging);

    return buildPackagingResponse(packaging);

  }

  /**
   * Processa os pedidos e seus produtos, construindo o melhor empacotamento com base nos tipos de caixa.
   * O processo ocorre em cima de iterações. De forma a representar a ideia de uma pessoa
   * pegando os produtos e colocando nas caixas.
   * <p>
   * Para cada pedido, pega seus produtos, do maior volume para o menor,
   * e tenta colocar nas caixas, da de menor volume para a de maior volume.
   * Caso alguma caixa já esteja sendo usada, tenta colocar o próximo produto nela. Se nenhuma caixa foi pega,
   * pega a menor. Se o próximo produto não cabe na caixa, pega a próxima caixa maior que esta atual.
   * Se algum produto não cabe em nenhum dos tipos de caixa, cria uma caixa fictícia que armazena
   * os produtos que não foram possíveis de empacotar.
   *
   * @param request        Pedidos e seus respectivos produtos.
   * @param availableBoxes Lista com os tipos existentes de caixa.
   * @param packaging      Mapeamento que representa como os pedidos estão empacotados.
   */
  private static void putTheProductsInTheBoxes(OrdersRequest request, List<Box> availableBoxes, Map<Long, List<BoxWithProducts>> packaging) {
    // Itera sobre os pedidos
    for (var order : request.getOrders()) {
      Long orderId = order.getOrderId();
      List<ProductRequest> sortedProducts = order.getProducts().stream()
          .sorted((p1, p2) -> p2.getDimensions().getVolume().compareTo(p1.getDimensions().getVolume()))
          .toList();

      List<BoxWithProducts> usingBoxes = new ArrayList<>();
      List<ProductRequest> nonPackagedProducts = new ArrayList<>();

      // Itera sobre os produtos de 1 pedido
      for (var product : sortedProducts) {
        boolean isProductPackaged = false;

        // Itera as caixas em uso. Tenta alocar 1 produto e alguma caixas já em uso.
        for (BoxWithProducts box : usingBoxes) {
          if (box.doesTheProductFitInTheBox(product)) {
            box.addProduct(product);
            isProductPackaged = true;
            break;
          }
        }

        // Se o produto não foi alocado em uma caixa já em uso, abre uma nova caixa vazia.
        // Itera sobre os tipos de caixa, verificando se o produto cabe em alguma delas.
        // Se sim, adiciona o produto na nova caixa e adiciona a nova caixa na lista de caixa em uso.
        if (isNotProductPackaged(isProductPackaged)) {
          for (Box box : availableBoxes) {
            BoxWithProducts newBox = new BoxWithProducts(box);
            if (newBox.doesTheProductFitInTheBox(product)) {
              newBox.addProduct(product);
              usingBoxes.add(newBox);
              isProductPackaged = true;
              break;
            }
          }
        }

        // Se o produto não couber em nenhum dos tipos de caixa,
        // Adiciona o produto na caixa fictícia de produtos que não couberam em nenhuma caixa.
        if (isNotProductPackaged(isProductPackaged)) {
          nonPackagedProducts.add(product);
        }
      }

      // Após iterar todos os produtos, verifica se existe algum produto não empacotado.
      // Se existe, adiciona os produtos numa caixa fictícia que os representa.
      // E adiciona a caixa na lista de caixas usadas.
      if (isNonPackagedProductsListNotEmpty(nonPackagedProducts.isEmpty())) {
        BoxWithProducts nonPackagedProductsBox = new BoxWithProducts(null);
        nonPackagedProducts.forEach(nonPackagedProductsBox::addProduct);
        usingBoxes.add(nonPackagedProductsBox);
      }

      packaging.put(orderId, usingBoxes);
    }
  }

  /**
   * Valida se o produto não está empacotado.
   */
  private static boolean isNotProductPackaged(boolean isProductPackaged) {
    return !isProductPackaged;
  }

  /**
   * Valida se a lista de produtos não empacotados não está vazia.
   */
  private static boolean isNonPackagedProductsListNotEmpty(boolean isNonPackagedProductsListNotEmpty) {
    return !isNonPackagedProductsListNotEmpty;
  }

  /**
   * Transforma os resultados obtidos no processamento no objeto de resposta a ser devolvido.
   *
   * @param packaging Empacotamento final.
   * @return Resposta devolvida, com os dados sobre os pedidos e seus respectivos empacotamentos.
   */
  private static PackagingResponse buildPackagingResponse(Map<Long, List<BoxWithProducts>> packaging) {
    List<OrdersResponse> ordersResponses = packaging.entrySet().stream()
        .map(entry -> {
          Long orderId = entry.getKey();
          List<BoxWithProducts> boxWithProductsList = entry.getValue();

          List<BoxResponse> boxResponses = boxWithProductsList.stream().map(boxWithProducts -> {
            Box box = boxWithProducts.getBox();

            return BoxResponse.builder()
                .boxId(box != null ? box.id() : null)
                .boxName(box != null ? box.name() : Box.NON_FIT_PRODUCTS_BOX_NAME)
                .products(boxWithProducts.getProductsInsideTheBox().stream()
                    .map(ProductRequest::getProductName)
                    .toList())
                .build();

          }).toList();

          return OrdersResponse.builder()
              .orderId(orderId)
              .boxes(boxResponses)
              .build();
        })
        .toList();

    return PackagingResponse.builder()
        .ordersResponse(ordersResponses)
        .build();
  }
}
