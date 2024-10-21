package com.bootcamp_berijalan.transactionservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "danala04";

    public CustomUserDetails decodeToken(String token) {
        Claims claims = getClaimsFromToken(token);

        String username = claims.getSubject();
        Long id = claims.get("user_id", Long.class);
        String role = claims.get("role", String.class);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new CustomUserDetails(username, id, authorities);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("Invalid Token");
        }
    }
}
