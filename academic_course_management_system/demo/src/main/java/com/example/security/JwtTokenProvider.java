// package com.example.security;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtTokenProvider {

//     private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//     private final long EXPIRATION_TIME = 86400000; // 1 day

//     public String generateToken(String gmail, String role, Integer idAccount) {
//         return Jwts.builder()
//                 .setSubject(gmail)
//                 .claim("role", role)
//                 .claim("idAccount", idAccount)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                 .signWith(key)
//                 .compact();
//     }

//     public String getGmailFromToken(String token) {
//         return Jwts.parserBuilder().setSigningKey(key).build()
//                 .parseClaimsJws(token).getBody().getSubject();
//     }

//     public Integer getIdAccountFromToken(String token) {
//         Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
//                 .parseClaimsJws(token).getBody();
//         return claims.get("idAccount", Integer.class);
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder().setSigningKey(key).build()
//                     .parseClaimsJws(token);
//             return true;
//         } catch (JwtException | IllegalArgumentException e) {
//             return false;
//         }
//     }
// }

package com.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 86400000; // 1 day

    public String generateToken(String gmail, String role, Integer idAccount) {
        return Jwts.builder()
                .setSubject(gmail)
                .claim("role", role)
                .claim("idAccount", idAccount)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String getGmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Integer getIdAccountFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.get("idAccount", Integer.class);
    }

    public Collection<? extends SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        String role = claims.get("role", String.class);
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}