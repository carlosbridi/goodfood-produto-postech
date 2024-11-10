package com.goodfood.product.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.goodfood.product.domain.NotFoundException;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.ProdutoDatabaseGateway;

class EditarProdutoImplTest {

    @Mock
    private ProdutoDatabaseGateway produtoDatabaseGateway;

    @InjectMocks
    private EditarProdutoImpl editarProdutoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeShouldEditAndSaveProduto() {
        UUID id = UUID.randomUUID();
        Produto existingProduto = Produto.builder().id(id).preco(BigDecimal.valueOf(10.0)).descricao("Old Description").categoria("Old Category").build();
        Produto editedProduto = Produto.builder().preco(BigDecimal.valueOf(20.0)).descricao("New Description").categoria("New Category").build();

        when(produtoDatabaseGateway.findById(id)).thenReturn(existingProduto);

        editarProdutoImpl.execute(id, editedProduto);

        verify(produtoDatabaseGateway, times(1)).save(existingProduto);
        assertEquals(0, existingProduto.getPreco().compareTo(BigDecimal.valueOf(20.0)));
        assertEquals("New Description", existingProduto.getDescricao());
        assertEquals("New Category", existingProduto.getCategoria());
    }

    @Test
    void executeShouldThrowExceptionWhenProdutoNotFound() {
        UUID id = UUID.randomUUID();
        Produto editedProduto = Produto.builder().preco(BigDecimal.valueOf(20.0)).descricao("New Description").categoria("New Category").build();

        when(produtoDatabaseGateway.findById(id)).thenThrow(new NotFoundException("Produto não encontrado."));

        assertThrows(NotFoundException.class, () -> editarProdutoImpl.execute(id, editedProduto));
    }
}
