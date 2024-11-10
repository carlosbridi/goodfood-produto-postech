package com.goodfood.product.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.goodfood.product.domain.EProdutoCategoria;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;

class CriarProdutoImplTest {

    @Mock
    private ProdutoDatabaseGateway produtoDatabaseGateway;

    @InjectMocks
    private CriarProdutoImpl criarProdutoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executarDeveSalvarERetornarProduto() {
        final UUID id = UUID.randomUUID();
        Produto produto = Produto.builder().id(id).categoria(EProdutoCategoria.BEBIDA.name()).build();
        Produto expectedProduto = Produto.builder().id(id).categoria(EProdutoCategoria.BEBIDA.name()).build();

        when(produtoDatabaseGateway.salvar(any(Produto.class))).thenReturn(expectedProduto);

        Produto result = criarProdutoImpl.executar(produto);

        assertEquals(expectedProduto, result);
        verify(produtoDatabaseGateway, times(1)).salvar(any(Produto.class));
    }

    @Test
    void executarDeveLancarExcecaoQuandoProdutoForNulo() {
        assertThrows(NullPointerException.class, () -> criarProdutoImpl.executar(null));
    }
}
