package com.utopia.api.utilities;

import com.utopia.api.dao.UsersDAO;
import com.utopia.api.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final UsersDAO usersDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

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
        Timestamp authTime = new Timestamp(now.getTime());
        CompletableFuture<Void> setAuthTimeFuture = CompletableFuture.runAsync(() -> {
            usersDAO.setAuthTime(user.getId(), authTime);
        });
        setAuthTimeFuture.join();

        Timestamp authTimeFromDb = usersDAO.getAuthTime(user.getId());

        long expirationTimeMillis = 24 * (60 * 60 * 1000); // 24 hours in milliseconds
        Date expirationDate = new Date(authTimeFromDb.getTime() + expirationTimeMillis);

        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("userRole", user.getRole())
                .setIssuedAt(authTimeFromDb)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public JwtChecked validate(String token) {
        // Here jwtChecked.isValid = false;
        JwtChecked jwtChecked = new JwtChecked();

        if (token == null || token.isEmpty()) {
            return jwtChecked;
        }

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            if (!claims.containsKey("userId")) {
                return jwtChecked;
            }
            if (!claims.containsKey("userRole")) {
                return jwtChecked;
            }

            long userId = Long.parseLong(claims.get("userId").toString());
            if (!usersDAO.exists(userId)) {
                return jwtChecked;
            }

            String userRole = claims.get("userRole").toString();
            if (!userRole.equals("user")){
                String existingUserRole = usersDAO.getRole(userId);
                if(existingUserRole == null) {
                    return jwtChecked;
                }
                if(!existingUserRole.equals(userRole) || (!userRole.equals("owner") && !userRole.equals("admin"))) {
                    return jwtChecked;
                }
            }

            // Check if auth time from the database matches with the issued time of the token
            // If they don't match, it means that the user changed password
            Timestamp authTimeFromDB = usersDAO.getAuthTime(userId);
            Date authTime = new Date(authTimeFromDB.getTime());
            Date issuedAt = claims.getIssuedAt();
            if (authTime.compareTo(issuedAt) != 0) {
                return jwtChecked;
            }

            jwtChecked.isValid = true;
            jwtChecked.userId = userId;
            jwtChecked.userRole = userRole;
            return jwtChecked;
        } catch (ExpiredJwtException e) {
            // LOGGER.error("JWT token expired: ", e);
            return jwtChecked;
        } catch (MalformedJwtException e) {
           // LOGGER.error("Malformed JWT token: ", e);
            return jwtChecked;
        } catch (Exception e) {
           // LOGGER.error("Error validating JWT token: ", e);
            return jwtChecked;
        }
    }
}