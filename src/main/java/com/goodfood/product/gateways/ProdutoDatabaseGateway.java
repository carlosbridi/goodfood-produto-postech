package com.goodfood.product.gateways;

import java.util.List;
import java.util.UUID;
import com.goodfood.product.domain.Produto;

public interface ProdutoDatabaseGateway {

  Produto findById(UUID uuid);
  
  Produto save(Produto produto);
  
  void delete(UUID uuid);
  
  List<Produto> findByCategory(String category);
  
}
