package com.example.salamnura.todolist.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenGenerator implements AuthenticationEntryPoint {
    private static final long serialVersionUID = 3600 * 4;

    @Value("${jwt.secret}")

    private String jwtSecret;

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimsFromToken(String token , Function<Claims , T> claimsResolver){
            return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    public String getEmailFromToken(String token){
        return getClaimsFromToken(token , Claims::getSubject);
    }

    public Date getExpirationFromToken(String token){
        return getClaimsFromToken(token , Claims::getExpiration);
    }





    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    }
}
