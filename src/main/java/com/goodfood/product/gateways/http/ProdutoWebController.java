package com.goodfood.product.gateways.http;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.goodfood.product.domain.Produto;
import com.goodfood.product.gateways.http.request.ProdutoRequest;
import com.goodfood.product.gateways.http.response.ProdutoResponse;
import com.goodfood.product.usecase.CriarProduto;
import com.goodfood.product.usecase.EditarProduto;
import com.goodfood.product.usecase.ObterProduto;
import com.goodfood.product.usecase.ObterProdutoCategoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("produto")
@Api(value = "/produto", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProdutoWebController {

  private final ObterProduto obterProduto;
  private final CriarProduto criarProduto;
  private final EditarProduto editarProduto;
  private final ObterProdutoCategoria obterProdutoCategoria;

  @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 201, message = "Created")})
  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping
  @ApiImplicitParams({
      @ApiImplicitParam(name = "descricao", value = "Descrição do produto", required = true,
          dataType = "string", paramType = "body"),
      @ApiImplicitParam(name = "preco", value = "Preço do produto", required = true,
          dataType = "BigDecimal", paramType = "body"),
      @ApiImplicitParam(name = "categoria", value = "Categoria do produto", required = true,
          dataType = "string", paramType = "body"),})
  @Operation(summary = "Criar produto", description = "Cadastrar um produto",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Produto a ser cadastrado", required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProdutoRequest.class),
              examples = @ExampleObject(
                  value = "{ \"descricao\": \"Coca-cola\", \"preco\": 10.00, \"categoria\": \"BEBIDA\" }"))))
  @CacheEvict(value = "ProdutoResponse", allEntries = true)
  public ResponseEntity<ProdutoResponse> cadastrarProduto(
      @RequestBody ProdutoRequest produtoRequest) {
    final Produto produto = criarProduto.executar(produtoRequest.toDomain());
    return ResponseEntity.created(URI.create("/" + produto.getId())).body(new ProdutoResponse(produto));
  }

  @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 404, message = "Not found")})
  @PutMapping(path = "/{id}")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "descricao", value = "Descrição do produto", required = true,
          dataType = "string", paramType = "body"),
      @ApiImplicitParam(name = "preco", value = "Preço do produto", required = true,
          dataType = "BigDecimal", paramType = "body"),
      @ApiImplicitParam(name = "categoria", value = "Categoria do produto", required = true,
          dataType = "string", paramType = "body"),})
  @Operation(summary = "Editar produto", description = "Editar um produto",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Produto a ser editado", required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProdutoRequest.class),
              examples = @ExampleObject(
                  value = "{ \"descricao\": \"Coca-cola\", \"preco\": 10.00, \"categoria\": \"BEBIDA\" }"))))
  @CacheEvict(value = "ProdutoResponse", allEntries = true)
  public ResponseEntity<Void> editarProduto(@PathVariable String id,
      @RequestBody ProdutoRequest produtoRequest) {
    editarProduto.executar(UUID.fromString(id), produtoRequest.toDomain());
    return ResponseEntity.ok().build();
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 201, message = "Created")})
  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping
  public ResponseEntity<ProdutoResponse> obterProduto(@RequestParam String id) {
    final Produto produto = obterProduto.executar(id);
    return ResponseEntity.ok(new ProdutoResponse(produto));
  }


  @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 404, message = "Not found")})
  @GetMapping(path = "/categoria")
  @Operation(summary = "Buscar produtos por categoria",
      description = "Buscar produtos por categoria",
      parameters = @io.swagger.v3.oas.annotations.Parameter(name = "categoria",
          description = "Categoria do produto", required = true, example = "BEBIDA"))
  @Cacheable(value = "ProdutoResponse", key = "#categoria")
  public ResponseEntity<List<ProdutoResponse>> buscarPorCategoria(@RequestParam String categoria) {
    return ResponseEntity.ok(obterProdutoCategoria.executar(categoria).stream().map(ProdutoResponse::new).collect(Collectors.toList()));
  }

}
