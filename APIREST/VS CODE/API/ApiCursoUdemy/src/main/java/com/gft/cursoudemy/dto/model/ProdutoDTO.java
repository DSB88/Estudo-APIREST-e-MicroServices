package com.gft.cursoudemy.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoDTO {

    private Long id;

    private String nome;

    private Integer quantidade;

    private Double valor;

    private String observacao;
}

