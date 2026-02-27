package org.challengegroup.coursesrecomendation.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class InternalJwtProvider {
    @Value("${internal.jwt.secret}")
    private String internalSecret;
    @Value("${internal.jwt.expiration}")
    private long internalExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(internalSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateInternalToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + internalExpiration);
        return Jwts.builder()
                .subject("spring-boot-service")
                .claim("service", "couse-recommendation")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }
}
