package com.utopia.api.utilities;

import com.utopia.api.dao.UsersDAO;
import com.utopia.api.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final SecretKey key;
    private final UsersDAO usersDAO;

    public JwtUtil(JdbcTemplate jdbcTemplate) {
        this.usersDAO = new UsersDAO(jdbcTemplate);
        // Generate a secure key with sufficient length for HS256
        this.key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    }

    public String generateToken(User user) {
        Date now = new Date();
        long expirationTimeMillis = 24 * (60 * 60 * 1000); // 24 hours in milliseconds
        Date expirationDate = new Date(now.getTime() + expirationTimeMillis);

        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("userName", user.getName())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            long userId = extractUserId(token);
            return usersDAO.exists(userId);
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.err.println("Malformed JWT token: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error validating JWT token: " + e.getMessage());
            return false;
        }
    }

    public long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return Long.parseLong(claims.get("userId").toString());
    }
}
