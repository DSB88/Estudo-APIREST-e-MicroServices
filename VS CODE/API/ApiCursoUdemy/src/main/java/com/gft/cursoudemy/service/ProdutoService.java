package com.gft.cursoudemy.service;

import com.gft.cursoudemy.model.Produto;
import com.gft.cursoudemy.dto.model.ProdutoDTO;
import com.gft.cursoudemy.handlerexception.exception.ResourceNotFoundException;
import com.gft.cursoudemy.repository.ProdutoRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    /**
     * Método para retornar uma lista de produtoRepository
     * @return lista de produtoRepository.
     */
    public List<ProdutoDTO> obterTodos(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método que retorna o produto encontrado pelo seu ID.
     * @param id do produtoDTO que será localizado
     * @return Retorna um produto caso seja encontrado.
     */
    public Optional<ProdutoDTO> obterPorId(Long id){
        Optional<ProdutoDTO> produtoDTO = verificaSeProdutoExiste(id);

        ProdutoDTO dto = new ModelMapper().map(produtoDTO.get(),ProdutoDTO.class);
        return Optional.of(dto);
    }

    /**
     * Método para adicionar produto na lista.
     * @param produtoDTO que será adicionado.
     * @return Retorna o produto que foi adicionado na lista.
     */
    public ProdutoDTO adicionarProduto(ProdutoDTO produtoDTO){
        try {
            //Remove o id para conseguir garantir a inserção do cadastro.
            produtoDTO.setId(null);
            //Cria um objeto de mapeamento
            ModelMapper mapper = new ModelMapper();
            //Converte o nosso ProdutoDTO em um Produto
            Produto produto = mapper.map(produtoDTO, Produto.class);
            //Salva o produto no banco de dados
            produto = produtoRepository.save(produto);
            //Altero o id do produtoDTO com o id novo do produto
            produtoDTO.setId(produto.getId());
            //Retorno o produtoDTO com o produto com id novo criado
        } catch (ConstraintViolationException exceptionError) {
            //Lança uma exception de validação de dados.
            throw exceptionError;
        }
        return produtoDTO;
    }


    /**
     * Método para deletar um produto na lista.
     * É usado o verificaSeProdutoExiste para conferência se exister, deleta o produto se não lança uma exception.
     * @param id que será deletado.
     */
    public void deletarProduto(Long id){
        Optional<ProdutoDTO> produtoDTO = verificaSeProdutoExiste(id);
        produtoRepository.deleteById(id);
    }

    /**
     * Método para atualizar o produto na lista.
     * @param produtoDTO que será atualizado.
     * @param id do produto.
     * @return Retorna o produto após atualizar a lista.
     */
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO){
        //Verifique se o id existe, caso não lança uma exception.
        Optional<ProdutoDTO> dto = verificaSeProdutoExiste(id);

        //Passa o id para o produtoDTO.
        produtoDTO.setId(id);

        //Cria o objeto de mapeamento.
        ModelMapper mapper = new ModelMapper();

        //Converte o ProdutoDTO em Produto.
        Produto produto = mapper.map(produtoDTO, Produto.class);

        //Atualiza o produto no banco de dados.
        produtoRepository.save(produto);

        //Retorna o produtoDTO Atualizado.
        return produtoDTO;

    }

    /**
     * Método para verificar se o produto existe.
     * @param id do produto.
     * @return
     */
    private Optional<ProdutoDTO> verificaSeProdutoExiste(Long id){
        Optional<Produto> produtoEncontrado =  produtoRepository.findById(id);

        if(!produtoEncontrado.isPresent()){
            throw new ResourceNotFoundException("O Produto inexistente.");
        }
        ProdutoDTO produtoDTO = new ModelMapper().map(produtoEncontrado.get(),ProdutoDTO.class);
        return Optional.of(produtoDTO);
    }

}

