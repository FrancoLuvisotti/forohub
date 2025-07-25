package com.francoLuvisotti.forohub.infra.security;

import com.francoLuvisotti.forohub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS256, apiSecret)
                .compact();
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token inválido");
        }

        return Jwts.parser()
                .setSigningKey(apiSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}