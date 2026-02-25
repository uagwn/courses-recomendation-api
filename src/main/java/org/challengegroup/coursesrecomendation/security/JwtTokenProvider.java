package org.challengegroup.coursesrecomendation.security;
import lombok.extern.slf4j.Slf4j;

import org.challengegroup.coursesrecomendation.entity.User;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long JwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    };

    public String generateToken(User user) {
        Date now = new Date();

        Date expiration = new Date(now.getTime() + JwtExpiration);
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("nome", user.getName())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String getTokenEmail(String token) {
        return getClaims(token).getSubject();
    }

    public Long getTokenUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Token not suported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Token bad formed: {}", e.getMessage());
        } catch (SecurityException e) {
            log.warn("Invalid sign: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Empty token: {}", e.getMessage());
        }
        return false;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
