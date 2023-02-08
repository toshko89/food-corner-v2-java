package com.example.food_corner_v2_java.auth;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class JwtService {

    private final JwtKeyProps jwtKeyProps;

    public JwtService(JwtKeyProps jwtKeyProps) {
        this.jwtKeyProps = jwtKeyProps;
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> claims, AppUser userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setId(userDetails.getId().toString())
                .addClaims(new HashMap<>() {{
                    put("role", userDetails.getUserRole().name());
                }})
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getJwtKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(AppUser userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getJwtKey()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid token " + e.getMessage());
        }
    }

    private Key getJwtKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtKeyProps.key());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
