package com.goodfood.product.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;

class ObterProdutoCategoriaImplTest {

    @Mock
    private ProdutoDatabaseGateway produtoDatabaseGateway;

    @InjectMocks
    private ObterProdutoCategoriaImpl obterProdutoCategoriaImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeShouldReturnProdutosWhenCategoriaIsValid() {
        String categoria = "BEBIDA";
        List<Produto> expectedProdutos = List.of(Produto.builder().categoria(categoria).build());

        when(produtoDatabaseGateway.findByCategory(categoria)).thenReturn(expectedProdutos);

        List<Produto> result = obterProdutoCategoriaImpl.execute(categoria);

        assertEquals(expectedProdutos, result);
    }

    @Test
    void executeShouldReturnEmptyListWhenCategoriaHasNoProdutos() {
        String categoria = "BEBIDA";

        when(produtoDatabaseGateway.findByCategory(categoria)).thenReturn(Collections.emptyList());

        List<Produto> result = obterProdutoCategoriaImpl.execute(categoria);

        assertEquals(Collections.emptyList(), result);
    }
}
