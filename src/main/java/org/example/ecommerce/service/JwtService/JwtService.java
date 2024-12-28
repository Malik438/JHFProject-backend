package org.example.ecommerce.service.JwtService;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    //private  static  final  String  SECRET_KEY = "cea333c646abd239c8953e1fadf3a7aab4eb2a912a72bad7d8224984392bf46ba9eb61c0ad262ed6225c41062f7b132e134c05c04013a4a155cbd5266bc092e3e9c41219ae932ea4037646004b7c0ba50a345687db9ff71b0b3f96599a4a785b373d09c29116e9eb43b223b89b693b0d9c64c6875a2a3b3bc06d5e32e547f962fc493d6609144ef5f64e155103eba4aa08ffce8e30764af349596631d3c46f66b36be215bd8dc603b8a5fdcdd387163f4de896d4c8b3cf82e1aa9c9725dc08bc0eedc62288af225d7456a34e4f0cada16b11175e81b33140f0984620d994ca8d39c68b4dc5ec0f5b53e5ad4569012bef6d7c814b26c15e24960047223ff27c13";


    //@Value("${application.security.jwt.secret-key}")
    private static final   String Secret_Key = "cea333c646abd239c8953e1fadf3a7aab4eb2a912a72bad7d8224984392bf46ba9eb61c0ad262ed6225c41062f7b132e134c05c04013a4a155cbd5266bc092e3e9c41219ae932ea4037646004b7c0ba50a345687db9ff71b0b3f96599a4a785b373d09c29116e9eb43b223b89b693b0d9c64c6875a2a3b3bc06d5e32e547f962fc493d6609144ef5f64e155103eba4aa08ffce8e30764af349596631d3c46f66b36be215bd8dc603b8a5fdcdd387163f4de896d4c8b3cf82e1aa9c9725dc08bc0eedc62288af225d7456a34e4f0cada16b11175e81b33140f0984620d994ca8d39c68b4dc5ec0f5b53e5ad4569012bef6d7c814b26c15e24960047223ff27c13";
   // @Value("${application.security.jwt.expiration}")
    private static final long Jwt_Expiration = 3600000;
   // @Value("${application.security.jwt.refresh-token.expiration}")
    private  static  final long Refresh_Expiration = 604800000;


    public String generateToken(  UserDetails userDetails) {
        return generateToken( new HashMap<>(), userDetails ,Jwt_Expiration);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails, Refresh_Expiration);
    }


    public  String generateToken(Map<String, Objects> extraClaims , UserDetails userDetails , Long expiration) {

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("role" , userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }



    public String extractUserName(String token) {

        return  extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Secret_Key);
        return Keys.hmacShaKeyFor(keyBytes);


    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // the  SignInKey is responsible for building signature part of jwt token  where each user have signature ; and its must be as minimum 256 bit


}
