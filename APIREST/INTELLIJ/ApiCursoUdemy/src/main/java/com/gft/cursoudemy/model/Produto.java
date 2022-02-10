package com.gft.cursoudemy.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Este campo Nome, não pode ser vazio.")
    @Size(min = 3, max = 30)
    private String nome;

    @NotNull(message = "Campo quantidade é de preencimento obrigatório.")
    private Integer quantidade;

    @NotNull(message = "Campo valor é de preencimento obrigatório.")
    private Double valor;

    @Size(min = 2, max = 30)
    private String observacao;

}
