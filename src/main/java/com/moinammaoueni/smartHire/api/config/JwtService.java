package com.moinammaoueni.smartHire.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    // Génération du token (CLEAN)
    public String generateToken(Map<String, Object> claims, String username) {
        return createToken(claims, username);
	}

    // Construction du token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSignKey())
                .compact();
    }

    //  clé secrète
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    // extraire username
    public String extractUsername(String token) {
    	
        return extractClaim(token, Claims::getSubject);
        
    }

    // extraire n’importe quel claim
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    	
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return resolver.apply(claims);
    }

    // validation token
    public boolean isTokenValid(String token, String username) {
    	
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // expiration
    private boolean isTokenExpired(String token) {
    	
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}