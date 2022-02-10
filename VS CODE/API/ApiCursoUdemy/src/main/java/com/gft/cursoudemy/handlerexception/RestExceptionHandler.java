package com.gft.cursoudemy.handlerexception;

import java.util.List;
import java.util.stream.Collectors;

import com.gft.cursoudemy.handlerexception.error.ErrorMessage;
import com.gft.cursoudemy.handlerexception.exception.ResourceNotFoundException;
import com.gft.cursoudemy.handlerexception.exception.ValidationErrorDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException ex){
            ErrorMessage error = new ErrorMessage("Not Found",HttpStatus.NOT_FOUND.value(),ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors =  e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
        validationErrorDetails.setStatus (HttpStatus.BAD_REQUEST.value());
        validationErrorDetails.setTitulo("Requisição Inválida");
        validationErrorDetails.setMensagem("Requisição Inválida");
        validationErrorDetails.setField(fields);
        validationErrorDetails.setFieldMessage(fieldMessages);

        return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);

    }

}
