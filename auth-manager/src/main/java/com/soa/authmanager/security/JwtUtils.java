package com.soa.authmanager.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwtToken(String email) {
        Key key;
        try {
            key = generateSecretKey();
        } catch (NoSuchAlgorithmException e){
            return null;
        }
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    public Key generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(secureRandom);
        return keyGenerator.generateKey();
    }
}

