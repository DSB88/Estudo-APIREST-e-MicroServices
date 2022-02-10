package com.gft.cursoudemy.controller;

import com.gft.cursoudemy.dto.model.ProdutoDTO;
import com.gft.cursoudemy.dto.handlers.ProdutoRequest;
import com.gft.cursoudemy.dto.handlers.ProdutoResponse;
import com.gft.cursoudemy.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/produtos")
@CrossOrigin(origins = "*")
@Api(value="API REST Produtos")
public class ProdutoController {

    final private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @ApiOperation(value="Retorna uma lista de todos os produtos cadastrados")
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos(){

        List<ProdutoDTO> produtosDTO = produtoService.obterTodos();

        ModelMapper mapper = new ModelMapper();

        List<ProdutoResponse> responses =  produtosDTO.stream()
                .map(produtoDTO -> mapper.map(produtoDTO, ProdutoResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value="Retorna um produto único, pelo seu id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> obterPorId(@PathVariable Long id){

        Optional<ProdutoDTO> produtoDTO = produtoService.obterPorId(id);

        ProdutoResponse produtoResponse = new ModelMapper().map(produtoDTO.get(),ProdutoResponse.class);

        return new ResponseEntity<>(Optional.of(produtoResponse), HttpStatus.OK);
    }

    @ApiOperation(value="Adiciona(salva) um produto")
    @PostMapping()
    public ResponseEntity<ProdutoResponse> adicionar(@Valid @RequestBody ProdutoResponse produtoResponse){
        ModelMapper mapper = new ModelMapper();

        ProdutoDTO produtoDTO = mapper.map(produtoResponse, ProdutoDTO.class);

        produtoDTO = produtoService.adicionarProduto(produtoDTO);

        return new ResponseEntity<>(mapper.map(produtoDTO, ProdutoResponse.class),HttpStatus.CREATED);
    }

    @ApiOperation(value="Deleta um produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value="Atualiza um produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id,@Valid @RequestBody ProdutoRequest produtoRequest){
        ModelMapper mapper = new ModelMapper();

        ProdutoDTO produtoDTO = mapper.map(produtoRequest, ProdutoDTO.class);

        produtoDTO = produtoService.atualizarProduto(id,produtoDTO);

        return new ResponseEntity<>(
                mapper.map(produtoDTO, ProdutoResponse.class),
                HttpStatus.OK);
    }

}
