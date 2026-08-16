package com.yashwanth.ecommerce.security;

import com.yashwanth.ecommerce.service.CustomUserDetailsService;
import com.yashwanth.ecommerce.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println(
                "========== JWT FILTER =========="
        );

        System.out.println(
                "REQUEST: " +
                        request.getMethod() +
                        " " +
                        request.getRequestURI()
        );

        String authHeader = request.getHeader("Authorization");

        // No JWT provided
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println(
                    "AUTHORIZATION HEADER: NOT PRESENT"
            );

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(
                "AUTHORIZATION HEADER: PRESENT"
        );

        // Extract JWT and remove accidental leading/trailing whitespace
        String token = authHeader.substring(7).trim();

        // Empty token check
        if (token.isEmpty()) {

            System.out.println(
                    "JWT ERROR: TOKEN IS EMPTY"
            );

            filterChain.doFilter(request, response);
            return;
        }

        try {

            String email = jwtService.extractUsername(token);

            System.out.println(
                    "JWT EMAIL: " + email
            );

            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                System.out.println(
                        "DATABASE USER: " +
                                userDetails.getUsername()
                );

                System.out.println(
                        "DATABASE AUTHORITIES: " +
                                userDetails.getAuthorities()
                );

                boolean tokenValid =
                        jwtService.isTokenValid(
                                token,
                                userDetails
                        );

                System.out.println(
                        "JWT VALID: " + tokenValid
                );

                if (tokenValid) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "SECURITY CONTEXT AUTHENTICATED: YES"
                    );

                    System.out.println(
                            "AUTHENTICATED USER: " +
                                    SecurityContextHolder
                                            .getContext()
                                            .getAuthentication()
                                            .getName()
                    );

                    System.out.println(
                            "AUTHENTICATED AUTHORITIES: " +
                                    SecurityContextHolder
                                            .getContext()
                                            .getAuthentication()
                                            .getAuthorities()
                    );

                } else {

                    System.out.println(
                            "SECURITY CONTEXT AUTHENTICATED: NO"
                    );

                    System.out.println(
                            "REASON: JWT TOKEN INVALID"
                    );
                }

            } else {

                if (email == null) {

                    System.out.println(
                            "SECURITY CONTEXT AUTHENTICATED: NO"
                    );

                    System.out.println(
                            "REASON: EMAIL IS NULL"
                    );

                } else {

                    System.out.println(
                            "AUTHENTICATION ALREADY EXISTS"
                    );

                    System.out.println(
                            "CURRENT AUTHENTICATION: " +
                                    SecurityContextHolder
                                            .getContext()
                                            .getAuthentication()
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR TYPE: " +
                            e.getClass().getName()
            );

            System.out.println(
                    "JWT ERROR MESSAGE: " +
                            e.getMessage()
            );

            e.printStackTrace();
        }

        System.out.println(
                "FINAL SECURITY CONTEXT: " +
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
        );

        System.out.println(
                "================================"
        );

        filterChain.doFilter(request, response);
    }
}