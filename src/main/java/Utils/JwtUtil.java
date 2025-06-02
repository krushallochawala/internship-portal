/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class JwtUtil {
//    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure random key
//    private static final long EXPIRATION_TIME = 1000*60*60*2; // 2 Hours
    
    private static final String SECRET = "my-super-secret-key-1234567890123456"; // 32+ chars
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 Hours
    
    public static String generateToken(String email,int userId){
        System.out.println("Validating JWT with key: " + key);
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
        
    }
    
//    public static boolean validateToken(String token){
//        try{
//            Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token);
//            return true;
//        }catch(Exception e){
//            return false;
//        }
//    }
    
    public static Claims validateToken(String token) throws ExpiredJwtException, SignatureException, MalformedJwtException {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    public static String getEmailFromToken(String token){
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }
}
