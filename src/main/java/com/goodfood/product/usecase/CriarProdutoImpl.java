package com.goodfood.product.usecase;

import org.springframework.stereotype.Component;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;
import com.goodfood.product.gateways.database.entities.ProdutoEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CriarProdutoImpl implements CriarProduto {

  private final ProdutoDatabaseGateway produtoDatabaseGateway;
  
  public Produto executar(Produto produto) {
    return produtoDatabaseGateway.salvar(new ProdutoEntity(produto).toDomain());
  }
  
}
