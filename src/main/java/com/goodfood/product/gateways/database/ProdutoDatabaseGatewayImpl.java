package com.goodfood.product.gateways.database;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.goodfood.product.domain.EProdutoCategoria;
import com.goodfood.product.domain.NotFoundException;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;
import com.goodfood.product.gateways.database.entities.ProdutoEntity;
import com.goodfood.product.gateways.database.repositories.ProdutoRepository;

@Component
public class ProdutoDatabaseGatewayImpl implements ProdutoDatabaseGateway {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Override
  public Produto save(Produto produto) {
    return produtoRepository.save(new ProdutoEntity(produto)).toDomain();
  }

  @Override
  public void delete(UUID uuid) {
    produtoRepository.deleteById(uuid);
  }

  @Override
  public List<Produto> findByCategory(String category) {
    return CollectionUtils.emptyIfNull(produtoRepository.findByCategoria(EProdutoCategoria.getByString(category)))
        .stream()
        .map(ProdutoEntity::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Produto findById(UUID uuid) {
    return produtoRepository
        .findById(uuid)
        .map(ProdutoEntity::toDomain)
        .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
  }

}
