package com.project.SpringBootJwtTemplate.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // CHAVE PARA FAZER A CRIPTOGRAFIA DA SENHA  
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // VARIAVEL PARA EXPIRAÇÃO DO TOKEN
    private static final long EXPIRATION_TIME = 86400000; // 86400000 MINUTOS = 24 HORAS

    // ESSE METODO VAI GERAR O TOKEN PRONTO 
    public static String generateToken(String username){
        return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
    }


    // METODO PARA EXTRAIR O USUARIO
    public static String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
    }


    // METODO PARA VALIDAR O TOKEN
    public static boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch(JwtException e){
            return false;
        }
    }
}
