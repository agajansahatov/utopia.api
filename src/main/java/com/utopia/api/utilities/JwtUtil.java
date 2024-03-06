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

    private final SecretKey key;
    private final UsersDAO usersDAO;

    public JwtUtil(JdbcTemplate jdbcTemplate, @Value("${jwt.secret}") String secretKey) {
        this.usersDAO = new UsersDAO(jdbcTemplate);

        //The secretKey parameter should be removed in the production mode.
        //This line should be commented out in the production mode.
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // This line should be enabled in the production mode. Because it generates more secure key.
        // this.key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    }


    public String generateToken(User user) {
        Date now = new Date();
        long expirationTimeMillis = 24 * (60 * 60 * 1000); // 24 hours in milliseconds
        Date expirationDate = new Date(now.getTime() + expirationTimeMillis);

        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("userRole", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public JwtChecked validate(String token) {
        JwtChecked jwtChecked = new JwtChecked();

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();


            if (!claims.containsKey("userId")) {
                System.err.println("Error validating JWT: Missing userId claim");
                return jwtChecked;
            }
            if (!claims.containsKey("userRole")) {
                System.err.println("Error validating JWT: Missing userRole claim");
                return jwtChecked;
            }

            long userId = Long.parseLong(claims.get("userId").toString());
            if (!usersDAO.exists(userId)) {
                System.err.println("Error validating JWT: User doesn't exist or might be deleted!");
                return jwtChecked;
            }

            String userRole = claims.get("userRole").toString();
            String existingUserRole = usersDAO.getRole(userId);
            if(existingUserRole == null) {
                System.err.println("'role' field is null in the database!!!");
                return jwtChecked;
            }
            if (!userRole.equals("user") && !existingUserRole.equals(userRole)) {
                System.err.println("Error validating JWT: User role mismatch!");
                return jwtChecked;
            }

            jwtChecked.isValid = true;
            jwtChecked.userId = userId;
            jwtChecked.userRole = userRole;
            return jwtChecked;
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token expired: " + e.getMessage());
            return jwtChecked;
        } catch (MalformedJwtException e) {
            System.err.println("Malformed JWT token: " + e.getMessage());
            return jwtChecked;
        } catch (Exception e) {
            System.err.println("Error validating JWT token: " + e.getMessage());
            return jwtChecked;
        }
    }

//    public long extractUserId(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//        return Long.parseLong(claims.get("userId").toString());
//    }
//
//    public String extractUserRole(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//        return claims.get("userRole").toString();
//    }

}