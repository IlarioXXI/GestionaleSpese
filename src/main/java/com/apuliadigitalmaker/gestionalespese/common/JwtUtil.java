package com.apuliadigitalmaker.gestionalespese.common;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "keb5nOfeC9PlMXJIez0MghFB5zdCdskJzqNwcBukOejNiUImPOZCuPz9JJO8HhePHI6nNUDcxbhxBFLxWwpTgUR0D8a5nNVHd1TOBDXvlQX4gbzMWXz4VUagkoVYRoON3QojvlJB8wTRQsHVlJg24LWSlb2nntYROwcYHlroEDEPYvLF8XxTb8TkbaAvjnqzkmdI0iXcd1hFxRwUGGmTYDZmi8MbF7KIaH9KCYWSzmj6OwHw9bVjqSg1gxAfApOk";
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String id) {
        long expirationTime = 3600000;
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String userId) {
        return (userId.equals(extractId(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.before(new Date());
    }




}
