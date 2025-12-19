package com.project.SpringBootJwtTemplate.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.SpringBootJwtTemplate.DTO.StandardErrorDTO;

import jakarta.servlet.http.HttpServletRequest; // Importante para pegar o caminho da URL

@ControllerAdvice
public class GlobalExceptionHandler {

    // TRATAMENTO DE RECURSO NÃO ENCONTRADO (404)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<StandardErrorDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        
        StandardErrorDTO erro = new StandardErrorDTO(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Não Encontrado",
            ex.getMessage(),
            request.getRequestURI() // Mostra qual URL o usuário tentou acessar
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // TRATAMENTO GENÉRICO (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        
        StandardErrorDTO erro = new StandardErrorDTO(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno do Servidor",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
