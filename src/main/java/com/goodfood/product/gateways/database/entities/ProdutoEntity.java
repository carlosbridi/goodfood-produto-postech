package com.goodfood.product.gateways.database.entities;

import java.math.BigDecimal;
import java.util.UUID;
import com.goodfood.product.domain.EProdutoCategoria;
import com.goodfood.product.domain.Produto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class ProdutoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String descricao;
  private BigDecimal preco;
  @Enumerated(EnumType.STRING)
  private EProdutoCategoria categoria;
  
  public ProdutoEntity(Produto produto) {
    id = produto.getId();
    descricao = produto.getDescricao();
    preco = produto.getPreco();
    categoria = EProdutoCategoria.getByString(produto.getCategoria());
  }
  
  public Produto toDomain(){
    return Produto.builder()
      .id(id)
      .descricao(descricao)
      .preco(preco)
      .categoria(categoria.toString())
    .build();
  }
}
