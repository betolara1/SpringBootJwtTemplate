package com.project.SpringBootJwtTemplate.exception;

public class RecursoNaoEncontradoException extends RuntimeException{

    // CONSTRUTOR PARA RETORNAR A MENSAGEM DE ERRO 
    public RecursoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
