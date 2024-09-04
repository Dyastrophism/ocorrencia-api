package br.univesp.ocorrencia_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedCpfException.class)
    public ResponseEntity<String> handleDuplicatedCpfException(DuplicatedCpfException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
