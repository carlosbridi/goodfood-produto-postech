package com.goodfood.product.usecase;

import com.goodfood.product.domain.Produto;

public interface ObterProduto {

  Produto executar(String idProduto);

  
}
