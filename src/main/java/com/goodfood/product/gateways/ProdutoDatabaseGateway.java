package com.goodfood.product.gateways;

import java.util.List;
import java.util.UUID;
import com.goodfood.product.domain.Produto;

public interface ProdutoDatabaseGateway {

  Produto obterPorId(UUID uuid);
  
  Produto salvar(Produto produto);
  
  void remover(UUID uuid);
  
  List<Produto> obterPorCategoria(String category);
  
}
