package com.faroukbakre.farbaksshop.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "23456432345";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Got here!!!");
        System.out.println("TokenAuthenticationFilter invoked for path: " + request.getRequestURI());
        // Extract token from the request header
        String token = extractToken(request);

        // Validate and authenticate the token
        if (token != null) {
            Authentication authentication = validateToken(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("userId", getId(token));
            }
            else {
                sendErrorResponse(response, "Invalid or expired token", HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        else {
            sendErrorResponse(response, "Token not provided", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract the token excluding "Bearer "
        }

        return null;
    }

    private String getId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        // Extract information from the token
        return claims.getSubject();
    }

    private Authentication validateToken(String token) {
        try {
            // Parse and validate the token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract information from the token
            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            // Create an Authentication object
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(username, null, authorities);

        } catch (ExpiredJwtException e) {
            // Handle token expiration
            // You may choose to refresh the token or log the user out
            return null;
        } catch (Exception e) {
            // Handle other exceptions
            return null;
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage, int httpStatus) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus);
        response.getWriter().write("{\"error\":\"" + errorMessage + "\"}");
    }
}
