package com.goodfood.product.domain;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Produto {
  
  private UUID id;
  private String descricao;
  private BigDecimal preco;
  private String categoria;
  
}
