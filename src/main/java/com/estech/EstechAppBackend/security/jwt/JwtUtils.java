package com.estech.EstechAppBackend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    /**
     * Method that generates a Json Web Token.
     * It sets the subject (user), the generation date, the expiration date and the key algorithm.
     * @param email -> UserEntity Object email attribute, that will identify the user
     * @return Token to be provided to the user
     */
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Method that decodes the "Secret Key" in a Spring Security Key.
     * It is needed to sign the Json Web Tokens.
     * @return the Spring Security Key Object
     */
    public Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Method to get all the Claims contained in a JWT body.
     * @param token -> JWT
     * @return All the claims contained in JWT
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Method that get a specific claim contained in a JWT body.
     * @param token -> Token
     * @param claimsTFunction -> An argument function.
     * @return The claim that is being requested.
     * @param <T> -> The claim type that is being requested.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String getUserFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Method that receives a token and checks if it is valid or not.
     * @param token -> JWT
     * @return true if token is valid, false if it is not.
     */
    public Boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            logger.error("Token invalido " + e.getCause().getMessage());
            return false;
        }
    }

}
