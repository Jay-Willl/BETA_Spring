package edu.sustech.ooad.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtAuth {

    static final long expiration = 1000 * 3600 * 24;
    static String signature = "ooadproject";

    public static String createToken(String username, String passwd) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("test", "test")
                .claim("primary", username)
                .claim("passwd", passwd)
                .setSubject("login_auth")
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }

    public static Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims;
    }

}
