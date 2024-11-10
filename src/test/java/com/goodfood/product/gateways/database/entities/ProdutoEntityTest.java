package com.goodfood.product.gateways.database.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.goodfood.product.domain.EProdutoCategoria;
import com.goodfood.product.domain.Produto;

class ProdutoEntityTest {

    @Test
    void produtoEntityDeveSerCriadoAPartirDoDominioCorretamente() {
        UUID id = UUID.randomUUID();
        ProdutoEntity produtoEntity = ProdutoEntity.builder().id(id).descricao("Test Product").preco(BigDecimal.valueOf(10.99)).categoria(EProdutoCategoria.BEBIDA).build();

        Produto produto = produtoEntity.toDomain();

        assertEquals(id, produto.getId());
        assertEquals("Test Product", produto.getDescricao());
        assertEquals(BigDecimal.valueOf(10.99), produto.getPreco());
        assertEquals("BEBIDA", produto.getCategoria());
    }

    @Test
    void produtoEntityDeveTerIdNaoNuloAposCriacao() {
        UUID id = UUID.randomUUID();
        Produto produto = Produto.builder().id(id).descricao("Test Product").preco(BigDecimal.valueOf(10.99)).categoria("BEBIDA").build();

        ProdutoEntity produtoEntity = new ProdutoEntity(produto);

        assertEquals(id, produtoEntity.getId());
        assertEquals("Test Product", produtoEntity.getDescricao());
        assertEquals(BigDecimal.valueOf(10.99), produtoEntity.getPreco());
        assertEquals(EProdutoCategoria.BEBIDA, produtoEntity.getCategoria());
    }
}
