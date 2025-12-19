package com.project.SpringBootJwtTemplate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class UsuariosDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public UsuariosDB(){}

    public UsuariosDB(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
