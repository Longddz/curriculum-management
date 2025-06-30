// package com.example.security;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtTokenProvider jwtTokenProvider;

//     public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//         this.jwtTokenProvider = jwtTokenProvider;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                     FilterChain filterChain) throws ServletException, IOException {
//         String header = request.getHeader("Authorization");
//         if (header != null && header.startsWith("Bearer ")) {
//             String token = header.substring(7);
//             if (jwtTokenProvider.validateToken(token)) {
//                 String gmail = jwtTokenProvider.getGmailFromToken(token);
//                 Integer idAccount = jwtTokenProvider.getIdAccountFromToken(token); // Đảm bảo Integer
//                 JwtAuthentication authentication = new JwtAuthentication(gmail, idAccount);
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }

package com.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtTokenProvider.validateToken(token)) {
                String gmail = jwtTokenProvider.getGmailFromToken(token);
                Integer idAccount = jwtTokenProvider.getIdAccountFromToken(token);
                Collection<? extends SimpleGrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesFromToken(token);
                JwtAuthentication authentication = new JwtAuthentication(gmail, idAccount, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}