package com.goodfood.product.gateways.http;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.goodfood.product.domain.Produto;
import com.goodfood.product.usecase.CriarProduto;
import com.goodfood.product.usecase.EditarProduto;
import com.goodfood.product.usecase.ObterProduto;
import com.goodfood.product.usecase.ObterProdutoCategoria;

@WebMvcTest(ProdutoWebController.class)
class ProdutoWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObterProduto obterProduto;

    @MockBean
    private CriarProduto criarProduto;

    @MockBean
    private EditarProduto editarProduto;

    @MockBean
    private ObterProdutoCategoria obterProdutoCategoria;

    @Test
    void cadastrarProdutoComRequisicaoValida() throws Exception {
        Produto produto = Produto.builder()
                .id(UUID.randomUUID())
                .descricao("Coca-cola")
                .preco(new BigDecimal("10.00"))
                .categoria("BEBIDA")
                .build();

        when(criarProduto.executar(any())).thenReturn(produto);

        mockMvc.perform(post("/produto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"descricao\": \"Coca-cola\", \"preco\": 10.00, \"categoria\": \"BEBIDA\" }"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/" + produto.getId()))
                .andExpect(jsonPath("$.descricao").value("Coca-cola"))
                .andExpect(jsonPath("$.preco").value(10.00))
                .andExpect(jsonPath("$.categoria").value("BEBIDA"));
    }

    @Test
    void editarProdutoComRequisicaoValida() throws Exception {
        Produto produto = Produto.builder()
                .id(UUID.randomUUID())
                .descricao("Coca-cola")
                .preco(new BigDecimal("10.00"))
                .categoria("BEBIDA")
                .build();

        doNothing().when(editarProduto).executar(any(), any());

        mockMvc.perform(put("/produto/" + produto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"descricao\": \"Coca-cola\", \"preco\": 10.00, \"categoria\": \"BEBIDA\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void obterProdutoComIdValido() throws Exception {
        Produto produto = Produto.builder()
                .id(UUID.randomUUID())
                .descricao("Coca-cola")
                .preco(new BigDecimal("10.00"))
                .categoria("BEBIDA")
                .build();

        when(obterProduto.executar(anyString())).thenReturn(produto);

        mockMvc.perform(get("/produto")
                                .param("id", produto.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Coca-cola"))
                .andExpect(jsonPath("$.preco").value(10.00))
                .andExpect(jsonPath("$.categoria").value("BEBIDA"));
    }

    @Test
    void buscarPorCategoriaComCategoriaValida() throws Exception {
        Produto produto = Produto.builder()
                .id(UUID.randomUUID())
                .descricao("Coca-cola")
                .preco(new BigDecimal("10.00"))
                .categoria("BEBIDA")
                .build();

        when(obterProdutoCategoria.executar(anyString())).thenReturn(Collections.singletonList(produto));

        mockMvc.perform(get("/produto/categoria")
                                .param("categoria", "BEBIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Coca-cola"))
                .andExpect(jsonPath("$[0].preco").value(10.00))
                .andExpect(jsonPath("$[0].categoria").value("BEBIDA"));
    }
}
