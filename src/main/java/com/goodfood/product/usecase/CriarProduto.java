package com.goodfood.product.usecase;

import com.goodfood.product.domain.Produto;

public interface CriarProduto {

  Produto executar(Produto produto);
  
}
