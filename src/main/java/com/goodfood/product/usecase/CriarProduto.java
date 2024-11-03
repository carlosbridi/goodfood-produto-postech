package com.goodfood.product.usecase;

import com.goodfood.product.domain.Produto;

public interface CriarProduto {

  Produto execute(Produto produto);
  
}
