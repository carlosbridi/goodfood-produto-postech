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

import com.goodfood.product.domain.NaoEncontradoException;
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
    void executarDeveRetornarProdutoQuandoIdForValido() {
        UUID id = UUID.randomUUID();
        Produto expectedProduto = Produto.builder().id(id).build();

        when(produtoDatabaseGateway.obterPorId(id)).thenReturn(expectedProduto);

        Produto result = obterProdutoImpl.executar(id.toString());

        assertEquals(expectedProduto, result);
    }

    @Test
    void executarDeveLancarExcecaoQuandoProdutoNaoForEncontrado() {
        UUID id = UUID.randomUUID();

        when(produtoDatabaseGateway.obterPorId(id)).thenThrow(new NaoEncontradoException("Produto não encontrado."));

        assertThrows(NaoEncontradoException.class, () -> obterProdutoImpl.executar(id.toString()));
    }
}
