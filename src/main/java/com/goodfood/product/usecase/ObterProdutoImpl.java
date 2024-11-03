package com.goodfood.product.usecase;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObterProdutoImpl implements ObterProduto {
  
  private final ProdutoDatabaseGateway produtoDatabaseGateway;
  
  @Override
  public Produto execute(String idProduto) {
    return produtoDatabaseGateway.findById(UUID.fromString(idProduto));
  }

}
