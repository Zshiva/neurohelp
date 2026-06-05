package com.project.neurohelp.security;

import com.project.neurohelp.repositories.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final JwtProperties properties;
    private final SecretKey key;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
        this.key = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserEntity user) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(properties.accessTokenExpirationMinutes() * 60L);
        String userId = user.getId() == null ? "" : user.getId().toString();
        String role = user.getRoles() == null ? "USER" : user.getRoles().name();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer(properties.issuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim("uid", userId)
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public long getAccessTokenExpiresInSeconds() {
        return properties.accessTokenExpirationMinutes() * 60L;
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(properties.issuer())
                .build()
                .parseClaimsJws(token);
    }
}

