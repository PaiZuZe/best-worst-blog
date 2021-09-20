package com.paizuze.bestWorstBlog.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.VALID_TIME}")
    private long ALIVE_TIME;

    public JwtTokenUtil(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken() {
        Date now = new Date();

        return Jwts.builder()
                .setSubject("Bob")
                .setIssuer("bestWorstBlogAPI")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ALIVE_TIME * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            throw e;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();
    }

}
