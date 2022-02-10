package com.gft.cursoudemy.handlerexception.error;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {

    private String titulo;
    private Integer status;
    private String mensagem;
}
