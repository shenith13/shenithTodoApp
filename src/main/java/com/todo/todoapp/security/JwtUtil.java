package com.todo.todoapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// Utility class to generate and validate JWT tokens
@Component
public class JwtUtil {

    // Secret key used to sign the JWT (generated once when the app starts)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token expiration time in milliseconds (1 day = 24 * 60 * 60 * 1000)
    private final long EXPIRATION_TIME = 86400000;

    // Generate a JWT token using the username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                      // Set the username as the token subject
                .setIssuedAt(new Date())                   // Set issue time to now
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(key)                             // Sign the token with the secret key
                .compact();                               // Build the token string
    }

    // Extract username from the JWT token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)                        // Set the signing key for validation
                .build()
                .parseClaimsJws(token)                     // Parse the JWT claims from the token
                .getBody()
                .getSubject();                            // Return the username (subject)
    }

    // Validate the JWT token: returns true if valid, false otherwise
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);                // Try parsing the token
            return true;                                 // If no exceptions, token is valid
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid or expired
            return false;
        }
    }
}
