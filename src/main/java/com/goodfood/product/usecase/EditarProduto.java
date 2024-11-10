package com.goodfood.product.usecase;

import java.util.UUID;
import com.goodfood.product.domain.Produto;

public interface EditarProduto {
  
  void executar(UUID id, Produto produtoEdited);
  
}
