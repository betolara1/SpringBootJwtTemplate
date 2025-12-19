package com.project.SpringBootJwtTemplate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SpringBootJwtTemplate.model.UsuariosDB;
import com.project.SpringBootJwtTemplate.security.JwtUtil;
import com.project.SpringBootJwtTemplate.service.UsuariosService;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuariosService usuariosService;
    public AuthController(UsuariosService usuariosService){
        this.usuariosService = usuariosService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        UsuariosDB usuario = usuariosService.registrarUsuarios(request.get("username"), request.get("password"));
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<UsuariosDB> usuario = usuariosService.buscarUsername(request.get("username"));

        // CONDIÇÃO PARA VER SE O USUARIO EXISTE E A SENHA FOR IGUAL
        if (usuario.isPresent() && usuario.get().getPassword().equals(request.get("password"))){
            String token = JwtUtil.generateToken(usuario.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Credenciais invalidas");
    }

    
    
}
