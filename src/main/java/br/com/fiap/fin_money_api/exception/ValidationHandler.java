package br.com.fiap.fin_money_api.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// capturar os erros e devolve uma resposta, no formato json
// quando falha uma validação do BeenValidation, ele retorna um erro 400 com o json 
@RestControllerAdvice 
public class ValidationHandler {
    record ValidationError (String field, String message) {

        public ValidationError(FieldError error) {
           this(error.getField(), error.getDefaultMessage());
        }}

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handler(MethodArgumentNotValidException e){
        return e.getFieldErrors()
                .stream()
                .map(ValidationError::new) //método de referencia 
                .toList();
    } //pegar só o nome do campo e a mensagem de erro 
}

//exception tem um atributo field errors