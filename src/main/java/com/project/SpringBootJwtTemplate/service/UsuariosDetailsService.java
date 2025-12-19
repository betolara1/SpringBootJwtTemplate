package com.project.SpringBootJwtTemplate.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.SpringBootJwtTemplate.model.UsuariosDB;
import com.project.SpringBootJwtTemplate.repository.UsuariosRepository;

@Service
public class UsuariosDetailsService implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosDetailsService(UsuariosRepository usuariosRepository){
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuariosDB usuariosDB = usuariosRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        return User.builder()
                .username(usuariosDB.getUsername())
                .password(usuariosDB.getPassword())
                .roles("USER")
                .build();
    }

}
