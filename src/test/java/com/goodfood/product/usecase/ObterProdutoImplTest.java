package com.goodfood.product.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.goodfood.product.domain.NotFoundException;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;

class ObterProdutoImplTest {

    @Mock
    private ProdutoDatabaseGateway produtoDatabaseGateway;

    @InjectMocks
    private ObterProdutoImpl obterProdutoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeShouldReturnProdutoWhenIdIsValid() {
        UUID id = UUID.randomUUID();
        Produto expectedProduto = Produto.builder().id(id).build();

        when(produtoDatabaseGateway.findById(id)).thenReturn(expectedProduto);

        Produto result = obterProdutoImpl.execute(id.toString());

        assertEquals(expectedProduto, result);
    }

    @Test
    void executeShouldThrowExceptionWhenProdutoNotFound() {
        UUID id = UUID.randomUUID();

        when(produtoDatabaseGateway.findById(id)).thenThrow(new NotFoundException("Produto não encontrado."));

        assertThrows(NotFoundException.class, () -> obterProdutoImpl.execute(id.toString()));
    }
}
