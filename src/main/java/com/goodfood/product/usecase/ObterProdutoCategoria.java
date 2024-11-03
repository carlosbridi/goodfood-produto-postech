package com.goodfood.product.usecase;

import java.util.List;
import com.goodfood.product.domain.Produto;

public interface ObterProdutoCategoria {
  List<Produto> execute(String categoria);
}
