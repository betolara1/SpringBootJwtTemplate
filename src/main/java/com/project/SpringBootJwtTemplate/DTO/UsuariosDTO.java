package com.project.SpringBootJwtTemplate.DTO;

import com.project.SpringBootJwtTemplate.model.UsuariosDB;

import lombok.Data;

@Data
public class UsuariosDTO {
    private Long id;
    private String username;

    public UsuariosDTO (){}

    public UsuariosDTO (UsuariosDB usuarios){
        this.id = usuarios.getId();
        this.username = usuarios.getUsername();
    }
}
