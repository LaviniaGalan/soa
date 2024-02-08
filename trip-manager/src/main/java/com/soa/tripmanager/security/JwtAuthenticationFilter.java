package com.soa.tripmanager.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Extract JWT token from Authorization header
            String header = request.getHeader("Authorization");
            String token = "";
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            }
            // Validate JWT token (we should use auth service; for the moment we will check it to not be empty)
            if (token != null && !token.isEmpty()) {
//                String username = Jwts.parser()
//                        .setSigningKey("abcd1234")
//                        .parseClaimsJws(token)
//                        .getBody()
//                        .getSubject();
                String email = request.getHeader("Email");
                if(email == null || email.isEmpty()){
                    email = "email";
                }
                // Set the authenticated user in the Spring Security context
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token has expired");
        } catch (Exception e) {
            logger.error("Failed to validate JWT token", e);
        }
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
