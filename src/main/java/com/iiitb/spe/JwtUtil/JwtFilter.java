package com.iiitb.spe.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
@ComponentScan("com.iiitb.spe.JwtUtil")

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private TokenManager tokenManager;

    public JwtFilter(JwtUserDetailsService userDetailsService, TokenManager tokenManager) {
        this.tokenManager = tokenManager;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        System.out.println(tokenHeader);
        String username = null;
        String token = null;
        //System.out.println(tokenHeader);
        if (tokenHeader != null) {
            if (tokenHeader.length() != 0){
           // token = tokenHeader.substring(10);
            token = tokenHeader.substring(7);
            System.out.println(token);
            //System.out.println(tokenHeader);
            try {
                username = tokenManager.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token "+ e);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }}
        } else {
            System.out.println("Bearer String not found in token");
        }
        System.out.println("Username: " + username);
        if (null != username &&SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenManager.validateJwtToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken
                        authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
