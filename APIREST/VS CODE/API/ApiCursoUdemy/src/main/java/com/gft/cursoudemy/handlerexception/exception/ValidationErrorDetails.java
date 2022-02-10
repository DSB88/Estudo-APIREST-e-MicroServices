package com.gft.cursoudemy.handlerexception.exception;

import com.gft.cursoudemy.handlerexception.error.ErrorMessage;
import lombok.Data;

@Data
public class ValidationErrorDetails extends ErrorMessage {

    private String field;
    private String fieldMessage;
}
