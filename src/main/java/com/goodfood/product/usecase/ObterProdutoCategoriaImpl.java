package com.goodfood.product.usecase;

import java.util.List;
import org.springframework.stereotype.Component;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObterProdutoCategoriaImpl implements ObterProdutoCategoria {
  
  private final ProdutoDatabaseGateway produtoDatabaseGateway;
  
  @Override
  public List<Produto> execute(String categoria) {
    return produtoDatabaseGateway.findByCategory(categoria);
  }

}
