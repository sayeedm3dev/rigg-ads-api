package com.rigg.ads.components;

import com.rigg.ads.entity.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;


    public String generateToken(String username, Set<Role> roles, Long clientId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("clientId", clientId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }


    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRolesFromToken(String token) {
        Claims claims = parseClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof Collection) {
            return ((Collection<?>) rolesObj).stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public Long getClientIdFromToken(String token) {
        Claims claims = parseClaims(token);
        Object clientIdObj = claims.get("clientId");
        if (clientIdObj != null) {
            return Long.valueOf(clientIdObj.toString());
        }
        return null;
    }

    public boolean validateToken(String authToken) {
//        System.out.println("JWT secret used for validation: " + jwtSecret); // ✅ Add this line
        try {
            parseClaims(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
