package ro.tuc.ds2020.jwt;//
//package ro.tuc.ds2020.jwt;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    // Generate a secure 256-bit key
//    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 hours validity
//
//    /**
//     * Generate JWT Token with isAdmin claim
//     */
//    public String generateToken(String username, boolean isAdmin) {
//        return Jwts.builder()
//                .setSubject(username) // Set username as subject
//                .claim("isAdmin", isAdmin) // Custom claim for isAdmin
//                .setIssuedAt(new Date()) // Issued time
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration time
//                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Sign the token
//                .compact();
//    }
//
//    /**
//     * Validate the JWT Token
//     */
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    /**
//     * Extract Username (Subject) from Token
//     */
//    public String extractUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    /**
//     * Extract isAdmin Claim from Token
//     */
//    public boolean extractIsAdmin(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("isAdmin", Boolean.class);
//    }
//}
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 hours validity

    // Constructor pentru a injecta cheia secretă din proprietăți
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.SECRET_KEY = new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    /**
     * Generate JWT Token with isAdmin claim
     */
    public String generateToken(String username, boolean isAdmin) {
        return Jwts.builder()
                .setSubject(username)
                .claim("isAdmin", isAdmin)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Validate JWT Token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extract Username (Subject) from Token
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extract isAdmin Claim from Token
     */
    public boolean extractIsAdmin(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("isAdmin", Boolean.class);
    }
}
