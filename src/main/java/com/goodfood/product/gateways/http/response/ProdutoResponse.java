package com.goodfood.product.gateways.http.response;

import java.io.Serializable;
import java.math.BigDecimal;
import com.goodfood.product.domain.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse implements Serializable {

  private static final long serialVersionUID = 5033384337768652970L;
  
  private String id;
  private String descricao;
  private BigDecimal preco;
  private String categoria;
  
  public ProdutoResponse(final Produto produto) {
    this.id = produto.getId().toString();
    this.descricao = produto.getDescricao();        
    this.preco = produto.getPreco();
    this.categoria = produto.getCategoria();
  }

}
