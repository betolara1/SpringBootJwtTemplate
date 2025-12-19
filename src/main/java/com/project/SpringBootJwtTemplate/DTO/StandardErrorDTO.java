package com.project.SpringBootJwtTemplate.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                // Cria Getters, Setters, toString, equals e hashCode automaticamente
@AllArgsConstructor  // Cria o construtor com todos os argumentos
@NoArgsConstructor   // Cria o construtor vazio (necessário para algumas bibliotecas)
public class StandardErrorDTO {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}